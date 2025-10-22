package com.pandroid.vijayiwfhassignment.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pandroid.vijayiwfhassignment.core.State
import com.pandroid.vijayiwfhassignment.data.model.Title
import com.pandroid.vijayiwfhassignment.ui.navigation.LocalNavigationProvider
import com.pandroid.vijayiwfhassignment.ui.navigation.Routes
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    val context = LocalContext.current
    val state by viewModel.homeState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Movies", "TV Shows")

    val navController = LocalNavigationProvider.current

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 8.dp,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTab])
                        .height(3.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.Medium) }
                )
            }
        }

        when (val uiState = state) {
            is State.Loading -> {
                val shimmerInstance = rememberShimmer(ShimmerBounds.Window)
                ShimmerListPlaceholder(shimmer = shimmerInstance)
            }

            is State.Success -> {
                val (movies, shows) = uiState.data
                val currentList = if (selectedTab == 0) movies else shows
                MediaList(
                    list = currentList,
                    onItemClick = { navController.navigate(Routes.Details(it.id)) })
            }

            is State.Error -> {
                Toast.makeText(
                    context,
                    uiState.message ?: "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }
}

@Composable
fun MediaList(list: List<Title>, onItemClick: (Title) -> Unit) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        userScrollEnabled = true,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list, key = { it.id }) { item ->
            MediaListItem(item = item, onItemClick = onItemClick)
        }
    }
}

@Composable
fun MediaListItem(item: Title, onItemClick: (Title) -> Unit) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${item.tmdb_id}"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                modifier = Modifier
                    .size(90.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Year: ${item.year}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ShimmerListPlaceholder(shimmer: Shimmer) {
    val shimmerColor = Color.Gray.copy(alpha = 0.4f)
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .shimmer(shimmer)
                    .background(shimmerColor),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(shimmerColor)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(0.6f)
                            .background(shimmerColor)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.4f)
                            .background(shimmerColor)
                    )
                }
            }
        }
    }
}
