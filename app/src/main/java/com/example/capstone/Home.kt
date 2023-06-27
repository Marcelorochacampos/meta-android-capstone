package com.example.capstone

import android.R.attr.maxLines
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    val databaseMenuItems = database.menuItemDao().getAll().observeAsState(listOf())
    val categories = databaseMenuItems.value.map{
        it.category
    }.distinct()
    val orderMenuItems = remember {
        mutableStateOf(false)
    }

    var menuItems = if (orderMenuItems.value) {
        databaseMenuItems.value.sortedBy { it.title }
    } else {
        databaseMenuItems.value
    }

    val searchPhrase = remember {
        mutableStateOf("")
    }

    val filterByCategory = remember {
        mutableStateOf(false)
    }
    val selectedCategory = remember {
        mutableStateOf("")
    }
    menuItems = if(filterByCategory.value) {
        menuItems.filter { it.category.equals(selectedCategory.value) }
    } else {
        menuItems
    }

    Column() {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .height(100.dp)
                    .width(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "App profile",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            navController.navigate(Profile.route) {
                                popUpTo(Profile.route)
                                launchSingleTop = true
                            }
                        }
                )
            }
        }
        Row(
            modifier = Modifier
                .background(Color(0xFF495E57))
                .fillMaxWidth()
                .height(360.dp)
                .padding(start = 20.dp)
        ) {
            Column() {
                Text(text="Little lemon", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF4CE14))
                Text(text="Chicago", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFFFFF))
                Row() {
                    Box(
                        modifier = Modifier
                            .width(220.dp)
                            .padding(top = 15.dp, bottom = 15.dp)
                    ) {
                        Text(
                            text="We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                            color = Color(0xFFFFFFFF)
                        )
                    }
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hero),
                            contentDescription = "Hero image",
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                ) {
                    TextField(
                        value = searchPhrase.value,
                        onValueChange = { searchPhrase.value = it },
                        label = { Text(text = "Enter search phrase")},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Row() {
            Column() {
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text="ORDER FOR DELIVERY!", fontWeight = FontWeight.Bold)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    categories.forEach {
                        Button(
                            onClick = {
                                filterByCategory.value = !filterByCategory.value
                                if (filterByCategory.value) {
                                    selectedCategory.value = it
                                } else {
                                    selectedCategory.value = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFCECECE)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text=it, color = Color(0xFF495E57))
                        }
                    }
                }
            }
        }

        if (!searchPhrase.value.isEmpty()) {
            menuItems = menuItems.filter { it.title.contains(searchPhrase.value, ignoreCase = true) }
        }
        MenuItems(menuItems)
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItems(itemList: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(
            items = itemList,
            itemContent = { menuItem ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Column() {
                        Text(text=menuItem.title, fontSize = 18.sp)
                        Text(text=menuItem.description, fontSize = 14.sp, modifier = Modifier
                            .fillMaxWidth(.75F)
                            .padding(top = 5.dp, bottom = 5.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color(0XFF495E57)
                        )
                        Text(
                            text = "$${menuItem.price}",
                            color = Color(0XFF495E57)
                        )
                    }
                    GlideImage(model = menuItem.image, contentDescription = "Item image", modifier = Modifier.clip(RoundedCornerShape(1.dp)))
                }
            }
        )
    }
}