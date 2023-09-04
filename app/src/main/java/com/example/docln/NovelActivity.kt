package com.example.docln

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.example.docln.ui.theme.DocLNTheme

class NovelActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocLNTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = NovelViewModel()
                    viewModel.getNovelDetail()
                    val res = viewModel.novelDetailResponse
                    println("in activity:")
                    println(res)
                    Greeting(res)
                }
            }
        }
    }
}



@Composable
fun Greeting(novel : String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val intent = activity?.intent
    val id = intent?.getIntExtra("novelID", 1)

    Column {
        Text(
            text = "Novel ID is: $id!",
            modifier = modifier
        )
        Text(
            text = "Novel name is: " + novel,
            modifier = modifier
        )
//        LazyColumn {
//            itemsIndexed(items = truyen.dsChuong, key = {index, chapter -> chapter.id_chuong}) {
//                    index, item -> ChapterList(chapter = item)
//            }
//        }
    }



}

@Composable
fun ChapterList(chapter: Chapter, modifier: Modifier = Modifier) {
    Row {
        Text(
            text = chapter.STT.toString(),
            modifier = modifier
        )
        Text(text = chapter.ten_chuong)
    }
}


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    DocLNTheme {
        val viewModel = NovelViewModel()
        viewModel.getNovelDetail()
        val res = viewModel.novelDetailResponse
        Greeting(novel = res)
    }
}