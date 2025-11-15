package me.inassar.shared

class WasmPlatform : Platform {
    override val name: PlatformEnum = PlatformEnum.WASM_JS
}

actual fun getPlatform(): Platform = WasmPlatform()