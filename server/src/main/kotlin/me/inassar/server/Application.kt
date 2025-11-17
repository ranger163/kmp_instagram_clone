package me.inassar.server

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.inassar.shared.Greeting
import me.inassar.shared.SERVER_PORT

/** Launches the Ktor server using Netty. */
fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

/** Basic Ktor module exposing a health endpoint. */
fun Application.module() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}
