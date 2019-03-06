package luclx.com.beapp.data.remote

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.stream.MalformedJsonException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * This class provide method load data for only that card is created by my-self
 * Not cache data from API
 */
@Suppress("LeakingThis")
abstract class NetworkBoundResourceNoneLocal<DBType, APIType>
@MainThread constructor() {

	private val result = MediatorLiveData<Resource<DBType>>()

	init {
		// Load card of mind
		if (needLoadLocal()) {

			val dbSource = loadFromDb()
			// load local first
			result.addSource(dbSource) { newData ->
				result.removeSource(dbSource)
				newData?.let {
					fetchFromNetwork(newData)
				}
			}
		} else {
			fetchFromNetwork(null)
		}
	}

	private fun fetchFromNetwork(dataLocal: DBType?) {
		// set loading
		result.value = Resource.loading(dataLocal)

		createCall().enqueue(object : Callback<APIType> {
			override fun onResponse(call: Call<APIType>, response: Response<APIType>) {
				response.body()?.let {
					result.value = Resource.success(convertData(it))
				} ?: kotlin.run {
					result.value = Resource.error("NULL response", null)
				}
			}

			override fun onFailure(call: Call<APIType>, t: Throwable) {
				result.value = Resource.error(getErrorMessage(t), null)
			}
		})
	}

	private fun getErrorMessage(error: Throwable): String {
		return when (error) {
			is SocketTimeoutException -> "Request time out"
			is MalformedJsonException -> "Malformed json"
			is IOException -> "Network exception"
			is HttpException -> error.response().message()
			else -> "Something went wrong"
		}
	}

	fun asLiveData() = result as LiveData<Resource<DBType>>

	@MainThread
	protected abstract fun needLoadLocal(): Boolean

	@MainThread
	protected abstract fun loadFromDb(): LiveData<DBType>

	@MainThread
	protected abstract fun createCall(): Call<APIType>

	@MainThread
	protected abstract fun convertData(item: APIType): DBType
}