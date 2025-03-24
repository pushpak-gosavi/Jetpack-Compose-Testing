package com.pushpak.textfield

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pushpak.textfield.ui.theme.TextFieldTheme
import com.pushpak.textfield.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val map = BuildConfig.GOOGLE_MAP_KEY
        Log.d("local_propertiy", "using the BuildConfig: $map")


        //enableEdgeToEdge()
        setContent {
            TextFieldTheme {
                val mainViewModel: MainViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Compose Animation", fontWeight = FontWeight.Bold) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White
                            )
                        )
                    }) { innerPadding ->
                    Cryprograpicdata(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Cryprograpicdata(modifier: Modifier, mainViewModel: MainViewModel) {
    var textFieldValue by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var flowData by remember { mutableStateOf("") }

    var isVisible by remember { mutableStateOf(false) }
    var isLargeDP by remember { mutableStateOf(false) }
    var isInfiniteStart by remember { mutableStateOf(false) }
    val animatedFloatSize by animateFloatAsState(
        targetValue = if (isVisible) 200f else 100f, label = "Size Float as Sate"
    )
    val animatedDPSize by animateDpAsState(
        targetValue = if (isLargeDP) 200.dp else 100.dp,
        animationSpec = TweenSpec(durationMillis = 1000),
        label = "Animated DP as Sate"
    )
    val infiniteTransition = rememberInfiniteTransition(
        label = "infinite Transition"
    )
    val infiniteTransitionSize by infiniteTransition.animateFloat(
        initialValue = 100f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "infinite Transition"
    )
    Column(
        modifier = modifier
    ) {
        /* TextField(
             value = textFieldValue,
             onValueChange = { textFieldValue = it }
         )
         Spacer(Modifier.height(10.dp))
         Button(
             onClick = {
                 mainViewModel.setDataToCryptoText(textFieldValue)
                 scope.launch {
                     mainViewModel.flowData().collect {
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
         Text(flowData)*/

        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
            isVisible = !isVisible
            isLargeDP = !isLargeDP
            isInfiniteStart = !isInfiniteStart
        }) {
            Text(if(isVisible)"Stop Animations" else "Start Animations")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = isVisible
            ) {
                Text(
                    "I am Visibile Now", fontSize = 30.sp, color = MaterialTheme.colorScheme.primary
                )
            }

            Text(text = "AnimatedVisibility", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .size(size = animatedFloatSize.dp)
                    .background(color = Color.Red)
            )
            Text(text = "AnimatedFloatAsState", fontSize = 12.sp, color = Color.Red)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .size(animatedDPSize)
                    .background(color = Color.Green)
            )

            Text(text = "AnimatedDpAsSate", fontSize = 12.sp, color = Color.Green)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .size(if(isInfiniteStart)infiniteTransitionSize.dp else 100.dp)
                    .background(color = Color.Blue)
            )
            Text(text = "Infinite Animation", fontSize = 12.sp, color = Color.Blue)
        }
    }
}

@PreviewLightDark
@Preview(showSystemUi = true, showBackground = true, uiMode = 1)
@Composable
fun GreetingPreview() {
    TextFieldTheme {
        Cryprograpicdata(modifier = Modifier.padding(8.dp), MainViewModel())
    }

}
