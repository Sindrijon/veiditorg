package com.veiditorg.DummyData

import java.time.Duration
// Simple data class that holds permit information
data class Permit(
    val permitID: Int = 0,
    val ownerID: String,
    val river: String,
    val startDuration: Duration,
    val endDuration: Duration,
    val forTrade: Boolean = false
)
// Need to implement a method to give the permits ID