package com.alex.helpietest.model

//Classes referentes ao User
data class User(val id: Long, val name: String, val email: String, val company: Company, val address: Address)

data class Address(val city: String)

data class Company(val name: String)



//Classe referente ao Post
data class Post(val userId: Long, val id: Long, val title: String, val body: String)



//Classe referente à Photo
data class Photo(val albumId: Long, val id: Long, val title: String, val url: String, val thumbnailUrl: String)