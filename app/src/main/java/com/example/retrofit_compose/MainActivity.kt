package com.example.retrofit_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.retrofit_compose.model.ProductModel
import com.example.retrofit_compose.ui.theme.RetrofitComposeTheme
import com.example.retrofit_compose.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    getAllProduct(viewModel)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = CardDefaults.cardElevation(2.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = (product.thumbnail),
                contentDescription = "Product Thumbnail",
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = product.title, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Price: $${product.price}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Rating: ${product.rating}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

private val list = listOf<ProductModel>(
    ProductModel(1,"yuyfgufgu","fgfdgdgs",56565.0,45.5,3.6,4,"fdggs","dfgdfgf","fdgs",
        listOf("a","b","c")
    ),
    ProductModel(2,"dfsdfgh","fgdgs",56565.0,45.5,3.6,4,"fdggs","dfgdfgf","fdgs",
        listOf("a","b","c")
    )
)

@Composable
fun ProductList(products: List<ProductModel>) {
    LazyColumn {
        items(products) { it->
            ProductItem(it)
        }
    }
}

@Composable
fun getAllProduct(mainViewModel: MainViewModel){
    when(val result = mainViewModel.response.value){
        is DataState.Success->{
            if(result.data.allProduct != null){
                ProductList(products = result.data.allProduct)
            }else{
                ProductList(list)
            }

        }
        is DataState.Error->{
            Text(text = "${result.exception}")
        }
        DataState.Loading->{
            CircularProgressIndicator()
        }
        DataState.Empty->{

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitComposeTheme {
        ProductList(products = list)
    }
}