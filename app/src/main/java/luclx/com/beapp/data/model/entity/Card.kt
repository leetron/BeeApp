package luclx.com.beapp.data.model.entity

import luclx.com.beapp.data.local.entity.CardEntity

data class Card(
    val address: String?,
    val avatar: String?,
    val email: String?,
    val first_name: String?,
    val gender: String?,
    val id: Int,
    val last_name: String?,
    val mobile: String?,
    val position: String?,
    val about: String?
) {
    fun toCardEntity() = CardEntity(
        id = id,
        avatar = avatar,
        email = email,
        first_name = first_name,
        last_name = last_name,
        gender = gender,
        mobile = mobile,
        position = "N/A",
        about = "N/A",
        company = "N/A",
        address = address,
        dob = "N/A"
    )
}