package com.smakolyky.server.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.favoritesRoutes() {
    route("/favorites") {
        get { call.respond(HttpStatusCode.NotImplemented, NotImplementedResponse()) }
        post { call.respond(HttpStatusCode.NotImplemented, NotImplementedResponse()) }
        delete("/{recipeId}") { call.respond(HttpStatusCode.NotImplemented, NotImplementedResponse()) }
    }
}
