package com.maden.makewidget.model.count_model

data class CountModel(
    val citiesCounts: List<CitiesCount>?,
    val courierCount: Int?,
    val customerCount: Int?,
    val partnerCount: Int?,
    val workOrderCount: WorkOrderCount?
)