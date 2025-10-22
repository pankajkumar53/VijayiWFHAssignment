package com.pandroid.vijayiwfhassignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.pandroid.vijayiwfhassignment.ui.home.HomeScreen
import com.pandroid.vijayiwfhassignment.ui.navigation.LocalNavigationProvider
import com.pandroid.vijayiwfhassignment.ui.navigation.NavGraph
import com.pandroid.vijayiwfhassignment.ui.navigation.Routes
import com.pandroid.vijayiwfhassignment.ui.theme.VijayiWFHAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VijayiWFHAssignmentTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(value = LocalNavigationProvider provides navController) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavGraph(
                            modifier = Modifier.padding(innerPadding),
                            startDestination = Routes.Home
                        )
                    }
                }
            }
        }
    }
}