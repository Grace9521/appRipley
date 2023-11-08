package com.example.concierto2.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.graceproyectoripley.R


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(){
    Column(modifier = Modifier.verticalScroll(
        rememberScrollState()
    ).padding(bottom = 40.dp)) {
        ContenidoHomeView()
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoHomeView(){

    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precioTotal by remember { mutableStateOf(0.0) }
    var porcentajeDescuento by remember { mutableStateOf("0%") }
    var precioDescuento by remember { mutableStateOf(0.0) }
    var Descuentoprecio by remember { mutableStateOf(0.0) }
    var precioIgv by remember { mutableStateOf(0.0) }


    val categorias = listOf(
        "Zapatos" ,
        "Prendas" ,
        "Electrodomesticos" ,
        "Celulares" ,
        "Ropa" ,
        "Juguetes" ,
        "Laptops"
    )
    Column (modifier = Modifier.fillMaxWidth().padding(top= 40.dp) , horizontalAlignment = Alignment.CenterHorizontally) {

        Box(modifier = Modifier.padding(bottom = 20.dp)){
            Image(
                painter = painterResource(id = R.drawable.logoripley),
                contentDescription = "Logo" )


        }
        Row (modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp , horizontal = 20.dp) , horizontalArrangement = Arrangement.SpaceEvenly){
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Precio Total")
                Text(text = precioTotal.toString())
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column (modifier = Modifier.weight(1f)){
                Text(text = "Porcentaje de Descuento")
                Text(text = porcentajeDescuento )
            }


        }
        Row (modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp , horizontal = 20.dp) , horizontalArrangement = Arrangement.SpaceEvenly){
            Column(modifier = Modifier.weight(1f)) {
                Text(text="Precio con Descuento")
                Text(text=precioDescuento.toString())
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column (modifier = Modifier.weight(1f)){
                Text(text="Descuento")
                Text(text=Descuentoprecio.toString())
            }


        }

        Row (modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp , horizontal = 20.dp) , horizontalArrangement = Arrangement.SpaceEvenly){
            Column (modifier = Modifier.weight(1f)){
                Text(text="Precio con Igv")
                Text(text=precioIgv.toString())
            }




        }
        Box(){
            Button(onClick = {  expanded = !expanded }){
                if(categoriaSeleccionada != ""){
                    Text(text = "Categoría seleccionada: $categoriaSeleccionada")

                }else{
                    Text(text = "Seleccione una Categoria")
                }
            }




        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false},
            modifier = Modifier.fillMaxWidth()
        ){
            categorias.forEach{
                    categoria -> DropdownMenuItem(text = { Text(text= categoria) } , onClick = {
                categoriaSeleccionada = categoria
                expanded = false

                Toast.makeText(context, categoria.toString(), Toast.LENGTH_SHORT).show()
            })
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = precio , onValueChange = { precio = it } ,   label = {Text("Ingresa El Precio") },  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = cantidad , onValueChange = { cantidad = it } ,   label = {Text("Ingresa La Cantidad") } ,  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Spacer(modifier = Modifier.height(20.dp))
        Button(   onClick = {
            if(precio != "" && cantidad != "" && categoriaSeleccionada != "" ){
                precioTotal = calcularTotal(precio.toDouble() , cantidad.toDouble())
                if(categoriaSeleccionada == "Zapatos" && precioTotal > 1000){

                    porcentajeDescuento = "10%"
                    precioDescuento =  precioTotal - (precioTotal * 0.10)
                    Descuentoprecio = precioTotal * 0.10
                    precioIgv = precioDescuento + ( precioDescuento * 0.18 )
                }  else if(categoriaSeleccionada == "Prendas" && precioTotal > 500){
                    porcentajeDescuento = "18%"
                    precioDescuento =  precioTotal - (precioTotal * 0.18)
                    Descuentoprecio = precioTotal * 0.18
                    precioIgv = precioDescuento + ( precioDescuento * 0.18 )

                } else if(categoriaSeleccionada == "Electrodomestico" && precioTotal > 6000){
                    porcentajeDescuento = "7%"
                    precioDescuento =  precioTotal - (precioTotal * 0.07)
                    Descuentoprecio = precioTotal * 0.07
                    precioIgv = precioDescuento + ( precioDescuento * 0.18 )

                }
                else if(categoriaSeleccionada == "Celulares" && precioTotal > 3500){
                    porcentajeDescuento = "9%"
                    precioDescuento =  precioTotal - (precioTotal * 0.09)
                    Descuentoprecio = precioTotal * 0.09
                    precioIgv = precioDescuento + ( precioDescuento * 0.18 )

                }
                else if(categoriaSeleccionada == "Ropa" && precioTotal > 1500){
                    porcentajeDescuento = "5%"
                    precioDescuento =  precioTotal - (precioTotal * 0.05)
                    Descuentoprecio = precioTotal * 0.05
                    precioIgv = precioDescuento + ( precioDescuento * 0.18 )

                }else if(categoriaSeleccionada == "Juguetes" && precioTotal > 2500){
                    porcentajeDescuento = "13%"
                    precioDescuento =  precioTotal - (precioTotal * 0.13)
                    Descuentoprecio = precioTotal * 0.13
                    precioIgv = precioDescuento + ( precioDescuento * 0.18 )

                }else if(categoriaSeleccionada == "Laptops" && precioTotal > 8000){
                    porcentajeDescuento = "19%"
                    precioDescuento =  precioTotal - (precioTotal * 0.19)
                    Descuentoprecio = precioTotal * 0.19
                    precioIgv = precioDescuento + ( precioDescuento * 0.18 )

                }

                else{
                    porcentajeDescuento = "0%"
                    precioIgv = precioTotal + ( precioTotal * 0.18 )
                }


            }else{
                error = !error
            }
        } ){
            Text("Calcular")
        }

        if(error){
            Text("LLene Todos los campos")
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                error = false
            }

        }



    }



}

fun calcularTotal(precio: Double , cantidad:Double): Double{
    var resul = precio * cantidad
    return  kotlin.math.round(resul)
}