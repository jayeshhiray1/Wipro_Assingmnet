package com.wipro.codingexcercise.utils

/**
 * @author jayeshhiray
 * ALL the constants all over the class
 */
class Constants {
    /**
     * ALL the bundle keys should be define here
     */
    interface BundleKeys {
        companion object {
            const val KEY_FACT = "fact"
        }
    }

    /**
     * all the endpoints must be define here
     */
    interface UrlConstants {
        companion object {
            const val BaseUrl = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"
        }
    }
}