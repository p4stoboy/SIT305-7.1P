package sit305.a71p

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LostAndFoundScreen(navController: NavController, viewModel: LostAndFoundViewModel) {
    val items by viewModel.items.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create_advert") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Advert")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = items, key = { item -> item.id }) { item ->
                LostAndFoundItem(navController, viewModel, item = item)
            }
        }
    }
}

@Composable
fun LostAndFoundItem(nc: NavController, viewModel: LostAndFoundViewModel, item: LostAndFoundItem) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = item.postType.toString(), modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)
        Text(text = item.name + " (" + item.location + ")", modifier = Modifier.fillMaxWidth())
        Text(text = "Date: " + item.date, modifier = Modifier.fillMaxWidth(), fontSize = 12.sp)
        Button(onClick = {
            nc.navigate("item_details/${item.id}")
        },
            modifier = Modifier.fillMaxWidth()) {
            Text("View Details")
        }
    }
}

@Composable
fun CreateAdvertScreen(nc: NavController, viewModel: LostAndFoundViewModel) {
    val postType = remember { mutableStateOf(PostType.LOST) }
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            RadioButton(
                selected = postType.value == PostType.LOST,
                onClick = { postType.value = PostType.LOST }
            )
            Text("Lost")
            RadioButton(
                selected = postType.value == PostType.FOUND,
                onClick = { postType.value = PostType.FOUND }
            )
            Text("Found")
        }
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = phone.value,
            onValueChange = { phone.value = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = date.value,
            onValueChange = { date.value = it },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = location.value,
            onValueChange = { location.value = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            val item = LostAndFoundItem(
                postType = postType.value,
                name = name.value,
                phone = phone.value,
                description = description.value,
                date = date.value,
                location = location.value
            )
            viewModel.insertItem(item)
            nc.navigateUp()
        }) {
            Text("Save")
        }
        Button(onClick = {
            nc.navigateUp()
        }) {
            Text("Cancel")
        }
    }
}

@Composable
fun ItemDetailsScreen(nc: NavController, viewModel: LostAndFoundViewModel, itemId: Int) {
    val item = viewModel.getItem(itemId)

    if (item != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Post Type: ${item.postType}", modifier = Modifier.fillMaxWidth())
            Text(text = "Name: ${item.name}", modifier = Modifier.fillMaxWidth())
            Text(text = "Phone: ${item.phone}", modifier = Modifier.fillMaxWidth())
            Text(text = "Description: ${item.description}", modifier = Modifier.fillMaxWidth())
            Text(text = "Date: ${item.date}", modifier = Modifier.fillMaxWidth())
            Text(text = "Location: ${item.location}", modifier = Modifier.fillMaxWidth())
            Button(onClick = {
                nc.navigateUp()
            }) {
                Text("Back")
            }
            Button(onClick = {
                viewModel.removeItem(item)
                nc.navigateUp()
            }) {
                Text("Remove")
            }
        }
    } else {
        Text("Item not found")
    }
}