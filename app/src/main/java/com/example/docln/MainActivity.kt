package com.example.docln

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.docln.ui.theme.DocLNTheme


class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocLNTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    viewModel.getNovelList()
                    NovelList(viewModel.novelListResponse)
                }
            }
        }
    }
}

@Composable
fun NovelList (novelList: List<Novel>) {
    LazyColumn {
        itemsIndexed(items = novelList, key = {index, novel -> novel.id_truyen}) {
            index, item -> NovelItem(novel = item)
        }
    }
}

@Composable
fun NovelItem (novel: Novel) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(150.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Surface() {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .clickable {
                        val intent = Intent(context, NovelActivity::class.java)
                        intent.putExtra("novelID", novel.id_truyen)
                        context.startActivity(intent)
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(novel.coverImg)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Novel Cover Image",
                    error = painterResource(id = R.drawable.nocover)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = novel.ten_truyen,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}

@Composable
fun ReaderUI() {

    var isVisible = remember { mutableStateOf(true) }
    var backgroundColor = remember { mutableStateOf(Color.White) }
    var fontColor = remember { mutableStateOf(Color.Black) }
    var fontSize = remember { mutableStateOf(24) }
    var fontStyle = remember { mutableStateOf(FontFamily.Default) }

    val imgRegex = """`img`""".toRegex()
    var text = stringResource(R.string.LNContent)
    var result: List<String> = text.split(imgRegex)
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .background(
            color = backgroundColor.value
        )) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .clickable { isVisible.value = !isVisible.value })
        {
            for (p in result) {
                if (p.startsWith("link:")){
                    AsyncImage(
                        model = p.removePrefix("link:"),
                        contentDescription = "null",
                    )
                    Log.e("test",p.removePrefix("link:"))
                }
                else {
                    Text(
                        text = p,
                        style = TextStyle(
                            fontSize = fontSize.value.sp,
                            color = fontColor.value,
                            fontFamily = fontStyle.value
                        )
                    )
                    Log.e("test","Text line")
                }
            }
        }
        if (isVisible.value) {
            Row(modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.Blue))
            {
                CustomReader(backgroundColor, fontColor, fontSize, fontStyle)
            }
        }
    }
}

@Composable
fun CustomReader(background : MutableState<Color>,
                 fontColor : MutableState<Color>,
                 fontSize: MutableState<Int>,
                 fontStyle: MutableState<SystemFontFamily>) {
    //Giao dien
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (background.value == Color.White) {
                background.value = Color.Black
                fontColor.value = Color.White
            }
            else {
                background.value = Color.White
                fontColor.value = Color.Black
            }
        }) {
            Text("Change theme")
        }
        Row {
            Button(onClick = { fontSize.value-- }) {
                Text("-")
            }
            Text(fontSize.value.toString())
            Button(onClick = { fontSize.value++ }) {
                Text("+")
            }
        }
        Row {
            Button(onClick = { fontStyle.value = FontFamily.Serif }) {
                Text("Serif")
            }
            Button(onClick = { fontStyle.value = FontFamily.Cursive }) {
                Text("Cursive")
            }
            Button(onClick = { fontStyle.value = FontFamily.Monospace }) {
                Text("Monospace")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val viewModel = MainViewModel()
    DocLNTheme {
        viewModel.getNovelList()
        NovelList(viewModel.novelListResponse)
    }
}