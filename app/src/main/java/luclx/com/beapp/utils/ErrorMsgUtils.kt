package luclx.com.beapp.utils

import android.util.Log
import luclx.com.beapp.data.model.entity.Error
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ErrorMsgUtils {
	companion object {
		fun getError(e: Throwable): Error {
			var error = ""
			var code = 0
			try {
				when (e) {
					is HttpException -> {
						error = e.message()
						code = e.code()
						Log.e("ErrorMessage", "HttpException: " + e.message())
					}
					is SocketTimeoutException -> {
						error = "Timeout exception"
						Log.e("ErrorMessage", "time out")
					}
					is IOException -> {
						error = "Network exception"
						Log.e("ErrorMessage", "net error")
					}
					else -> {
						error = e.message.toString()
						Log.e("ErrorMessage", "Another: " + e.localizedMessage)
					}
				}
			} catch (ex: Exception) {
				Log.e("ErrorMessage", ex.localizedMessage)
			}

			return Error(code, error)
		}
	}
}