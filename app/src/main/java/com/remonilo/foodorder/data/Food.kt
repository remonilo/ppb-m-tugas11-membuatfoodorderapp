package com.remonilo.foodorder.data

import androidx.annotation.DrawableRes
import com.remonilo.foodorder.R
import java.util.Locale

data class Food(
    val id: Int,
    val name: String,
    val category: String,
    val price: Int,
    val description: String,
    @DrawableRes val image: Int,
)

data class CartItem(
    val food: Food,
    val quantity: Int,
)

const val SHIPPING_COST = 5000

val categories = listOf("Semua", "Makanan", "Minuman", "Camilan")

val foods =
    listOf(
        Food(
            1,
            "Nasi Goreng Spesial",
            "Makanan",
            18000,
            "Nasi goreng dengan bumbu spesial, telur, dan sayuran segar.",
            R.drawable.nasi_goreng,
        ),
        Food(
            2,
            "Ayam Geprek",
            "Makanan",
            20000,
            "Ayam goreng tepung yang digeprek dengan sambal bawang pedas.",
            R.drawable.ayam_geprek,
        ),
        Food(
            3,
            "Mie Ayam Jamur",
            "Makanan",
            15000,
            "Mie kenyal dengan topping ayam kecap, jamur, dan sawi.",
            R.drawable.mie_ayam,
        ),
        Food(
            4,
            "Es Teh Manis",
            "Minuman",
            5000,
            "Teh manis dingin yang menyegarkan, cocok untuk teman makan.",
            R.drawable.es_teh,
        ),
        Food(
            5,
            "Es Campur",
            "Minuman",
            12000,
            "Campuran buah, cincau, dan kolang kaling dengan sirup dan susu.",
            R.drawable.es_campur,
        ),
        Food(
            6,
            "Pisang Goreng",
            "Camilan",
            10000,
            "Pisang goreng renyah di luar dan lembut di dalam.",
            R.drawable.pisang_goreng,
        ),
    )

fun formatRupiah(value: Int): String = "Rp" + String.format(Locale.US, "%,d", value).replace(',', '.')
