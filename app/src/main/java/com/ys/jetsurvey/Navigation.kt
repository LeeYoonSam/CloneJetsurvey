package com.ys.jetsurvey

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.security.InvalidParameterException

enum class Screen { Welcome, SignUp, SignIn, Survey }

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }

    when (to) {
        Screen.Welcome -> {
            findNavController().navigate(R.id.welcome_fragment)
        }

        Screen.SignUp -> {

        }

        Screen.SignIn -> {

        }

        Screen.Survey -> {
            findNavController().navigate(R.id.survey_fragment)
        }
    }
}