package ui

import GetWordInfoUseCase
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.rememberKoinInject

@Composable
fun App() {
    MaterialTheme {
        val getWordInfoUseCase = rememberKoinInject<GetWordInfoUseCase>()
        val coroutineScope = rememberCoroutineScope()
        val word = remember { mutableStateOf("") }
        val wordInfoResult = remember { mutableStateOf("") }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                value = word.value,
                onValueChange = { word.value = it },
                label = { Text("Enter word") }
            )

            Button(onClick = {
                getWordInfo(
                    word = word.value,
                    getWordInfoUseCase = getWordInfoUseCase,
                    coroutineScope = coroutineScope,
                    wordInfoResult = wordInfoResult
                )
            }) {
                Text("Submit")
            }

            LazyColumn {
                item {
                    Text(wordInfoResult.value, modifier = Modifier.fillMaxWidth().padding(8.dp))
                }
            }
        }
    }
}

private fun getWordInfo(
    word: String,
    coroutineScope: CoroutineScope,
    getWordInfoUseCase: GetWordInfoUseCase,
    wordInfoResult: MutableState<String>
) {
    coroutineScope.launch {
        val wordInfo = getWordInfoUseCase.invoke(word = word)
        if (wordInfo != null) {
            println(wordInfo)
            wordInfoResult.value = wordInfo.toString()
        } else println("wordInfo is null")
    }
}