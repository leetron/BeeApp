package luclx.com.beapp.api

import luclx.com.beapp.data.remote.ApiService
import luclx.com.beapp.data.remote.ServerConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class APIUnitTest {
	private var apiService: ApiService? = null

	@Before
	fun createService() {
		val okHttpClient = OkHttpClient.Builder()
		okHttpClient.connectTimeout(ServerConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
		okHttpClient.readTimeout(ServerConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
		okHttpClient.writeTimeout(ServerConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
		okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

		apiService = Retrofit.Builder()
			.baseUrl(ServerConfig.SERVER_HOST)
			.addConverterFactory(GsonConverterFactory.create())
			.client(okHttpClient.build())
			.build()
			.create(ApiService::class.java)
	}

	@Test
	fun getCards() {
		val response1 = apiService?.loadCardPage(1)?.execute()
		assertEquals(response1?.code(), 200)
		val response2 = apiService?.loadCardPage(2)?.execute()
		assertEquals(response2?.code(), 200)
		val response3 = apiService?.loadCardPage(3)?.execute()
		assertEquals(response3?.code(), 200)
		val response4 = apiService?.loadCardPage(4)?.execute()
		assertEquals(response4?.code(), 200)
		val response5 = apiService?.loadCardPage(5)?.execute()
		assertEquals(response5?.code(), 404)
	}
}