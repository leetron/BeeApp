package luclx.com.beapp.respository.request

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.data.remote.Resource

interface ICardRequest {

	fun getCardPage(page: Int, needLocalData: Boolean): LiveData<Resource<List<CardEntity>>>

	fun addCard(card: CardEntity): Completable
}