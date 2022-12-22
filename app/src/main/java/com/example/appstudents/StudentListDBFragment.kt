package com.example.appstudents

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstudents.data.Student
import java.util.*

class StudentListDBFragment : Fragment() {

    private lateinit var studentListRecyclerView: RecyclerView
    private lateinit var studentListViewModel: StudentListDBViewModel

    private var adapter: StudentsListAdapter? = StudentsListAdapter(emptyList())

    companion object {
        private var INSTANCE: StudentListDBFragment? = null

        fun getInstance():StudentListDBFragment {
            if (INSTANCE == null) {
                INSTANCE = StudentListDBFragment()
            }
            return INSTANCE?: throw IllegalStateException("Отображение списка не создано!")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutView = inflater.inflate(R.layout.list_of_students, container, false)
        studentListRecyclerView=layoutView.findViewById(R.id.rvList)
        studentListRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        studentListRecyclerView.adapter = adapter
        return layoutView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentListViewModel = ViewModelProvider(this).get(StudentListDBViewModel::class.java)
        studentListViewModel.studentListLiveData.observe(
            viewLifecycleOwner,
            Observer { students ->
                students?.let {
                    updateUI(students)
                }
            })
    }

    private inner class StudentsListAdapter(private val items: List<Student>)
        : RecyclerView.Adapter<StudentHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): StudentHolder {
            val view = layoutInflater.inflate(R.layout.student_list_ement, parent, false)
            return StudentHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            holder.bind(items[position])
        }
    }

    private inner class StudentHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener{
        private lateinit var student: Student
        private val fioTextView: TextView = itemView.findViewById(R.id.tvFIO)
        private val ageTextView: TextView = itemView.findViewById(R.id.tvAge)
        private val groupTextView: TextView = itemView.findViewById(R.id.tvGroup)
        private val clLayout: ConstraintLayout = itemView.findViewById(R.id.clCL)

        fun bind(student: Student) {
            this.student=student
            fioTextView.text="${student.lastName} ${student.firstName} ${student.middleName}"
            groupTextView.text=student.group
            ageTextView.text=student.age.toString()
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            callbacks?.onStudentSelected(student.id)
        }
        override fun onLongClick(v: View?): Boolean {

            return true
        }
    }

    private fun updateUI(students: List<Student>){
        if (students==null) return
        adapter=StudentsListAdapter(students)
        studentListRecyclerView.adapter = adapter
    }

    interface Callbacks {
        fun onStudentSelected(studentId: UUID)
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
