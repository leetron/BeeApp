package luclx.com.beapp.data.remote

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.stream.MalformedJsonException
import io.reactivex.Completable
import luclx.com.beapp.utils.RxUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class NetworkBoundResource<DBType, APIType>
@MainThread constructor() {

    private val result = MediatorLiveData<Resource<DBType>>()

    init {
        result.value = Resource.loading(null)

        // Always load the data from DB
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            fetchFromNetwork(dbSource)
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<DBType>) {
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        createCall().enqueue(object : Callback<APIType> {
            override fun onResponse(call: Call<APIType>, response: Response<APIType>) {
                result.removeSource(dbSource)
                response.body()?.let {
                    saveResultAndReturnIt(it)
                }
            }

            override fun onFailure(call: Call<APIType>, t: Throwable) {
                result.removeSource(dbSource)
                result.addSource(dbSource) { newData ->
                    result.setValue(
                        Resource.error(
                            getCustomErrorMessage(t),
                            newData
                        )
                    )
                }
            }
        })
    }

    @SuppressLint("CheckResult")
    @MainThread
    private fun saveResultAndReturnIt(response: APIType) {
        val task = Completable.create {
            // some action need save local
            saveCallResult(response)
            it.onComplete()
        }
        task.compose(RxUtil.applyCompletableSchedulers())
            .subscribe {
                result.addSource(loadFromDb()) { newData ->
                    newData?.let {
                        result.value = Resource.success(newData)
                    }
                }
            }
    }

    private fun getCustomErrorMessage(error: Throwable): String {
        return when (error) {
            is SocketTimeoutException -> "Request time out"
            is MalformedJsonException -> "Malformed json"
            is IOException -> "Network exception"
            is HttpException -> error.response().message()
            else -> "Something went wrong"
        }
    }

    fun asLiveData() = result as LiveData<Resource<DBType>>

    @WorkerThread
    protected abstract fun saveCallResult(item: APIType)

    @MainThread
    protected abstract fun loadFromDb(): LiveData<DBType>

    @MainThread
    protected abstract fun createCall(): Call<APIType>
}