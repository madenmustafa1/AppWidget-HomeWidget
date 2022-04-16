package com.maden.makewidget.model.count_model

data class WorkOrderCount(
    val activeWorkOrderCount: Int,
    val allWorkOrderCount: Int,
    val completeWorkOrderCount: Int,
    val failedWorkOrderCount: Int,
    val notReachableCount: Int,
    val reservationCount: Int,
    val transferredWorkOrderCount: Int,
    val waitWorkOrderCount: Int
)