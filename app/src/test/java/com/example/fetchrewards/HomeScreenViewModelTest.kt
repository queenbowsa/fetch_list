package com.example.fetchrewards

import com.example.fetchrewards.data.remote.dto.ItemResponse
import com.example.fetchrewards.data.remote.network.Services
import com.example.fetchrewards.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FakeService : Services {
    override suspend fun getItems(): List<ItemResponse> {
        return listOf(
            ItemResponse(id = 1, listId = 2, name = "Item B"),
            ItemResponse(id = 2, listId = 1, name = "Item A"),
            ItemResponse(id = 3, listId = 1, name = null),
            ItemResponse(id = 4, listId = 2, name = "")
        )
    }
}

class HomeScreenViewModelTest {

    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setUp(): Unit = runTest {
        viewModel = HomeScreenViewModel(service = FakeService())
    }

    @Test
    fun `fetching items filters and sorts correctly`(): Unit = runTest {
        val groupedPosts = viewModel.groupedPosts

        assertTrue(groupedPosts.containsKey(1))
        assertTrue(groupedPosts.containsKey(2))

        val list1 = groupedPosts[1]
        val list2 = groupedPosts[2]
        groupedPosts.values.flatten().forEach { item ->
            assertTrue(!item.name.isNullOrBlank())
        }

        assertEquals("Item A", list1?.get(0)?.name)
        assertEquals("Item B", list2?.get(0)?.name)

        assertEquals(2, groupedPosts.size)
    }

    @Test
    fun `search finds correct item`(): Unit = runTest {
        viewModel.searchQuery = "1"
        viewModel.search()

        val result = viewModel.searchResult
        assertNotNull(result)
        assertEquals(1, result?.id)
        assertEquals("Item B", result?.name)
    }

    @Test
    fun `search returns null for invalid ID`(): Unit = runTest {
        viewModel.searchQuery = "999"
        viewModel.search()

        assertNull(viewModel.searchResult)
    }

    @Test
    fun `tab selection changes selected index`() {
        assertEquals(0, viewModel.selectedTabIndex)

        viewModel.onTabSelected(1)
        assertEquals(1, viewModel.selectedTabIndex)
    }

    @Test
    fun `search is case insensitive and ignores whitespace`(): Unit = runTest {
        viewModel.searchQuery = " 1 "
        viewModel.search()

        assertNotNull(viewModel.searchResult)
        assertEquals(1, viewModel.searchResult?.id)
    }
}
