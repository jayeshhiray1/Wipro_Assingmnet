package com.wipro.codingexcercise.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wipro.codingexcercise.HomeActivity
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.adapter.ViewHolders.FactViewHolder
import com.wipro.codingexcercise.ui.mvvm.view.MvvmFactFragment
import com.wipro.codingexcercise.utils.CommonUtility
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith




@RunWith(AndroidJUnit4::class)
@LargeTest
class MvvmFactFragmentUITest {

    val homeActivityRule = ActivityTestRule(HomeActivity::class.java, true, true)


   /* @Mock
    var viewmodel: FactViewModel = Mockito.mock(FactViewModel::class.java)*/

    private lateinit var instrumentationCtx: Context

    /* @Rule
     var fragmentTestRule = MvvmFactFragment.create(MvvmFactFragment::class.java)*/

    @Rule
    fun rule() = homeActivityRule

    @Before
    fun setUp() {
        rule().activity.loadMVVMFragment(MvvmFactFragment.instance)
        instrumentationCtx = InstrumentationRegistry.getContext()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testInternetConnection() {

        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(message: Message) {
                if (CommonUtility.isInternetAvailable(instrumentationCtx as AppCompatActivity)) {
                    Toast.makeText(instrumentationCtx, "Internet Available", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(instrumentationCtx, "No Internet Available", Toast.LENGTH_SHORT).show()
                }
                Handler().postDelayed(Runnable { }, 5000)

            }
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun testGetListData() {

        //viewmodel.getFacts()

        assertTrue(!homeActivityRule.activity.isFinishing)

        onData(withId(R.id.root))
                .inAdapterView(withId(R.id.cardViewRoot))
                .atPosition(0)
                .perform(click())
    }


    @Test
    fun factList_CheckValueAtPosition() {

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewFact)).perform(RecyclerViewActions.scrollToPosition<FactViewHolder>(1))
        Espresso.onView(
                CoreMatchers.allOf(ViewMatchers.withId(R.id.textViewTitle), ViewMatchers.withText("Flag")))

    }


    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}