package com.example.appstudents

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.appstudents.MyConstants.TAG
import com.example.appstudents.data.Student
import com.example.appstudents.data.StudentsList
import com.example.appstudents.repository.StudentsRepository

class StudentListViewModel : ViewModel() {
    var studentsList : MutableLiveData<StudentsList> = MutableLiveData()
    private var studentCurrent : Student = Student()
    val student : Student
        get() = studentCurrent

    private var observer = Observer<StudentsList?>
    { newList ->
        newList?.let {
            Log.d(TAG, "Получен список StudentListViewModel от StudentsRepository")
            studentsList.postValue(newList)
        }
    }

    init {
        StudentsRepository.getInstance().student.observeForever{
            studentCurrent=it
            Log.d(TAG, "Получили Student в StudentListViewModel")
        }
        StudentsRepository.getInstance().studentsList.observeForever(observer)
        Log.d(TAG, "Подписались StudentListViewModel к StudentsRepository")
    }

    fun setStudent(student: Student){
        StudentsRepository.getInstance().setCurrentStudent(student)
    }

    fun getPosition() : Int =
        StudentsRepository.getInstance().getPosition()
}