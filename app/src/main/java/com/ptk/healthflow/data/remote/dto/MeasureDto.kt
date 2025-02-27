package com.ptk.healthflow.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MeasureDto(
    val body: Body? = null,
    val status: Int? = null
)

@Serializable
data class MeasuresItem(
    val type: Int? = null,
    val value: Int? = null,
)

@Serializable
data class MeasuregrpsItem(
    val measures: List<MeasuresItem?>? = null,
)

@Serializable
data class Body(
    val updatetime: Int? = null,
    val measuregrps: List<MeasuregrpsItem?>? = null
)
