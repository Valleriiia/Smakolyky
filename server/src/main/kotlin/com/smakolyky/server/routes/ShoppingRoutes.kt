package com.smakolyky.server.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.shoppingRoutes() {
    route("/shopping") {
        get { call.respond(HttpStatusCode.NotImplemented, NotImplementedResponse()) }
        post { call.respond(HttpStatusCode.NotImplemented, NotImplementedResponse()) }
    }
}
