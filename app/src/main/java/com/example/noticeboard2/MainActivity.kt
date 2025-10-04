package com.example.noticeboard2

import android.R.id.underline
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            NoticeScreen()
        }
    }
}

@Composable
fun NoticeScreen() {
    // Step 1: maintain notice as state
    var currentNotice by remember { mutableStateOf("Tap Generate to show a notice") }

    // Step 2: your list/map of notices
    val notices = listOf(
        "📚 Books found in library",
        "📅 Exams in December",
        "🎉 Fest in February"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
            .background(
                color=androidx.compose.ui.graphics.Color.Black
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Step 3: Notice Box (shows current notice)
        NoticeBox(currentNotice)

        Spacer(modifier = Modifier.height(16.dp))

        // Step 4: Button updates the state with a random notice
        Button("Generate") {
            currentNotice = notices.random()
        }
    }
}

@Composable
fun NoticeBox(notice: String) {
    var today by remember { mutableStateOf("") }

    // Updates date every minute (lightweight enough)
    LaunchedEffect(Unit) {
        while (true) {
            today = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                .format(java.util.Date())
            kotlinx.coroutines.delay(60_000L) // check once per minute
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .background(
                color = Color.Blue,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color=Color.Cyan)
                        .padding(8.dp)
                ){
                    Text(
                        text = today,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "NOTICE",
                        textDecoration = TextDecoration.Underline,
                        fontSize = 30.sp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = notice,
                fontSize = 25.sp,
                color = Color.White
            )
        }

    }
}

@Composable
fun Button(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = Color.Cyan,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}
