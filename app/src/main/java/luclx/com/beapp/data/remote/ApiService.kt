package luclx.com.beapp.data.remote

import luclx.com.beapp.data.model.entity.Card
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
	@GET("cards/{page}")
	fun loadCardPage(@Path("page") page: Int): Call<List<Card>>
}