package com.example.capstone

import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork (
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork (
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
) {
    fun toMenuItemRoom() = MenuItemRoom(
        id, title, description, price, image, category
    )
}