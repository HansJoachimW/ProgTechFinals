package Database

import Model.Movie
import Model.Student
import Model.Teach

class GlobalVar {

    companion object{
        val STORAGE_PERMISSION_CODE: Int = 100
        val READ_EXTERNAL_STORAGE: Int = 100
        val listDataMovie = ArrayList<Movie>()
        val listDataTeach = ArrayList<Teach>()
        val listDataStudent = ArrayList<Student>()
    }
}