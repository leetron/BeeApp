package luclx.com.beapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import luclx.com.beapp.data.local.entity.CardEntity
import luclx.com.beapp.utils.LiveDataTestUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class CardDaoTest : DbUnitTest() {
	@get:Rule
	var instantTaskExecutorRule = InstantTaskExecutorRule()

	@Test
	fun testLoadCards() {
		val card = CardEntity(
			(100..100000).random(),
			"test",
			"test",
			"test@gmail.com",
			"foo1",
			"test",
			"test",
			"test",
			"test",
			"test",
			"test",
			"test"
		)
		appDatabase.cardDao().insertCard(card)

		val cardsInserted = getValue(appDatabase.cardDao().loadCard())
		MatcherAssert.assertThat(cardsInserted, notNullValue())

		val cardResult = cardsInserted[0]
		MatcherAssert.assertThat(cardResult.email, `is`("test@gmail.com"))
		MatcherAssert.assertThat(cardResult.first_name, `is`("foo1"))
	}

	@Test
	fun createIfNotExits_exist() {
		val card = CardEntity(
			(100..100000).random(),
			"test",
			"test",
			"test@gmail.com",
			"foo1",
			"test",
			"test",
			"test",
			"test",
			"test",
			"test",
			"test"
		)
		appDatabase.cardDao().insertCard(card)
		MatcherAssert.assertThat(appDatabase.cardDao().insertCardIfNotExist(card), `is`(-1L))
	}
}