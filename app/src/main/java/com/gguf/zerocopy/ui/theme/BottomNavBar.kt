package com.gguf.zerocopy.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
    object Chat : NavItem(Route.Chat.property, "Chat", Icons.Default.ChatBubble)
    object Models : NavItem(Route.Models.property, "Models", Icons.Default.FolderZip)
    object Workbench : NavItem(Route.Workbench.property, "Workbench", Icons.Default.Build)
    object Settings : NavItem(Route.Settings.property, "Settings", Icons.Default.Settings)
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(NavItem.Chat, NavItem.Models, NavItem.Workbench, NavItem.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
