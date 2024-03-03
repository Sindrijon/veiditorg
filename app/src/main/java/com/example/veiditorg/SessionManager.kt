package com.example.veiditorg

interface SessionManager {

        fun startSession()
        fun endSession()
        fun isSessionActive(): Boolean

}