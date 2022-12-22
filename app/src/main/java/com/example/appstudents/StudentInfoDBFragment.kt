package com.example.appstudents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appstudents.MyConstants.STUDENT_ID_TAG
import com.example.appstudents.data.Student
import java.util.*

class StudentInfoDBFragment : Fragment() {
    private var student: Student?=null
    private lateinit var studentInfoViewModel: StudentInfoDBViewModel
    private lateinit var etLastName : EditText
    private lateinit var etFirstName : EditText
    private lateinit var etMiddleName : EditText
    private lateinit var etFaculty : EditText
    private lateinit var etGroup : EditText
    private lateinit var dpDate : DatePicker
    private lateinit var btnSave : Button
    private lateinit var btnCancel : Button

    companion object {
        fun newInstance(studentID: UUID): StudentInfoDBFragment {
            val args = Bundle().apply {
                putString(STUDENT_ID_TAG, studentID.toString())
            }
            return StudentInfoDBFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val studentId: UUID = UUID.fromString(arguments?.getString(STUDENT_ID_TAG))
        studentInfoViewModel = ViewModelProvider(this).get(StudentInfoDBViewModel::class.java)
        studentInfoViewModel.loadStudent(studentId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.student_info, container, false)
        etLastName=view.findViewById(R.id.lastName)
        etFirstName=view.findViewById(R.id.firstName)
        etMiddleName=view.findViewById(R.id.middleName)
        etFaculty=view.findViewById(R.id.faculty)
        etGroup=view.findViewById(R.id.group)
        dpDate=view.findViewById(R.id.datePicker)
        btnSave=view.findViewById(R.id.btOk)
        btnSave.setOnClickListener {
            if (student==null){
               student=Student()
               updateStudent()
               studentInfoViewModel.newStudent(student!!)
            }
            else{
                updateStudent()
                studentInfoViewModel.saveStudent(student!!)
            }
            //TODO("выход")
            callbacks?.showDBStudents()
        }
        btnCancel=view.findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener {
           // TODO("выход")
            callbacks?.showDBStudents()
        }
        return view
    }

    private fun updateStudent(){
        val dateBirth = GregorianCalendar(dpDate.year,  dpDate.month, dpDate.dayOfMonth)
        student?.lastName= etLastName.text.toString()
        student?.firstName=etFirstName.text.toString()
        student?.middleName=etMiddleName.text.toString()
        student?.birthDate=dateBirth.time
        student?.faculty=etFaculty.text.toString()
        student?.group=etGroup.text.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentInfoViewModel.studentLiveData.observe(
            viewLifecycleOwner
        ) { student ->
            student?.let {
                this.student = student
                updateUI()
            }
        }
    }

    fun updateUI(){
        etLastName.setText(student?.lastName)
        etFirstName.setText(student?.firstName)
        etMiddleName.setText(student?.middleName)
        val dateBirth = GregorianCalendar()
        dateBirth.time=student?.birthDate
        dpDate.updateDate(dateBirth.get(Calendar.YEAR),dateBirth.get(Calendar.MONTH),
                                                       dateBirth.get(Calendar.DAY_OF_MONTH))
        etFaculty.setText(student?.faculty)
        etGroup.setText(student?.group)
    }

    interface Callbacks {
        fun showDBStudents()
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}