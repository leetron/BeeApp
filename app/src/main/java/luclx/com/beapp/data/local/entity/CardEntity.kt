package luclx.com.beapp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "card")
data class CardEntity(
    @PrimaryKey val id: Int,
    val address: String?,
    val avatar: String?,
    val email: String?,
    val first_name: String?,
    val gender: String?,
    val last_name: String?,
    val mobile: String?,
    val position: String?,
    val about: String?,
    val company: String?,
    val dob: String?,
    val isMind: Boolean = false
) : Parcelable {
    fun getFullName() = (first_name ?: "") + " " + (last_name ?: "")

    fun getPhone() = "Mobile: " + (mobile ?: "N/A")

    fun getAddressEd() = "Address: " + (address ?: "N/A")

    fun getCompanyEd() = "Company: " + (company ?: "N/A")

    fun getPositionEd() = "Position: " + (position ?: "N/A")
}