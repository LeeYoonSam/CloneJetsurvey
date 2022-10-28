package com.ys.jetsurvey.survey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Suppress("UNUSED_PARAMETER")
@Composable
fun Question(
    question: Question,
    answer: Answer<*>?,
    onAnswer: (Answer<*>) -> Unit,
    onAction: (Int, SurveyActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(PaddingValues(horizontal = 20.dp))
    ) {
        Spacer(modifier = Modifier.height(44.dp))
        val backgroundColor = if (MaterialTheme.colors.isLight) {
            MaterialTheme.colors.onSurface.copy(alpha = 0.04f)
        } else {
            MaterialTheme.colors.onSurface.copy(alpha = 0.06f)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Text(
                text = stringResource(id = question.questionText),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (question.description != null) {
            Text(
                text = stringResource(id = question.description),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 8.dp, end = 8.dp)
            )
        }

        when (question.answer) {
            is PossibleAnswer.SingleChoice -> {}
            is PossibleAnswer.MultipleChoice -> {}
            is PossibleAnswer.Action -> {}
            is PossibleAnswer.Slider -> {}
        }
    }
}