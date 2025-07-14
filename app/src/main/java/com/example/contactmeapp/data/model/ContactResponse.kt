package com.example.contactmeapp.data.model

data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val message: String,
    val created_at: String
)

data class Pagination(
    val current_page: Int,
    val total_pages: Int,
    val total_items: Int,
    val items_per_page: Int
)

data class ContactResponse(
    val contacts: List<Contact>,
    val pagination: Pagination
)
