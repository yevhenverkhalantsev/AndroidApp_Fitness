package com.example.fitness.utils

import android.content.Context
import androidx.fragment.app.Fragment

class UserManager {
    companion object {
        var userId : Int = -1

        fun Fragment.logOut() {
            userId = -1
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit()
                .putInt(Utils.userIdKey, -1)
                .apply()
        }
    }
}