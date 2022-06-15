package Model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    var title:String?,
    var rating: String?,
    var genre:String?,
    var prodComp:String?,
    var synopsis:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    var imageUri:String?=""

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(rating)
        parcel.writeString(genre)
        parcel.writeString(prodComp)
        parcel.writeString(synopsis)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}




