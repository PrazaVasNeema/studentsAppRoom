package com.example.appstudents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.appstudents.data.Student
import com.example.appstudents.repository.StudentDBRepository
import java.util.*

class StudentInfoDBViewModel: ViewModel() {
    private val studentRepository = StudentDBRepository.get()
    private val studentIdLiveData = MutableLiveData<UUID>()
    var studentLiveData: LiveData<Student?> =
        Transformations.switchMap(studentIdLiveData) { studentId ->
            studentRepository.getStudent(studentId)
        }

    fun loadStudent(studentId: UUID) {
        studentIdLiveData.value = studentId
    }

    fun newStudent(student: Student) {
        studentRepository.addStudent(student)
    }

    fun saveStudent(student: Student) {
        studentRepository.updateStudent(student)
    }
}