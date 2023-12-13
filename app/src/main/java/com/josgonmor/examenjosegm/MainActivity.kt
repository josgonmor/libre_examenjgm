@file:OptIn(ExperimentalMaterial3Api::class)

package com.josgonmor.examenjosegm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.R
import androidx.core.os.persistableBundleOf
import com.josgonmor.examenjosegm.data.DataSource
import com.josgonmor.examenjosegm.data.DataSource.productos
import com.josgonmor.examenjosegm.data.Producto
import com.josgonmor.examenjosegm.ui.theme.ExamenjosegmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenjosegmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    listApp()
                }
            }
        }
    }
}

@Composable
fun listApp(){
    var products: ArrayList<Producto> = DataSource.productos
    listAppShow(products, modifier =  Modifier)
}
@Composable
fun  listAppShow(products1: ArrayList<Producto>, modifier: Modifier){
    var products: ArrayList<Producto> by rememberSaveable {
        mutableStateOf(products1)
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var price by rememberSaveable {
        mutableStateOf("0")
    }
    var downtext by rememberSaveable {
        mutableStateOf("Todavía no se ha añadido ningún valor")
    }



    Column {
        Column(modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .weight(1f)) {
            Text(text = "Hola soy alumno josegm", modifier = modifier.padding(start = 20.dp, top = 50.dp))
        }
        Row(modifier = modifier
            .fillMaxWidth()
            .weight(4f)) {
            Column (modifier = modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally){
                intoText(value = name, onValueChange = {name=it}, label = "Nombre", modifier =modifier)
                intoText(value = price, onValueChange = {price=it}, label = "Precio", modifier =modifier)
                Button(onClick = {
                    var find = false;
                    for (product in products){
                        if(product.equals(Producto(name, price.toInt()))){
                            downtext = "No se ha modificado nada del producto "+product.nombre+" el precio es el mismo"
                            find = true;
                        }else{
                            if(product.nombre.equals(name)){
                                var prod = Producto(product.nombre, price.toInt())
                                products.remove(product)
                                products.add(prod)
                                downtext="Del producto "+product.nombre+" se ha modificado el precio, de "+product.precio+" a "+prod.precio
                                find = true;
                            }
                        }
                    }
                    if(!find){
                        var productos = products
                        productos.add(Producto(name, price.toInt()))
                        products = productos;
                        downtext="Se ha añadido el producto "+name+" con precio"+price;
                    }
                }) {
                    Text(text = "Add/Update producto")
                }
            }
            LazyColumn(modifier = modifier.weight(1f)){
                items(products){
                    producto->ProductCard(producto)
                }
            }
        }
        Column(modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = downtext)
        }
    }
}
@Composable
fun intoText(
    value: String,
    onValueChange: (String)->Unit,
    label: String,
    modifier: Modifier
){
    TextField(
        value=value,
        onValueChange = onValueChange,
        label = {Text(label)},
        modifier = modifier.padding(8.dp),
    )
}



@Composable
fun ProductCard(producto: Producto){
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row (modifier = Modifier.background(Color.Yellow).padding(20.dp).fillMaxWidth()){
            Text(text = "Nombre: "+producto.nombre)
        }
        Row (modifier = Modifier.background(Color.Cyan).padding(20.dp).fillMaxWidth()){
            Text(text = "Precio: "+producto.precio.toString(), textAlign = TextAlign.Center)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExamenjosegmTheme {
        listApp();
    }
}