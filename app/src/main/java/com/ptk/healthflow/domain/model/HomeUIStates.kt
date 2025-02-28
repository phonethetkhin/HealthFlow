package com.ptk.healthflow.domain.model

data class HomeUIStates(
    val firstName: String = "",
    var updateTime: Comparable<*> = 0,
    var lowBloodPressure: Int = 0,
    var highBloodPressure: Int = 0,
    var heartRate: Int = 0,
    var temperature: String = "",
    var oxygen: Int = 0,

    var healthCondition: HeartBeatType = HeartBeatType.MODERATE,
    var feverType: FeverType = FeverType.NORMAL,
    var bloodPressureType: BloodPressureType = BloodPressureType.NORMAL,
    var oxygenLevelType: OxygenLevelType = OxygenLevelType.NORMAL,
)
