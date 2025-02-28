package com.ptk.healthflow.domain.uistates

import com.ptk.healthflow.domain.model.BloodPressureType
import com.ptk.healthflow.domain.model.FeverType
import com.ptk.healthflow.domain.model.HeartBeatType
import com.ptk.healthflow.domain.model.OxygenLevelType

data class HomeUIStates(
    val firstName: String = "",
    val lastName: String = "",
    var updateTime: String = "",
    var lowBloodPressure: Int = 0,
    var highBloodPressure: Int = 0,
    var heartRate: Int = 0,
    var temperature: String = "",
    var oxygen: Int = 0,
    var totalNotificationCount: Int = 0,

    var isShowDialog: Boolean = false,
    var isShowErrorDialog: Boolean = false,
    var isLoading: Boolean = false,
    var errDialogTitle: String = "",
    var errDialogMessage: String = "",

    var healthCondition: HeartBeatType = HeartBeatType.MODERATE,
    var feverType: FeverType = FeverType.NORMAL,
    var bloodPressureType: BloodPressureType = BloodPressureType.NORMAL,
    var oxygenLevelType: OxygenLevelType = OxygenLevelType.NORMAL,
)
