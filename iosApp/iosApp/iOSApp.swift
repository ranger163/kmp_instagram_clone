import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        InitKoinKt.doInitKoin { Koin_coreKoinApplication in
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
