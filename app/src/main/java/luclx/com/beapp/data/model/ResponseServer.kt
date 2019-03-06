package luclx.com.beapp.data.model

class ResponseServer<T> {
	private var httpCode: Int? = 0
	private var httpMessage: String? = null
	private var responseObjectData: T? = null
	private var responseErrorBody: String? = null

	constructor()
	constructor(responseObjectData: T?) {
		this.httpCode = 200
		this.httpMessage = null
		this.responseObjectData = responseObjectData
	}

	constructor(httpCode: Int?, httpMessage: String?, responseObjectData: T?) {
		this.httpCode = httpCode
		this.httpMessage = httpMessage
		this.responseObjectData = responseObjectData
	}

	constructor(httpCode: Int?, httpMessage: String?, responseErrorBody: String?) {
		this.httpCode = httpCode
		this.httpMessage = httpMessage
		this.responseErrorBody = responseErrorBody
	}

	fun setHttpCode(httpCode: Int?) {
		this.httpCode = httpCode
	}

	fun getHttpCode(): Int? {
		return this.httpCode
	}

	fun setHttpMessage(httpMessage: String?) {
		this.httpMessage = httpMessage
	}

	fun getHttpMessage(): String? {
		return this.httpMessage
	}

	fun setResponseObjectData(responseObjectData: T?) {
		this.responseObjectData = responseObjectData
	}

	fun getResponseObjectData(): T? {
		return this.responseObjectData
	}

	fun getErrorBody(): String? {
		return this.responseErrorBody
	}
}