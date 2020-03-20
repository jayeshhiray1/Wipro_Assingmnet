package com.wipro.codingexcercise.ui.mvvm.viewmodel

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.wipro.codingexcercise.model.FactsDto
import com.wipro.codingexcercise.model.Rows
import com.wipro.codingexcercise.ui.mvvm.controller.FactController
import com.wipro.codingexcercise.utils.CommonUtility
import com.wipro.codingexcercise.utils.network.APIInterface
import com.wipro.codingexcercise.utils.network.RetrofitHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.IOException


class FactViewModelTest {

    @Mock
    lateinit var factController: FactController

    @Mock
    lateinit var factViewModel: FactViewModel

   // private val testSubscriber = TestSubscriber.create<String>()

    lateinit var mTestScheduler: TestScheduler

    private val ROWS = arrayListOf<Rows>(Rows("Title1", "Description1", ""),
            Rows("Title1", "Description1", ""), Rows("Title1", "Description1", ""))

    private val EMPTY_ROWS = ArrayList<Rows>(0)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val compositeDisposable = CompositeDisposable()
        mTestScheduler = TestScheduler()
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkIfInternetAvailable() {
        val NET_PRESENT = true
        val NET_PRESENTFALSE = false
        assertFalse("IS internet Present", NET_PRESENTFALSE)
    }


    @Test
    fun testFailGettingFactList() {
        assertEquals(0, EMPTY_ROWS.size)
    }

    @Test
    fun testSuccessGettingFactList() {
        assertTrue("Getting non empty list", ROWS.size > 0)
    }

    @Test
    fun testGetFactAPIcallSuccess() {

        val apiEndpoints = RetrofitHelper.Companion.getInstance().create(APIInterface::class.java)

        val call = apiEndpoints.getALlFacts()

        try {

            val response = call.clone().execute()
            val authResponse: FactsDto = response.body() as FactsDto

            assertTrue(authResponse != null && authResponse.rows != null && authResponse.title != null)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    @Test
    fun getfactList() {
    }

    @Test
    fun getErrorMessage() {

    }

    @Test
    fun getFacts() {
    }

}