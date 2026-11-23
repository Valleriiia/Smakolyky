package com.smakolyky.server.plugins

import org.jetbrains.exposed.sql.Database

fun connectToDatabase(url: String, user: String, password: String): Database =
    Database.connect(
        url = url,
        driver = "org.postgresql.Driver",
        user = user,
        password = password,
    )
