package com.example.appstudents.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appstudents.data.Student

@Database(entities = [ Student::class ], version=1, exportSchema = false)
@TypeConverters(StudentTypeConverters::class)
abstract class StudentDatabase : RoomDatabase(){
    abstract fun studentDao(): StudentDao
}