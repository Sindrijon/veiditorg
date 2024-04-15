package com.veiditorg.modul

data class TradeOffer(
    val tradeId: String? = null,
    val initiatingUserId: String? = null,
    val initiatingPermitId: String? = null,
    val respondingUserId: String? = null,
    val respondingPermitId: String? = null,
    var status: String = TradeStatus.PENDING.name
)
