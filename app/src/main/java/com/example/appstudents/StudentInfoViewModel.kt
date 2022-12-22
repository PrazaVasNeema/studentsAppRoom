package com.example.appstudents

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appstudents.MyConstants.TAG
import com.example.appstudents.data.Student
import com.example.appstudents.repository.StudentDBRepository
import com.example.appstudents.repository.StudentsRepository
import java.util.*

class StudentInfoViewModel : ViewModel() {
    var student : MutableLiveData<Student> = MutableLiveData()
    init {
        StudentsRepository.getInstance().student.observeForever{
            student.postValue(it)
            Log.d(TAG, "Получили Student в StidentInfoViewModel")
        }
    }

    fun save(lastName: String="",
             firstName: String="",
             middleName: String="",
             birthDate : Date = Date(),
             faculty : String="",
             group : String=""){
        if (student.value == null) student.value= Student()
        student.value!!.lastName=lastName
        student.value!!.firstName=firstName
        student.value!!.middleName=middleName
        student.value!!.birthDate=birthDate
        student.value!!.faculty=faculty
        student.value!!.group=group
        StudentsRepository.getInstance().updateStudent(student.value!!)
        StudentDBRepository.get().addStudent(student.value!!)
    }
}