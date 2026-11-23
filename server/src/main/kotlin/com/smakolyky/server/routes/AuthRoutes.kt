package com.smakolyky.server.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

@Serializable
data class NotImplementedResponse(val message: String = "Not implemented yet")

fun Route.authRoutes() {
    route("/auth") {
        post("/register") { call.respond(HttpStatusCode.NotImplemented, NotImplementedResponse()) }
        post("/login") { call.respond(HttpStatusCode.NotImplemented, NotImplementedResponse()) }
    }
}
