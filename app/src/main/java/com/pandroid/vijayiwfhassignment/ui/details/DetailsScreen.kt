package com.pandroid.vijayiwfhassignment.ui.details

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pandroid.vijayiwfhassignment.core.State
import com.pandroid.vijayiwfhassignment.data.model.MediaDetails
import com.pandroid.vijayiwfhassignment.ui.home.HomeViewModel
import com.pandroid.vijayiwfhassignment.ui.navigation.LocalNavigationProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    id: Int,
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val state by homeViewModel.detailState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.getMediaDetails(id)
    }

    when (state) {
        is State.Loading -> {
            val shimmerInstance = rememberShimmer(ShimmerBounds.Window)
            ShimmerEffect(shimmerInstance)
        }

        is State.Success -> DetailView(
            media = (state as State.Success<MediaDetails>).data
        )

        is State.Error -> {
            Toast.makeText(
                context,
                (state as State.Error).message ?: "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
            ErrorText((state as State.Error).message)
        }

        else -> {
            /* Enjoy your Error 😎😎😎*/
        }
    }
}

@Composable
fun ShimmerEffect(shimmer: Shimmer = rememberShimmer(ShimmerBounds.Window)) {
    val scrollState = rememberScrollState()
    val shimmerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Poster placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .shimmer(shimmer)
                .background(shimmerColor)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(28.dp)
                .shimmer(shimmer)
                .background(shimmerColor)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Year & Type placeholders
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(20.dp)
                    .shimmer(shimmer)
                    .background(shimmerColor)
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(20.dp)
                    .shimmer(shimmer)
                    .background(shimmerColor)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Genres placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(20.dp)
                .shimmer(shimmer)
                .background(shimmerColor)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Metadata Card placeholders
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(shimmer)
                .background(shimmerColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .shimmer(shimmer)
                        .background(shimmerColor)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Overview title placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(24.dp)
                .shimmer(shimmer)
                .background(shimmerColor)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Overview content placeholder
        repeat(4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .shimmer(shimmer)
                    .background(shimmerColor)
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun DetailView(media: MediaDetails) {
    val scrollState = rememberScrollState()
    val navController = LocalNavigationProvider.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        Box {
            // Poster
            AsyncImage(
                model = media.poster,
                contentDescription = media.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )

            // Back Button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Content
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = media.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Year: ${media.year}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Type: ${media.type}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (media.genre_names.isNotEmpty()) {
                Text(
                    text = "Genres: ${media.genre_names.joinToString(", ")}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Metadata
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "IMDB ID: ${media.imdb_id}")
                    Text(text = "TMDB ID: ${media.tmdb_id}")
                    Text(text = "User Rating: ${media.user_rating}")
                    Text(text = "Critic Score: ${media.critic_score}")
                    Text(text = "US Rating: ${media.us_rating}")
                    Text(text = "Runtime: ${media.runtime_minutes} min")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = media.plot_overview,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ErrorText(message: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message ?: "Failed to load details",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    }
}
