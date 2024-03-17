package com.example.veiditorg.dummydata

import java.time.Duration
// Simple data class that holds permit information
data class Permit(
    val permitID: Int,
    val ownerID: String,
    val river: String,
    val startDuration: Duration,
    val endDuration: Duration,
    val forTrade: Boolean
)