package com.example.veiditorg

class MockSessionManager : SessionManager {
        private var sessionActive = false

        override fun startSession() {
            sessionActive = true
        }

        override fun endSession() {
            sessionActive = false
        }

        override fun isSessionActive(): Boolean {
            return sessionActive
        }


}