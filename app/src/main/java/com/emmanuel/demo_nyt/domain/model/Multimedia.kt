package com.emmanuel.demo_nyt.domain.model

//Multimedia model for the articles
data class Multimedia(
    val url: String?,
    val format: String?,
    val height: Int?,
    val width: Int?,
    val type: String?,
    val subtype: String?,
    val caption: String?,
    val copyright: String?
)