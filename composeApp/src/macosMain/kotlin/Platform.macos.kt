import platform.Foundation.NSProcessInfo

class MacosPlatform: Platform {
    override val name by lazy {
        val processInfo = NSProcessInfo.processInfo()
        "${processInfo.operatingSystemName()} ${processInfo.operatingSystemVersionString()}"
    }
}

actual fun getPlatform(): Platform = MacosPlatform()