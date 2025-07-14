package com.example.contactmeapp.screen.contact_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmeapp.data.model.Contact
import com.example.contactmeapp.data.network.RetrofitClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ContactUiState(
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val page: Int = 1,
    val canLoadMore: Boolean = true
)

class ContactViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ContactUiState())
    val uiState: StateFlow<ContactUiState> = _uiState.asStateFlow()

    init {
        loadContacts()
    }

    fun loadContacts() {
        val currentState = _uiState.value
        if (currentState.isLoading || !currentState.canLoadMore) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getContacts(page = currentState.page)
                val allContacts = currentState.contacts + response.contacts
                val morePages = currentState.page < response.pagination.total_pages

                _uiState.update {
                    it.copy(
                        contacts = allContacts,
                        isLoading = false,
                        page = it.page + 1,
                        canLoadMore = morePages
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Error desconocido") }
            }
        }
    }
}
