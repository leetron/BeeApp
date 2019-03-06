package luclx.com.beapp.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import luclx.com.beapp.R
import luclx.com.beapp.ui.home.CardListFragment
import luclx.com.beapp.ui.home.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class CardListTest {

	@JvmField
	@Rule
	val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

	@Before
	fun create() {
		mActivityRule.activity.supportFragmentManager.beginTransaction()
			.replace(R.id.container, CardListFragment()).commit()
	}

	@Test
	fun onRecyclerViewClick() {
		Espresso.onView(withId(R.id.rvCards)).perform(click())
	}

	@Test
	fun onSearchViewClick() {
		Espresso.onView(withId(R.id.searchView)).perform(click())
	}
}