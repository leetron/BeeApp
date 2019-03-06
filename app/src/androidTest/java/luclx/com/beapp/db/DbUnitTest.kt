package luclx.com.beapp.db

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import luclx.com.beapp.data.local.AppDatabase
import org.junit.After
import org.junit.Before

abstract class DbUnitTest {
	lateinit var appDatabase: AppDatabase

	@Before
	fun createDatabase() {
		appDatabase = Room.inMemoryDatabaseBuilder(
			InstrumentationRegistry.getContext(),
			AppDatabase::class.java
		).build()
	}

	@After
	fun controlZDatabase() {
		appDatabase?.close()
	}
}