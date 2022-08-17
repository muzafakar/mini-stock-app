package com.yildiz.ministockapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.yildiz.ministockapp.model.Article
import com.yildiz.ministockapp.util.getFormattedDate
import com.yildiz.ministockapp.viewModel.ArticleViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun ArticleSection(articleViewModel: ArticleViewModel = viewModel()) {
    val uiState = articleViewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        articleViewModel.loadGroupedArticles()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Trending",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        when (uiState.value) {
            is ArticleViewModel.UiState.DataLoaded -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                    state = lazyListState,
                    flingBehavior = rememberSnapperFlingBehavior(lazyListState),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val articleGroups = (uiState.value as ArticleViewModel.UiState.DataLoaded).data
                    items(articleGroups) {
                        ArticleGroup(articles = it)
                    }
                }

            }
            ArticleViewModel.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }
        }
    }

}

@Composable
private fun ArticleGroup(articles: List<Article>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(300.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {
        articles.forEach {
            ArticleItem(article = it)
            Divider()
        }
    }
}

@Composable
private fun ArticleItem(article: Article, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .height(72.dp)
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = article.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            val caption =
                listOf(article.source.name, article.getFormattedDate()).joinToString(" • ")
            Text(text = caption, fontWeight = FontWeight.Normal, fontSize = 10.sp)
        }

        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop
        )
    }
}