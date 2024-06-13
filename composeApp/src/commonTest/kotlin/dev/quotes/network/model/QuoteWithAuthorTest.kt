package dev.quotes.network.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuoteWithAuthorTest {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Test
    fun testEncodeDecode() {
        val originalData = """
            [
              {
                "author": "Douglas Crockford",
                "quote": "Mathematics is important in programming, but it’s just one of a lot of things that are important. If you overemphasize the math then you underemphasize stuff which might be even more important, such as literacy."
              },
              {
                "author": "Richard Feynman",
                "quote": "Computer science differs from physics in that it is not actually a science. It does not study natural objects. Rather, computer science is like engineering; it is all about getting something to do something."
              },
              {
                "author": "Toru Iwatani",
                "quote": "I'm interested in creating images that communicate with people."
              },
              {
                "author": "Jim Horning",
                "quote": "Hardware is the part you can replace. Software is the part you have to keep, because it’s so expensive and nobody understands it any more."
              },
              {
                "author": "Martin Fowler",
                "quote": "There are few things more frustrating or time wasting than debugging. Wouldn't it be a hell of a lot quicker if we just didn't create the bugs in the first place?"
              },
              {
                "author": "James Gleick",
                "quote": "Computer programs are the most intricate, delicately balanced and finely interwoven of all the products of human industry to date."
              },
              {
                "author": "Richard Moore (engineer)",
                "quote": "The difference between theory and practice is that in theory, there is no difference between theory and practice."
              },
              {
                "author": "Richard Hamming",
                "quote": "Typing is no substitute for thinking."
              },
              {
                "author": "Danny Hillis",
                "quote": "A skilled programmer is like a poet who can put into words those ideas that others find inexpressible."
              },
              {
                "author": "Douglas Crockford",
                "quote": "JavaScript, purely by accident, has become the most popular programming language in the world."
              }
            ]
        """.trimIndent()
        val decoded = json.decodeFromString<List<QuoteWithAuthor>>(originalData)
        assertEquals(10, decoded.size)
        decoded.forEach {
            assertTrue(it.author.isNotBlank())
            assertTrue(it.quote.isNotBlank())
        }
        val encodedData = json.encodeToString(decoded)
        val whitespace = "[\\s\\n\\r\\t]".toRegex()
        assertEquals(originalData.replace(whitespace, ""), encodedData.replace(whitespace, ""))
        val reDecoded = json.decodeFromString<List<QuoteWithAuthor>>(encodedData)
        assertEquals(reDecoded, decoded)
    }
}
