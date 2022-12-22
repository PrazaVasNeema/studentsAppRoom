package com.example.appstudents.repository

import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.example.appstudents.AppStudentIntendApplication
import com.example.appstudents.data.Student
import com.example.appstudents.data.StudentsList
import com.google.gson.Gson

class StudentsRepository {
    companion object {
        private var INSTANCE: StudentsRepository? = null

        fun getInstance(): StudentsRepository {
            if (INSTANCE == null) {
                INSTANCE = StudentsRepository()
            }
            return INSTANCE ?:
            throw IllegalStateException("Репозиторий StudentsRepository не инициализирован")
        }
    }

    var studentsList: MutableLiveData<StudentsList?> = MutableLiveData()
    var student : MutableLiveData<Student> = MutableLiveData()

    init {
        loadStudents()
    }

    fun loadStudents(){
        val jsonString=
            PreferenceManager.getDefaultSharedPreferences(AppStudentIntendApplication.applicationContext())
            .getString("students",null)
        if (!jsonString.isNullOrBlank()) {
            val st = Gson().fromJson(jsonString, StudentsList::class.java)
            if (st!=null)
                this.studentsList.postValue(st)
        }
    }

    fun saveStudents(){
        val gson = Gson()
        var jsonStudents = gson.toJson(studentsList.value)
        val preference =
            PreferenceManager.getDefaultSharedPreferences(AppStudentIntendApplication.applicationContext())
        preference.edit().apply {
            putString("students", jsonStudents)
            apply()
        }
    }

    fun setCurrentStudent(position: Int){
        if (studentsList.value==null || studentsList.value!!.items==null ||
            position<0 || (studentsList.value?.items?.size!! <= position))
            return
        student.postValue(studentsList.value?.items!![position])
    }

    fun setCurrentStudent(student :Student){
        this.student.postValue(student)
    }

    fun addStudent(student: Student) {
        var studentsListTmp = studentsList.value
        if (studentsListTmp == null) studentsListTmp = StudentsList()
        studentsListTmp.items.add(student)
        studentsList.postValue(studentsListTmp)
    }

    fun getPosition(student: Student): Int = studentsList.value?.items?.indexOfFirst {
        it.id == student.id } ?: -1

    fun getPosition() :Int{
        if (student.value!=null)
            return getPosition(student.value!!)
        else
            return 0
    }

    fun updateStudent(student: Student) {
        val position = getPosition(student)
        if (position < 0) addStudent(student)
        else {
            var studentsListTmp = studentsList
            studentsListTmp.value!!.items[position]=student
            studentsList.postValue(studentsListTmp.value)
        }
    }

    fun deleteStudent(student: Student){
        var studentsListTmp = studentsList
        if (studentsListTmp.value!!.items.remove(student)) {
            studentsList.postValue(studentsListTmp.value)
        }
        newStudent()
    }

    fun deleteStudent(){
        if (student.value!=null)
           deleteStudent(student.value!!)
    }

    fun newStudent(){
      setCurrentStudent(Student())
    }

}