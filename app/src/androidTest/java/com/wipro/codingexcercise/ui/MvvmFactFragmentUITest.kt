package com.wipro.codingexcercise.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wipro.codingexcercise.HomeActivity
import com.wipro.codingexcercise.R
import com.wipro.codingexcercise.adapter.ViewHolders.FactViewHolder
import com.wipro.codingexcercise.ui.mvvm.view.MvvmFactFragment
import com.wipro.codingexcercise.ui.mvvm.viewmodel.FactViewModel
import com.wipro.codingexcercise.utils.CommonUtility
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
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
                if (CommonUtility.isInternetAvailable()) {
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

    /*   fun tapRecyclerViewItem(recyclerViewId: Int, position: Int) {
         onView(withId(recyclerViewId)).perform(scrollToPosition(position))
       onView(withRecyclerView(recyclerViewId).atPosition(position)).perform(click())

         onView(withId(R.id.recyclerViewFact))
                 .perform(RecyclerViewActions.scrollToPosition(activity.recyclerView.getAdapter().getItemCount() - 1));
   } */


/*
    @Test
    fun factList_CheckValueAtPosition() {

        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewFact)).perform(RecyclerViewActions.scrollToPosition<FactViewHolder>(1))
        Espresso.onView(
                CoreMatchers.allOf(ViewMatchers.withId(R.id.textViewTitle), ViewMatchers.withText("Flag")))

        //special position
        Espresso.onView(
                CoreMatchers.allOf(ViewMatchers.withId(R.id.tv_repository_name),
                        childAtPosition(
                                CoreMatchers.allOf(ViewMatchers.withId(R.id.rl_item_container),
                                        childAtPosition(
                                                ViewMatchers.withId(R.id.rv_repositories_list),
                                                3)), // if not visible will not pass // 3 is the position on screen not adapter
                                0),
                        ViewMatchers.isDisplayed())).check(ViewAssertions.matches(ViewMatchers.withText("Array")))
    }

    @Test
    fun factList_CheckValueAtInvisiblePosition() {
        //Special position using RecyclerViewActions
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewFact)).perform(RecyclerViewActions.scrollToPosition<FactViewHolder>(1))
        Espresso.onView(
                CoreMatchers.allOf(ViewMatchers.withId(R.id.textViewTitle), ViewMatchers.withText("Flag")))

        val recyclerView = repositoriesActivityRule.activity.findViewById(R.id.rv_repositories_list) as RecyclerView
        assertTrue(recyclerView.adapter.itemCount > 0)
    }

    @Test
    fun navigateToDetailsScreen() {

        Espresso.onView(
                CoreMatchers.allOf(ViewMatchers.withId(R.id.rv_repositories_list),
                        childAtPosition(
                                CoreMatchers.allOf(ViewMatchers.withId(R.id.swipe_container),
                                        childAtPosition(
                                                ViewMatchers.withClassName(CoreMatchers.`is`("android.widget.LinearLayout")),
                                                0)),
                                0),
                        ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_repositories_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

    }*/

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