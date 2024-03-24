package com.veiditorg.DummyData

import java.time.LocalDate

// Simple data class that holds permit information
data class Permit(
    var permitID: Int = 0,
    var ownerID: String,
    val river: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    var forTrade: Boolean = false,
    val titleImage: Int
) {
    fun setID(id: Int) {
        permitID = id
    }
}