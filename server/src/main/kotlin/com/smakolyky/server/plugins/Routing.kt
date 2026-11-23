package com.smakolyky.server.plugins

import com.smakolyky.server.routes.authRoutes
import com.smakolyky.server.routes.favoritesRoutes
import com.smakolyky.server.routes.ratingsRoutes
import com.smakolyky.server.routes.shoppingRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/health") { call.respondText("OK", status = HttpStatusCode.OK) }

        authRoutes()
        favoritesRoutes()
        ratingsRoutes()
        shoppingRoutes()
    }
}
