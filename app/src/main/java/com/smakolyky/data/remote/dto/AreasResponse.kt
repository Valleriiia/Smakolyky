package com.smakolyky.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AreasResponse(
    val meals: List<AreaDto>? = null,
)
