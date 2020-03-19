package com.wipro.codingexcercise.ui.mvvm.controller

import com.wipro.codingexcercise.base.BaseInterface
import com.wipro.codingexcercise.model.Rows

interface ViewModelInterface : BaseInterface {

    fun setFacts(title: String, rowsList: ArrayList<Rows>)

    fun setMessage(msg: String)

    fun getFacts()
}
