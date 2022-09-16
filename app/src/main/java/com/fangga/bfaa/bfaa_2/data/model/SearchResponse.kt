package com.fangga.bfaa.bfaa_2.data.model

import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "items")
    val items: List<User>
)