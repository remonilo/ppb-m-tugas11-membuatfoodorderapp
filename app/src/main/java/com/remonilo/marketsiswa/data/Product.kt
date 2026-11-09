package com.remonilo.marketsiswa.data

data class Product(
    val category: String,
    val name: String,
    val description: String,
    val price: String,
)

val dummyProducts =
    listOf(
        Product("Makanan", "Brownies Lumer", "Cokelat melimpah, cocok untuk camilan sore.", "Rp 15.000"),
        Product("Fashion", "Kaos Sablon Kelas", "Kaos katun combed dengan desain buatan siswa.", "Rp 85.000"),
        Product("Kerajinan", "Gantungan Kunci Rajut", "Dirajut tangan, tersedia berbagai warna.", "Rp 10.000"),
        Product("Jasa", "Desain Poster", "Poster acara sekolah, selesai dalam 2 hari.", "Rp 25.000"),
    )
