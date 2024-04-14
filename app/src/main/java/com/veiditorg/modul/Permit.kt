package com.veiditorg.modul

import java.time.LocalDate

data class Permit(
    var river: String? = null,
    var ownerId: String? = null,
    var forTrade: Boolean = false,
    var startDate: String? = null,
    var endDate: String? = null,
    var permitID: String? = null
)