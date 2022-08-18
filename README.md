# Mini Stock App

This project is a skeleton version of Trading App

### Features

- Stock watchlist
- Realtime price
- Trending Articles

### Tech Stack

- Jetpack Compose <img src="https://img.shields.io/badge/kotlin-v1.6.0-blue" alt="kotlin"/>
- Hilt Dependency Injection <a href="https://developer.android.com/training/dependency-injection/hilt-android"><img src="https://img.shields.io/badge/hilt-v2.38.1-blue" alt="Hilt"/></a>
- Kotlin Coroutines and Flow <img src="https://img.shields.io/badge/coroutines-v1.6.0-blue" alt="coroutines"/>
- CSV Reader <a href="https://github.com/doyaaaaaken/kotlin-csv"><img src="https://img.shields.io/badge/csv%20reader-v1.6.0-blue" alt="csv reader"/></a> : To ease the process of reading and parsing csv file content
- Compose Snapper <a href="https://chrisbanes.github.io/snapper/"><img src="https://img.shields.io/badge/snapper-v0.2.2-blue" alt="snapper"/></a>: To allow item snapping for LazyRow and LazyColumn of Jetpack Compose
- Composable Graph <a href="https://github.com/jaikeerthick/Composable-Graphs"><img src="https://img.shields.io/badge/composable%20graph-v1.6.0-blue" alt="mockk"/></a>
- Coil Compose <a href="https://coil-kt.github.io/coil/compose/"><img src="https://img.shields.io/badge/coil%20compose-v2.2.0-blue" alt="coil"/></a>
- Mockk for Unit Testing <img src="https://img.shields.io/badge/mockk-v1.12.5-blue" alt="mockk"/>
- MVVM and Clean Architecture
- Kotlin DSL for Gradle

#### Note

- The [provided csv file](https://raw.githubusercontent.com/dsancov/TestData/main/stocks.csv) is slightly modified to allow CSV Reader parse it correctly. </br>
  from:</br>
  <img src="https://raw.githubusercontent.com/muzafakar/mini-stock-app/master/screenshots/old_csv_format.png" /></br>
  to:</br>
  <img src="https://raw.githubusercontent.com/muzafakar/mini-stock-app/master/screenshots/new_csv_format.png" />
- The chart will only show latest 30 prices
