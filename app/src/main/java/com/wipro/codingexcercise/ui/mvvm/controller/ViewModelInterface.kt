package com.wipro.codingexcercise.ui.mvvm.controller

import com.wipro.codingexcercise.base.BaseInterface
import com.wipro.codingexcercise.model.Row

interface ViewModelInterface : BaseInterface {

    fun setFacts(title: String, rowsList: ArrayList<Row>)

    fun setMessage(msg: String)

    fun getFacts()
}
