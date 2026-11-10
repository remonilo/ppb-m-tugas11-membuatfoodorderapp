package com.remonilo.foodorder.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.remonilo.foodorder.data.Food
import com.remonilo.foodorder.data.foods
import com.remonilo.foodorder.ui.components.FoodCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    favoriteIds: List<Int>,
    onFoodClick: (Food) -> Unit,
) {
    val favoriteFoods = foods.filter { it.id in favoriteIds }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Favorit") }) },
    ) { innerPadding ->
        if (favoriteFoods.isEmpty()) {
            EmptyState(
                "Belum ada makanan favorit.\nTekan ikon hati di halaman detail.",
                modifier = Modifier.padding(innerPadding),
            )
            return@Scaffold
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(favoriteFoods, key = { it.id }) { food ->
                FoodCard(food, onClick = { onFoodClick(food) })
            }
        }
    }
}
