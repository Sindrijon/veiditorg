package com.veiditorg.DummyData
// Simple data class that holds user information
data class User(
    val username: String,
    val fullName: String,
    val password: String,
    val email: String,
    val phone: String
) {
    fun isValid(name: String, pass: String): Boolean {
        return name == username && pass == password
    }
}