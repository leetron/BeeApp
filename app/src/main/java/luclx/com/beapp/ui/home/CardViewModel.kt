package luclx.com.beapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.respository.CardRepository
import javax.inject.Inject

class CardViewModel @Inject constructor(
    private val cardRepository: CardRepository
) : ViewModel() {
    var page = 0

    var loading = false

    var canLoad = true

    fun getCards(
        page: Int,
        needLocalData: Boolean = false
    ) = cardRepository.getCardPage(page, needLocalData)

    fun addCard(card: CardEntity): Completable {
        return cardRepository.addCard(card)
    }

    fun notifyData(): LiveData<CardEntity> {
        return cardRepository.notifyData()
    }
}