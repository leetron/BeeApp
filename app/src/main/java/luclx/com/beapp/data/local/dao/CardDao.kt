package luclx.com.beapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import luclx.com.beapp.data.local.entity.CardEntity

@Dao
interface CardDao {
	@Query("SELECT * FROM card ORDER BY card.isMind DESC")
	fun loadCard(): LiveData<List<CardEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertCard(cardEntity: CardEntity)

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insertCardIfNotExist(cardEntity: CardEntity): Long
}