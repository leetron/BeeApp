package luclx.com.beapp.data.model

import luclx.com.beapp.data.model.entity.Friend

data class CardResponse(
	val _id: String?,
	val about: String?,
	val address: String?,
	val age: Int?,
	val company: String?,
	val email: String?,
	val eyeColor: String?,
	val favoriteFruit: String?,
	val friends: List<Friend>?,
	val gender: String?,
	val greeting: String?,
	val guid: String?,
	val isActive: Boolean?,
	val latitude: Double?,
	val longitude: Double?,
	val name: String?,
	val phone: String?,
	val picture: String?,
	val registered: String?,
	val tags: List<String>?
)