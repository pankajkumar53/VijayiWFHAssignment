package com.pandroid.vijayiwfhassignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pandroid.vijayiwfhassignment.ui.details.DetailsScreen
import com.pandroid.vijayiwfhassignment.ui.home.HomeScreen
import kotlinx.serialization.Serializable

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController> {
    error("No NavHost provided")
}

@Composable
fun NavGraph(
    modifier: Modifier,
    startDestination: Routes.Home
) {
    val navHostController = LocalNavigationProvider.current

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<Routes.Home> {
            HomeScreen()
        }

        composable<Routes.Details> { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            DetailsScreen(id)
        }
    }

}

@Serializable
sealed class Routes {
    @Serializable
    data object Home

    @Serializable
    data class Details(val id: Int)
}