package com.remonilo.foodorder.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.remonilo.foodorder.data.foods
import com.remonilo.foodorder.ui.components.AppLogo
import com.remonilo.foodorder.ui.screen.CartScreen
import com.remonilo.foodorder.ui.screen.CheckoutScreen
import com.remonilo.foodorder.ui.screen.DetailScreen
import com.remonilo.foodorder.ui.screen.FavoriteScreen
import com.remonilo.foodorder.ui.screen.HomeScreen
import com.remonilo.foodorder.ui.screen.ThemeScreen
import com.remonilo.foodorder.viewmodel.FoodOrderViewModel
import kotlinx.coroutines.launch

private data class TopLevelRoute(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

private val topLevelRoutes =
    listOf(
        TopLevelRoute("home", "Beranda", Icons.Filled.Home),
        TopLevelRoute("cart", "Keranjang", Icons.Filled.ShoppingCart),
        TopLevelRoute("favorite", "Favorit", Icons.Filled.Favorite),
        TopLevelRoute("theme", "Tema", Icons.Filled.Settings),
    )

// Pindah antar menu utama tanpa menumpuk back stack
private fun NavHostController.navigateTopLevel(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id)
        launchSingleTop = true
    }
}

@Composable
fun FoodOrderApp(
    viewModel: FoodOrderViewModel,
    isDark: Boolean,
) {
    val navController = rememberNavController()
    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                AppLogo(modifier = Modifier.padding(24.dp))
                HorizontalDivider()
                topLevelRoutes.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = null) },
                        selected = currentRoute == item.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigateTopLevel(item.route)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    )
                }
            }
        },
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                if (topLevelRoutes.any { it.route == currentRoute }) {
                    NavigationBar {
                        topLevelRoutes.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute == item.route,
                                onClick = { navController.navigateTopLevel(item.route) },
                                label = { Text(item.label) },
                                icon = {
                                    BadgedBox(badge = {
                                        if (item.route == "cart" && viewModel.cartCount > 0) {
                                            Badge { Text(viewModel.cartCount.toString()) }
                                        }
                                    }) {
                                        Icon(item.icon, contentDescription = item.label)
                                    }
                                },
                            )
                        }
                    }
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding),
            ) {
                composable("home") {
                    HomeScreen(
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onFoodClick = { navController.navigate("detail/${it.id}") },
                    )
                }
                composable(
                    "detail/{foodId}",
                    arguments = listOf(navArgument("foodId") { type = NavType.IntType }),
                ) { entry ->
                    val food = foods.first { it.id == entry.arguments?.getInt("foodId") }
                    val isFavorite = food.id in viewModel.favorites
                    DetailScreen(
                        food = food,
                        isFavorite = isFavorite,
                        onBack = { navController.popBackStack() },
                        onToggleFavorite = {
                            viewModel.toggleFavorite(food)
                            val message = if (isFavorite) "Dihapus dari favorit" else "Ditambahkan ke favorit"
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(message)
                            }
                        },
                        onAddToCart = { viewModel.addToCart(food, it) },
                        onViewCart = { navController.navigateTopLevel("cart") },
                    )
                }
                composable("cart") {
                    CartScreen(
                        viewModel = viewModel,
                        onCheckout = { navController.navigate("checkout") },
                        onStartShopping = { navController.navigateTopLevel("home") },
                    )
                }
                composable("checkout") {
                    CheckoutScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onOrderPlaced = { navController.navigateTopLevel("home") },
                    )
                }
                composable("favorite") {
                    FavoriteScreen(
                        favoriteIds = viewModel.favorites,
                        onFoodClick = { navController.navigate("detail/${it.id}") },
                    )
                }
                composable("theme") {
                    ThemeScreen(isDark = isDark, onThemeChange = { viewModel.darkMode = it })
                }
            }
        }
    }
}
