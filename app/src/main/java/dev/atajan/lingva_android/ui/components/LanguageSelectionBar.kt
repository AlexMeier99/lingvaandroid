package dev.atajan.lingva_android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.atajan.lingva_android.api.entities.LanguageEntity

@Composable
fun LanguageSelectionBar(
    supportedLanguages: MutableState<List<LanguageEntity>>,
    sourceLanguage: MutableState<LanguageEntity>,
    targetLanguage: MutableState<LanguageEntity>,
    toggleErrorDialogState: (Boolean) -> Unit,
    onSwapLanguageTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sourceLanguagesPopUpShown = remember { mutableStateOf(false) }
    val targetLanguagesPopUpShown = remember { mutableStateOf(false) }

    Row(
        modifier = modifier.height(50.dp).fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(130.dp)
                .clickable {
                    if (supportedLanguages.value.isNotEmpty()) {
                        sourceLanguagesPopUpShown.value = true
                    } else {
                        toggleErrorDialogState(true)
                    }
                },
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = sourceLanguage.value.name,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
        }

        IconButton(
            onClick = onSwapLanguageTap,
            modifier = Modifier.fillMaxHeight().width(60.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.SwapHoriz,
                contentDescription = "Toggle app theme.",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize()
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(130.dp)
                .clickable {
                    if (supportedLanguages.value.isNotEmpty()) {
                        targetLanguagesPopUpShown.value = true
                    } else {
                        toggleErrorDialogState(true)
                    }
                },
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = targetLanguage.value.name,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }

    LanguageListPopUp(
        openDialog = sourceLanguagesPopUpShown,
        languageList = supportedLanguages.value
    ) { selectedLanguage: LanguageEntity ->
        sourceLanguage.value = selectedLanguage
    }

    // Drop the first language, "Detect", since it won't make sense for target language
    LanguageListPopUp(
        openDialog = targetLanguagesPopUpShown,
        languageList = supportedLanguages.value.drop(1)
    ) { selectedLanguage: LanguageEntity ->
        targetLanguage.value = selectedLanguage
    }
}
