package com.remonilo.foodorder.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.remonilo.foodorder.data.SHIPPING_COST
import com.remonilo.foodorder.data.formatRupiah
import com.remonilo.foodorder.viewmodel.FoodOrderViewModel

private val paymentMethods = listOf("Transfer Bank", "E-Wallet (OVO, GoPay, DANA)", "QRIS")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: FoodOrderViewModel,
    onBack: () -> Unit,
    onOrderPlaced: () -> Unit,
) {
    var address by rememberSaveable { mutableStateOf("") }
    var payment by rememberSaveable { mutableStateOf(paymentMethods.first()) }
    var showSuccess by rememberSaveable { mutableStateOf(false) }
    val total = viewModel.subtotal + SHIPPING_COST

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item { SectionTitle("Alamat Pengiriman") }
                item {
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        placeholder = { Text("Contoh: Jl. Pendidikan No. 10, Sidoarjo") },
                        leadingIcon = { Icon(Icons.Filled.LocationOn, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                item { SectionTitle("Metode Pembayaran") }
                item {
                    Card {
                        Column(modifier = Modifier.selectableGroup()) {
                            paymentMethods.forEach { method ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .selectable(
                                                selected = method == payment,
                                                onClick = { payment = method },
                                                role = Role.RadioButton,
                                            ).padding(horizontal = 8.dp),
                                ) {
                                    RadioButton(selected = method == payment, onClick = null)
                                    Text(method, modifier = Modifier.padding(12.dp))
                                }
                            }
                        }
                    }
                }
                item { SectionTitle("Ringkasan Pesanan") }
                items(viewModel.cart, key = { it.food.id }) { item ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(item.food.name, modifier = Modifier.weight(1f))
                        Text("x${item.quantity}", modifier = Modifier.padding(horizontal = 16.dp))
                        Text(formatRupiah(item.food.price * item.quantity))
                    }
                }
                item {
                    HorizontalDivider()
                    PriceRow("Ongkir", SHIPPING_COST)
                }
            }
            Surface(tonalElevation = 3.dp) {
                Column(modifier = Modifier.padding(16.dp)) {
                    PriceRow("Total Pembayaran", total, highlight = true)
                    Button(
                        onClick = { showSuccess = true },
                        enabled = address.isNotBlank() && viewModel.cart.isNotEmpty(),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                    ) {
                        Text("Buat Pesanan")
                    }
                }
            }
        }
    }

    if (showSuccess) {
        AlertDialog(
            onDismissRequest = {},
            icon = {
                Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            },
            title = { Text("Pesanan Berhasil!") },
            text = {
                Text("Pesananmu sebesar ${formatRupiah(total)} dengan pembayaran $payment sedang diproses dan akan dikirim ke $address.")
            },
            confirmButton = {
                Button(onClick = {
                    showSuccess = false
                    viewModel.clearCart()
                    onOrderPlaced()
                }) { Text("Kembali ke Beranda") }
            },
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
}
