package com.ys.jetsurvey.survey

import androidx.compose.runtime.Composable

@Suppress("UNUSED_PARAMETER")
@Composable
fun SurveyQuestionScreen(
    questions: SurveyState.Questions,
    onAction: (Int, SurveyActionType) -> Unit,
    onDonePressed: () -> Unit,
    onBackPressed: () -> Unit
) {

}

@Suppress("UNUSED_PARAMETER")
@Composable
fun SurveyResultScreen(
    result: SurveyState.Result,
    onDonePressed: () -> Unit
) {

}