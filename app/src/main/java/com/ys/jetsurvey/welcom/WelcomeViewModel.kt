package com.ys.jetsurvey.welcom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ys.jetsurvey.Screen
import com.ys.jetsurvey.util.Event

class WelcomeViewModel : ViewModel() {
    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> = _navigateTo

    fun showJetSurvey() {
        _navigateTo.value = Event(Screen.Survey)
    }
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