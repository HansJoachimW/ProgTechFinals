package Database

import Model.Movie

class GlobalVar {

    companion object{
        val STORAGE_PERMISSION_CODE: Int = 100
        val READ_EXTERNAL_STORAGE: Int = 100
        val listDataMovie = ArrayList<Movie>()
    }
}