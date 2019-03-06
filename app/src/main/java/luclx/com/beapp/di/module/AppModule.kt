package luclx.com.beapp.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import luclx.com.beapp.data.local.AppDatabase
import luclx.com.beapp.data.local.dao.CardDao
import luclx.com.beapp.data.remote.ApiService
import luclx.com.beapp.data.remote.ServerConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * The application module which provides app wide instances of various components
 **/
@Module(includes = [ViewModelModule::class])
class AppModule {
	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val okHttpClientBuilder = OkHttpClient.Builder()
		okHttpClientBuilder.connectTimeout(ServerConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
		okHttpClientBuilder.readTimeout(ServerConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
		okHttpClientBuilder.writeTimeout(ServerConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS)
		okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
		return okHttpClientBuilder.build()
	}

	@Provides
	@Singleton
	fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
		val retrofit = Retrofit.Builder()
			.baseUrl(ServerConfig.SERVER_HOST)
			.addConverterFactory(GsonConverterFactory.create())
			.client(okHttpClient)
			.build()

		return retrofit.create(ApiService::class.java)
	}

	@Provides
	@Singleton
	fun provideAppDatabase(application: Application): AppDatabase =
		Room.databaseBuilder(application, AppDatabase::class.java, "beeapp.db").build()

	@Provides
	@Singleton
	fun provideCardDao(appDatabase: AppDatabase): CardDao = appDatabase.cardDao()
}