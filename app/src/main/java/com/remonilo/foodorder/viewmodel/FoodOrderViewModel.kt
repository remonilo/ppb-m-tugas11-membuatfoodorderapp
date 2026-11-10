package com.remonilo.foodorder.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.remonilo.foodorder.data.CartItem
import com.remonilo.foodorder.data.Food

class FoodOrderViewModel : ViewModel() {
    val cart = mutableStateListOf<CartItem>()
    val favorites = mutableStateListOf<Int>()

    // null artinya mengikuti tema sistem
    var darkMode by mutableStateOf<Boolean?>(null)

    val cartCount: Int get() = cart.sumOf { it.quantity }
    val subtotal: Int get() = cart.sumOf { it.food.price * it.quantity }

    fun addToCart(
        food: Food,
        quantity: Int,
    ) {
        val index = cart.indexOfFirst { it.food.id == food.id }
        if (index == -1) {
            cart.add(CartItem(food, quantity))
        } else {
            cart[index] = cart[index].copy(quantity = cart[index].quantity + quantity)
        }
    }

    fun updateQuantity(
        food: Food,
        quantity: Int,
    ) {
        val index = cart.indexOfFirst { it.food.id == food.id }
        if (index != -1 && quantity > 0) {
            cart[index] = cart[index].copy(quantity = quantity)
        }
    }

    fun removeFromCart(food: Food) {
        cart.removeAll { it.food.id == food.id }
    }

    fun clearCart() {
        cart.clear()
    }

    fun toggleFavorite(food: Food) {
        if (!favorites.remove(food.id)) favorites.add(food.id)
    }
}
