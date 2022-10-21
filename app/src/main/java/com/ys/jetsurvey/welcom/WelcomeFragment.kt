package com.ys.jetsurvey.welcom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ys.jetsurvey.ui.theme.CloneJetsurveyTheme

class WelcomeFragment : Fragment() {
    private val viewModel: WelcomeViewModel by viewModels { WelcomeViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CloneJetsurveyTheme {
                    WelComeScreen()
                }
            }
        }
    }
}