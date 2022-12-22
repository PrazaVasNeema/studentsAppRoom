package com.example.appstudents.data

data class StudentsList(
    val items : MutableList<Student> = mutableListOf()
){
    override fun toString(): String {
        return "StudentsList(items=$items)"
    }
}
