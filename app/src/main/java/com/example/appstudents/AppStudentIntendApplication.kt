package com.example.appstudents

import android.app.Application
import android.content.Context
import com.example.appstudents.repository.StudentDBRepository

class AppStudentIntendApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: AppStudentIntendApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        StudentDBRepository.initialize(this)
    }
}