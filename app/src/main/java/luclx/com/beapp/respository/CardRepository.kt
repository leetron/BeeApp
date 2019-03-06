package luclx.com.beapp.respository

import androidx.lifecycle.LiveData
import io.reactivex.Completable
import luclx.com.beapp.data.local.dao.CardDao
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.data.model.entity.Card
import luclx.com.beapp.data.remote.ApiService
import luclx.com.beapp.data.remote.NetworkBoundResourceNoneLocal
import luclx.com.beapp.data.remote.Resource
import luclx.com.beapp.respository.request.ICardRequest
import retrofit2.Call
import javax.inject.Inject

class CardRepository @Inject constructor(
	val iDao: CardDao,
	val iApi: ApiService
) : ICardRequest {
	override fun getCardPage(
		page: Int,
		needLocalData: Boolean
	): LiveData<Resource<List<CardEntity>>> {
		return object : NetworkBoundResourceNoneLocal<List<CardEntity>, List<Card>>() {
			override fun needLoadLocal(): Boolean {
				return needLocalData
			}

			override fun loadFromDb(): LiveData<List<CardEntity>> {
				return iDao.loadCard()
			}

			override fun createCall(): Call<List<Card>> {
				return iApi.loadCardPage(page)
			}

			override fun convertData(item: List<Card>): List<CardEntity> {
				val resultList = mutableListOf<CardEntity>()
				item.forEach {
					resultList.add(it.toCardEntity())
				}

				return resultList
			}
		}.asLiveData()
	}

	override fun addCard(card: CardEntity): Completable {
		return Completable.create {
			iDao.insertCard(card)
			it.onComplete()
		}
	}
}