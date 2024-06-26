package com.emmanuel.demo_nyt.domain.model

//Article contain all the data retrieved from the API
data class Article(
    val section: String?,
    val subsection: String?,
    val title: String?,
    val abstract: String?,
    val url: String?,
    val uri: String?,
    val byline: String?,
    val item_type: String?,
    val updated_date: String?,
    val created_date: String?,
    val published_date: String?,
    val material_type_facet: String?,
    val kicker: String?,
    val des_facet: List<String?>,
    val org_facet: List<String?>,
    val per_facet: List<String?>,
    val geo_facet: List<String?>,
    val multimedia: List<Multimedia?>,
    val short_url: String?
)