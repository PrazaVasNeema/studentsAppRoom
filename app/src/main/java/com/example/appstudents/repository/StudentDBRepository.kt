package com.example.appstudents.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.appstudents.MyConstants
import com.example.appstudents.data.Student
import com.example.appstudents.database.StudentDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "test2.db"

class StudentDBRepository private constructor(context: Context) {

    companion object {
        private var INSTANCE: StudentDBRepository? = null
        fun initialize(context: Context) {

            if (INSTANCE == null) {
                Log.d(MyConstants.TAG, "dfdgfd")

                INSTANCE = StudentDBRepository(context)


            }
        }
        fun get(): StudentDBRepository {
            return INSTANCE ?:  throw IllegalStateException("StudentDBRepository must be initialized")
        }
    }

    private val database : StudentDatabase = Room.databaseBuilder(
        context.applicationContext,
        StudentDatabase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().build()

    private val studentDao = database.studentDao()

    fun getStudents(): LiveData<List<Student>> = studentDao.getStudents()
    fun getStudent(id: UUID): LiveData<Student?> = studentDao.getStudent(id)

    private val executor = Executors.newSingleThreadExecutor()
    fun updateStudent(student: Student) {
        executor.execute {
            studentDao.updateStudent(student)
        }
    }
    fun addStudent(student: Student) {
        executor.execute {
            studentDao.addStudent(student)
        }
    }
}

