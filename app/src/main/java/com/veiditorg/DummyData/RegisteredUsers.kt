package com.veiditorg.DummyData

class RegisteredUsers (
    private val users: MutableList<User>
) {
    fun addUser(user: User) {
        users.add(user)
    }

    fun deleteUser(user: User) {
        users.remove(user)
    }

    fun validUser(username: String, password: String): Boolean {
        // Returns true if a user with "username" and "password" exists in list of users
        users.forEach{ user -> if (user.isValid(username, password)) {return true} }
        return false
    }

}