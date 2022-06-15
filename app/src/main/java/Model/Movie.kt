package Model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    var classname:String?,
    var amount: String?,
    var major:String?,
    var teacher:String?,
    var description:String?):Parcelable {
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
        parcel.writeString(classname)
        parcel.writeString(amount)
        parcel.writeString(major)
        parcel.writeString(teacher)
        parcel.writeString(description)
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




