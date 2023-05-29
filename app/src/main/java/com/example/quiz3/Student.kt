package com.example.quiz3

data class Student(
    val email: String ?= null,
    val firstname : String ?= null,
    val lastname : String ?= null,
    val personalId : String ?= null,
    val profilePicture : String ?= null
)