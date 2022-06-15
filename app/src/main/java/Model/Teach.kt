package Model

import android.os.Parcel
import android.os.Parcelable

data class Teach(
    var name:String?,
    var email: String?,
    var password:String?,
    var repassword:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }
    var imageUri:String?=""

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(repassword)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Teach> {
        override fun createFromParcel(parcel: Parcel): Teach {
            return Teach(parcel)
        }

        override fun newArray(size: Int): Array<Teach?> {
            return arrayOfNulls(size)
        }
    }
}




