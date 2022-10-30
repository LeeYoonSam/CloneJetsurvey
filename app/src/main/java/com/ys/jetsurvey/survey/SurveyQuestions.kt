package com.ys.jetsurvey.survey

import androidx.annotation.StringRes
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.ys.jetsurvey.R
import dev.chrisbanes.accompanist.coil.CoilImage

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
            is PossibleAnswer.SingleChoice -> SingleChoiceQuestion(
                possibleAnswer = question.answer,
                answer = answer as Answer.SingleChoice?,
                onAnswerSelected = { answer -> onAnswer(Answer.SingleChoice(answer)) },
                modifier = Modifier.fillMaxWidth()
            )
            is PossibleAnswer.MultipleChoice -> MultipleChoiceQuestion(
                possibleAnswer = question.answer,
                answer = answer as Answer.MultipleChoice?,
                onAnswerSelected = { newAnswer, selected ->
                    // 답변이 존재하지 않는 경우 생성하거나 사용자의 선택에 따라 업데이트
                    if (answer == null) {
                        onAnswer(Answer.MultipleChoice(setOf(newAnswer)))
                    } else {
                        onAnswer(answer.withAnswerSelected(newAnswer, selected))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            is PossibleAnswer.Action -> ActionQuestion(
                questionId = question.id,
                possibleAnswer = question.answer,
                answer = answer as Answer.Action?,
                onAction = onAction,
                modifier = Modifier.fillMaxWidth()
            )
            is PossibleAnswer.Slider -> SliderQuestion(
                possibleAnswer = question.answer,
                answer = answer as Answer.Slider?,
                onAnswerSelected = { onAnswer(Answer.Slider(it)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SingleChoiceQuestion(
    possibleAnswer: PossibleAnswer.SingleChoice,
    answer: Answer.SingleChoice?,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswer.optionsStringRes.associateBy { stringResource(id = it) }

    val radioOptions = options.keys.toList()

    val selected = if (answer != null) {
        stringResource(id = answer.answer)
    } else {
        null
    }

    val (selectedOption, onOptionSelected) = remember(answer) { mutableStateOf(selected) }

    Column(modifier = modifier) {
        radioOptions.forEach { text ->
            val onClickHandle = {
                onOptionSelected(text)
                options[text]?.let { onAnswerSelected(it) }
                Unit
            }

            val optionSelected = text == selectedOption
            Surface(
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = optionSelected,
                            onClick = onClickHandle
                        )
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = text)

                    RadioButton(
                        selected = optionSelected,
                        onClick = onClickHandle,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun MultipleChoiceQuestion(
    possibleAnswer: PossibleAnswer.MultipleChoice,
    answer: Answer.MultipleChoice?,
    onAnswerSelected: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswer.optionsStringRes.associateBy { stringResource(id = it) }
    Column(modifier = modifier) {
        for (option in options) {
            var checkedState by remember(answer) {
                val selectedOption = answer?.answerStringRes?.contains(option.value)
                mutableStateOf(selectedOption ?: false)
            }

            Surface(
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                checkedState = !checkedState
                                onAnswerSelected(option.value, checkedState)
                            }
                        )
                        .padding(vertical = 16.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = option.key)

                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = { selected ->
                            checkedState = selected
                            onAnswerSelected(option.value, selected)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionQuestion(
    questionId: Int,
    possibleAnswer: PossibleAnswer.Action,
    answer: Answer.Action?,
    onAction: (Int, SurveyActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    when (possibleAnswer.actionType) {
        SurveyActionType.PICK_DATE -> {
            DateQuestion(
                questionId = questionId,
                answerLabel = possibleAnswer.label,
                answer = answer,
                onAction = onAction,
                modifier = modifier
            )
        }
        SurveyActionType.TAKE_PHOTO -> {
            PhotoQuestion(
                questionId = questionId,
                answer = answer,
                onAction = onAction,
                modifier = modifier
            )
        }
        SurveyActionType.SELECT_CONTACT -> TODO()
    }
}

@Composable
private fun DateQuestion(
    questionId: Int,
    @StringRes answerLabel: Int,
    answer: Answer.Action?,
    onAction: (Int, SurveyActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onAction(questionId, SurveyActionType.PICK_DATE) },
        modifier = modifier.padding(vertical = 20.dp)
    ) {
        Text(text = stringResource(id = answerLabel))
    }

    if (answer != null && answer.result is SurveyActionResult.Date) {
        Text(
            text = stringResource(id = R.string.selected_date, answer.result.date),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(vertical = 20.dp)
        )
    }
}

@Composable
private fun PhotoQuestion(
    questionId: Int,
    answer: Answer.Action?,
    onAction: (Int, SurveyActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    val resource = if (answer != null) {
        Icons.Filled.SwapHoriz
    } else {
        Icons.Filled.AddAPhoto
    }

    OutlinedButton(
        onClick = { onAction(questionId, SurveyActionType.TAKE_PHOTO) },
        modifier = modifier,
        contentPadding = PaddingValues()
    ) {
        Column {
            if (answer != null && answer.result is SurveyActionResult.Photo) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(answer.result.uri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                PhotoDefaultImage(
                    modifier = Modifier.padding(horizontal = 86.dp, vertical = 74.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomCenter)
                    .padding(vertical = 26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = resource, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(
                        id = if (answer != null) {
                            R.string.retake_photo
                        } else {
                            R.string.add_photo
                        }
                    )
                )
            }
        }
    }
}

@Composable
private fun PhotoDefaultImage(
    lightTheme: Boolean = MaterialTheme.colors.isLight,
    modifier: Modifier = Modifier
) {
    val assetId = if (lightTheme) {
        R.drawable.ic_selfie_light
    } else {
        R.drawable.ic_selfie_dark
    }
    Image(
        painter = painterResource(id = assetId),
        contentDescription = "PhotoDefault",
        modifier = modifier
    )
}

@Composable
private fun SliderQuestion(
    possibleAnswer: PossibleAnswer.Slider,
    answer: Answer.Slider?,
    onAnswerSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderPosition by remember {
        mutableStateOf(answer?.answerValue ?: possibleAnswer.defaultValue)
    }

    Row(modifier = modifier) {
        Text(
            text = stringResource(id = possibleAnswer.startText),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onAnswerSelected(it)
            },
            valueRange = possibleAnswer.range,
            steps = possibleAnswer.steps,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(id = possibleAnswer.endText),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}