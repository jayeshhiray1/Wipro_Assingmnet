package com.wipro.codingexcercise.base


/**
 * @author JA20049996
 * Its the base interface which provides ,common methods to use by other classes like presenter and interactor and view
 */
interface BaseInterface {
    fun showProgress()
    fun showError(message: String)
    fun hideProgress()
}