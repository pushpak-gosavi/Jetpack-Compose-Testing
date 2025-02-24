package com.pushpak.textfield

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.BuildCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pushpak.textfield.ui.theme.TextFieldTheme
import com.pushpak.textfield.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.util.Properties

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapKey = Properties().getProperty("MAPS_API_KEY")

        Log.d("local_propertiy", mapKey)

        val map = BuildConfig.GOOGLE_MAP_KEY
        //enableEdgeToEdge()
        setContent {
            TextFieldTheme {
                val mainViewModel: MainViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Cryprograpicdata(
                        modifier = Modifier.padding(innerPadding),
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Cryprograpicdata(modifier: Modifier, mainViewModel: MainViewModel) {
    var textFieldValue by rememberSaveable  { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var flowData by remember { mutableStateOf("") }
    Column(
        modifier = modifier
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it }
        )
        Spacer(Modifier.height(10.dp))
        Button(
            onClick = {
                mainViewModel.setDataToCryptoText(textFieldValue)
                scope.launch {
                    mainViewModel.flowData().collect{
                        flowData = it.toString()
                    }
                }


            }
        ) {
            Text(text = "Click")
        }
        Spacer(Modifier.height(10.dp))
        mainViewModel.getData().observeAsState().value?.let { Text(text = it) }
        Spacer(Modifier.height(10.dp))
        Text(flowData)
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TextFieldTheme {
        Cryprograpicdata(modifier = Modifier.padding(8.dp), MainViewModel())
    }

}
