package com.ys.jetsurvey.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ys.jetsurvey.R

@Composable
fun SurveyQuestionScreen(
    questions: SurveyState.Questions,
    onAction: (Int, SurveyActionType) -> Unit,
    onDonePressed: () -> Unit,
    onBackPressed: () -> Unit
) {
    val questionState = remember(questions.currentQuestionIndex) {
        questions.questionsState[questions.currentQuestionIndex]
    }
    
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                SurveyTopAppBar(
                    questionIndex = questionState.questionIndex,
                    totalQuestionCount = questionState.totalQuestionsCount,
                    onBackPressed = onBackPressed
                )
            },
            content = { innerPadding ->
                Question(
                    question = questionState.question,
                    answer = questionState.answer,
                    onAnswer = {
                        questionState.answer = it
                        questionState.enableNext = true
                    },
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            },
            bottomBar = {
                SurveyBottomBar(
                    questionState = questionState,
                    onPreviousPressed = { questions.currentQuestionIndex-- },
                    onNextPressed = { questions.currentQuestionIndex++ },
                    onDonePressed = onDonePressed
                )
            }
        )
    }
}

@Composable
fun SurveyResultScreen(
    result: SurveyState.Result,
    onDonePressed: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            content = { innerPadding ->
                val modifier = Modifier.padding(innerPadding)
                SurveyResult(result = result, modifier = modifier)
            },
            bottomBar = {
                OutlinedButton(
                    onClick = { onDonePressed() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        )
    }
}

@Composable
private fun SurveyResult(result: SurveyState.Result, modifier: Modifier = Modifier) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
    ) {
        Spacer(modifier = Modifier.height(44.dp))
        Text(
            text = result.surveyResult.library,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = stringResource(
                result.surveyResult.result,
                result.surveyResult.library
            ),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = stringResource(result.surveyResult.description),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
private fun TopAppBarTitle(
    questionIndex: Int,
    totalQuestionsCount: Int,
    modifier: Modifier = Modifier
) {
    val indexStyle = MaterialTheme.typography.caption.toSpanStyle().copy(
        fontWeight = FontWeight.Bold
    )
    val totalStyle = MaterialTheme.typography.caption.toSpanStyle()
    val text = buildAnnotatedString {
        withStyle(style = indexStyle) {
            append("${questionIndex + 1}")
        }
        withStyle(style = totalStyle) {
            append(stringResource(id = R.string.question_count, totalQuestionsCount))
        }
    }

    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        modifier = modifier,
    )
}

@Composable
private fun SurveyTopAppBar(
    questionIndex: Int,
    totalQuestionCount: Int,
    onBackPressed: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Surface(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TopAppBarTitle(
                    questionIndex = questionIndex,
                    totalQuestionsCount = totalQuestionCount,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )
            }

            Row(horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = onBackPressed,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                }
            }
        }

        LinearProgressIndicator(
            progress = (questionIndex + 1) / totalQuestionCount.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            backgroundColor = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
private fun SurveyBottomBar(
    questionState: QuestionState,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
) {
    Surface(
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if (questionState.showPrevious) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onPreviousPressed
                ) {
                    Text(text = stringResource(id = R.string.previous))
                }
            }

            if (questionState.showDone) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onDonePressed,
                    enabled = questionState.enableNext
                ) {
                    Text(text = stringResource(id = R.string.done))
                }
            } else {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onNextPressed,
                    enabled = questionState.enableNext
                ) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyResultScreenPreview() {
    SurveyResultScreen(
        result = SurveyState.Result(
            surveyTitle = R.string.show_survey,
            surveyResult = SurveyResult(
                library = "test",
                result = R.string.survey_result,
                description = R.string.survey_result_description
            )
        )
    ) {}
}