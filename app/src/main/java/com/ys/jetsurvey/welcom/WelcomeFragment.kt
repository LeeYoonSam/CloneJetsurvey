package com.ys.jetsurvey.welcom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ys.jetsurvey.Screen
import com.ys.jetsurvey.navigate
import com.ys.jetsurvey.ui.theme.CloneJetsurveyTheme

class WelcomeFragment : Fragment() {
    private val viewModel: WelcomeViewModel by viewModels { WelcomeViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Welcome)
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                CloneJetsurveyTheme {
                    WelcomeScreen(
                        onEvent = { event ->
                            when (event) {
                                WelcomeEvent.ShowJetSurvey -> viewModel.showJetSurvey()
                            }
                        }
                    )
                }
            }
        }
    }
}