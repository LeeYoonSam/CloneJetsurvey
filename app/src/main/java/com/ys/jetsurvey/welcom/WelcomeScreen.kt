package com.ys.jetsurvey.welcom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ys.jetsurvey.R
import com.ys.jetsurvey.ui.theme.CloneJetsurveyTheme

sealed class WelcomeEvent {
    object ShowJetSurvey : WelcomeEvent()
}

@Composable
fun WelcomeScreen(onEvent: (WelcomeEvent) -> Unit) {
    var brandingBottom by remember { mutableStateOf(0f) }
    var showBranding by remember { mutableStateOf(true) }
    var heightWithBranding by remember { mutableStateOf(0) }
    val heightDp = with(LocalDensity.current) { heightWithBranding.toDp()}

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .brandingPreferredHeight(showBranding, heightDp)
                .onSizeChanged {
                    if (showBranding) {
                        heightWithBranding = it.height
                    }
                }
        ) {
            Branding(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .onGloballyPositioned {
                        if (brandingBottom == 0f) {
                            brandingBottom = it.boundsInParent().bottom
                        }
                    }
            )
            JetSurvey(
                onEvent = onEvent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

    }
}

private fun Modifier.brandingPreferredHeight(
    showBranding: Boolean,
    heightDp: Dp
): Modifier {
    return if (!showBranding) {
        this
            .wrapContentHeight(unbounded = true)
            .height(heightDp)
    } else {
        this
    }
}

@Composable
private fun Branding(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Logo(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 76.dp))
        Text(
            text = stringResource(id = R.string.app_tagline),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun Logo(
    lightTheme: Boolean = MaterialTheme.colors.isLight,
    modifier: Modifier = Modifier
) {
    val assetId = if (lightTheme) {
        R.drawable.ic_logo_light
    } else {
        R.drawable.ic_logo_dark
    }
    Image(
        painter = painterResource(id = assetId),
        contentDescription = "Logo",
        modifier = modifier
    )
}

@Composable
private fun JetSurvey(
    onEvent: (WelcomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface {
            OutlinedButton(
                onClick = { onEvent(WelcomeEvent.ShowJetSurvey) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 24.dp),
                shape = CircleShape
            ) {
                Text(text = stringResource(id = R.string.show_survey))
            }
        }
    }
}

@Preview(name = "Welcome light theme")
@Composable
fun WelcomeScreenPreview() {
    CloneJetsurveyTheme {
        WelcomeScreen {}
    }
}

@Preview(name = "Welcome dark theme")
@Composable
fun WelcomeScreenPreviewDark() {
    CloneJetsurveyTheme(darkTheme = true) {
        WelcomeScreen {}
    }
}