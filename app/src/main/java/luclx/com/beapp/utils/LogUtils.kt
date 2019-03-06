package luclx.com.beapp.utils

import android.util.Log

class LogUtils {
	companion object {
		fun v(tag: String, msg: String) {
			debugMode {
				Log.d(tag, msg)
			}
		}

		fun v(tag: String, msg: String, e: Exception) {
			debugMode {
				Log.d(tag, msg, e)
			}
		}

		fun v(tag: String, msg: String, e: OutOfMemoryError) {
			debugMode {
				Log.d(tag, msg, e)
			}
		}

		fun reportException(e: Exception) {
			debugMode {
				Log.e("Exception", e.toString(), e)
			}
		}
	}
}