package com.veiditorg.modul


data class Permit(
    var river: String? = null,
    var ownerId: String? = null,
    var forTrade: Boolean = false,
    var startDate: String? = null,
    var endDate: String? = null,
    var permitID: String? = null
)

