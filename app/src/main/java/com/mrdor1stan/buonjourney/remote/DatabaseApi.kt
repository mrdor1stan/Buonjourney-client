package com.mrdor1stan.buonjourney.remote

interface DatabaseApi {
    fun loadUser(id: Int) : UserEntity

}