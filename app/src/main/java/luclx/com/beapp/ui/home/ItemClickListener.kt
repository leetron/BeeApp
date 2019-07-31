package luclx.com.beapp.ui.home

import luclx.com.beapp.data.local.entity.CardEntity

interface ItemClickListener {
    fun onCardClick(cardEntity: CardEntity)
}