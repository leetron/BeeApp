package luclx.com.beapp.data.remote

class Resource<D> constructor(
    val status: Status,
    val data: D?,
    val message: String?
) {
    companion object {

        fun <D> success(data: D?): Resource<D> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <D> error(msg: String, data: D?): Resource<D> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <D> loading(data: D?): Resource<D> {
            return Resource(Status.LOADING, data, null)
        }
    }
}