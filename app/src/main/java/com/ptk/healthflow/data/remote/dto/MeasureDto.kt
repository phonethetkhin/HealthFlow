package com.ptk.healthflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MeasureDto(
    val body: Body? = null,
    val status: Int? = null
)

@Serializable
data class MeasuresItem(
    val unit: Int? = null,
    val fm: Int? = null,
    val position: Int? = null,
    val type: Int? = null,
    val value: Int? = null,
    val algo: Int? = null
)

@Serializable
data class MeasuregrpsItem(
    val grpid: Int? = null,
    val date: Int? = null,
    val measures: List<MeasuresItem?>? = null,
    val attrib: Int? = null,
    val created: Int? = null,
    val timezone: String? = null,
    val modified: Int? = null,
    val comment: String? = null,
    val category: Int? = null,
    val hashDeviceid: String? = null,
    val deviceid: String? = null
)

@Serializable
data class Body(
    val offset: Int? = null,
    val timezone: String? = null,
    val more: Int? = null,
    val updatetime: String? = null,
    val measuregrps: List<MeasuregrpsItem?>? = null
)
