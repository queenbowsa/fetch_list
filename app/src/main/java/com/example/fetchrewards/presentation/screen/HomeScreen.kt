package com.example.fetchrewards.presentation.screen

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.fetchrewards.data.remote.dto.ItemResponse
import com.example.fetchrewards.presentation.viewmodel.HomeScreenViewModel
import io.ktor.websocket.Frame

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = viewModel()) {
    val posts = viewModel.posts

    if (posts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        PostListWithTabsAndSearch(
            viewModel = viewModel
        )
    }
}

@Composable
fun PostListWithTabsAndSearch(viewModel: HomeScreenViewModel) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Frame.Text("Search by Item ID") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.search() }) {
                Text("Search")
            }
        }

        viewModel.searchResult?.let { foundPost ->
            Text(
                text = "Found Item:",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )
            PostCard(foundPost, showListId = true)
        }

        if (viewModel.searchAttempted && viewModel.searchResult == null) {
            Text(
                text = "No item found with ID: ${viewModel.searchQuery}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        TabRow(selectedTabIndex = viewModel.selectedTabIndex) {
            viewModel.listIds.forEachIndexed { index, listId ->
                Tab(
                    selected = viewModel.selectedTabIndex == index,
                    onClick = { viewModel.onTabSelected(index) },
                    text = { Text("List $listId") }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.postsInSelectedTab) { post ->
                PostCard(post)
            }
        }
    }
}

@Composable
fun PostCard(post: ItemResponse, showListId: Boolean = false) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.name.orEmpty(),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ID: ${post.id}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (showListId) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "List ID: ${post.listId}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

