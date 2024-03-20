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

    fun toList() : MutableList<User> {
        return users
    }
}