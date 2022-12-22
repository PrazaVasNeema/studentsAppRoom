package com.example.appstudents.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.GregorianCalendar
import java.util.UUID

@Entity
data class Student(
    @PrimaryKey var id : UUID =  UUID.randomUUID(),
    var lastName: String="",
    var firstName: String="",
    var middleName: String="",
    var birthDate : Date= Date(),
    var faculty : String="",
    var group : String=""
    )
{


    val age : Int
       get(){
          val gregorianCalendar1 = GregorianCalendar()
           gregorianCalendar1.timeInMillis=birthDate.time
          val gregorianCalendar2 = GregorianCalendar()
          var y=gregorianCalendar2.get(GregorianCalendar.YEAR)-gregorianCalendar1.get(GregorianCalendar.YEAR)
          if (gregorianCalendar1.get(GregorianCalendar.MONTH)<gregorianCalendar2.get(GregorianCalendar.MONTH))
              y--
          return y
       }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        if (id != other.id) return false
        if (lastName != other.lastName) return false
        if (firstName != other.firstName) return false
        if (middleName != other.middleName) return false
        if (birthDate != other.birthDate) return false
        if (faculty != other.faculty) return false
        if (group != other.group) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + middleName.hashCode()
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + faculty.hashCode()
        result = 31 * result + group.hashCode()
        return result
    }

    override fun toString(): String {
        return "Student(id=$id, lastName='$lastName', firstName='$firstName', middleName='$middleName', birthDate=$birthDate, faculty='$faculty', group='$group')"
    }

}

