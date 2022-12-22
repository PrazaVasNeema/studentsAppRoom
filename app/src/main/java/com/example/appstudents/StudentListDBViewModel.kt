package com.example.appstudents

import androidx.lifecycle.ViewModel
import com.example.appstudents.repository.StudentDBRepository

class StudentListDBViewModel : ViewModel() {
    private val studentRepository = StudentDBRepository.get()
    val studentListLiveData = studentRepository.getStudents()
}