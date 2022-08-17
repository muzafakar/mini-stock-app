package com.yildiz.ministockapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yildiz.ministockapp.R

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,

        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item { StockSection() }
            item { Spacer(modifier = Modifier.size(20.dp)) }
            item { ArticleSection() }
            item { Spacer(modifier = Modifier.size(20.dp)) }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(imageVector = Icons.Sharp.Menu, contentDescription = null)
        AsyncImage(
            model = "https://avatars.githubusercontent.com/u/27369892?v=4",
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.placeholder),
        )
    }
}

@Composable
fun BottomBar() {
    val selectedItemIndex = remember { mutableStateOf(0) }
    val navigationIcons = listOf(
        R.drawable.home,
        R.drawable.trending_up,
        R.drawable.search,
        R.drawable.setting,
    )

    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
    ) {
        navigationIcons.forEachIndexed { index, icons ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = icons),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colors.primary
                    )
                },
                onClick = { selectedItemIndex.value = index },
                selected = true
            )
        }
    }
}