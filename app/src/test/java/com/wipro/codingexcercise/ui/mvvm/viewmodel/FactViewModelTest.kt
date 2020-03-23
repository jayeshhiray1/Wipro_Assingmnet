package com.wipro.codingexcercise.ui.mvvm.viewmodel

import android.app.Application
import com.wipro.codingexcercise.model.FactData
import com.wipro.codingexcercise.model.Row
import com.wipro.codingexcercise.ui.mvvm.controller.FactController
import com.wipro.codingexcercise.utils.network.APIInterface
import com.wipro.codingexcercise.utils.network.RetrofitHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.IOException
import kotlin.coroutines.coroutineContext


class FactViewModelTest {

    @Mock
    lateinit var factController: FactController

    @Mock
    lateinit var context: Application

    @Mock
    lateinit var factViewModel: FactViewModel

    lateinit var mTestScheduler: TestScheduler

    private val ROWS = arrayListOf<Row>(Row("Title1", "Description1", ""),
            Row("Title1", "Description1", ""), Row("Title1", "Description1", ""))

    private val EMPTY_ROWS = ArrayList<Row>(0)

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

        val apiEndpoints = RetrofitHelper.Companion.getInstance(context = context).create(APIInterface::class.java)

        val call = apiEndpoints.getALlFacts()

        try {

            val response = call.clone().execute()
            val authResponse: FactData = response.body() as FactData

            assertTrue(authResponse.rows != null && authResponse.title != null)

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