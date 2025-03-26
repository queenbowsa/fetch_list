package com.example.fetchrewards.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.example.fetchrewards.data.remote.dto.ItemResponse
import com.example.fetchrewards.data.remote.network.KtorClientProvider
import com.example.fetchrewards.data.remote.network.Services
import com.example.fetchrewards.data.remote.network.ServicesImpl

class HomeScreenViewModel(
    private val service: Services = ServicesImpl(KtorClientProvider.client)
) : ViewModel() {

    var posts by mutableStateOf<List<ItemResponse>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")
    var searchResult by mutableStateOf<ItemResponse?>(null)
    var selectedTabIndex by mutableIntStateOf(0)
    var searchAttempted by mutableStateOf(false)

    val groupedPosts: Map<Int, List<ItemResponse>>
        get() = posts
            .filter { !it.name.isNullOrBlank() }
            .sortedWith(compareBy({ it.listId }, { it.name }))
            .groupBy { it.listId }

    val listIds: List<Int>
        get() = groupedPosts.keys.sorted()

    val postsInSelectedTab: List<ItemResponse>
        get() = groupedPosts[listIds.getOrNull(selectedTabIndex)] ?: emptyList()

    init {
        viewModelScope.launch {
            try {
                posts = service.getItems()
            } catch (e: Exception) {
                e.printStackTrace()
                posts = emptyList()
            }
        }
    }

    fun search() {
        val id = searchQuery.trim().toIntOrNull()
        searchResult = posts.find { it.id == id && !it.name.isNullOrBlank() }
        searchAttempted = true
    }

    fun onTabSelected(index: Int) {
        selectedTabIndex = index
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }
}
