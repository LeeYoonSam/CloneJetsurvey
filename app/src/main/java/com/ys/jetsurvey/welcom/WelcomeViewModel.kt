package com.ys.jetsurvey.welcom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WelcomeViewModel : ViewModel() {
}

@Suppress("UNCHECKED_CAST")
class WelcomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}