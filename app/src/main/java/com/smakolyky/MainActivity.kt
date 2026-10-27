package com.smakolyky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smakolyky.navigation.BOTTOM_NAV_ROUTES
import com.smakolyky.navigation.NavGraph
import com.smakolyky.ui.components.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute in BOTTOM_NAV_ROUTES) BottomNavBar(navController = navController)
                    },
                ) { padding ->
                    androidx.compose.foundation.layout.Box(modifier = androidx.compose.ui.Modifier.padding(padding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
