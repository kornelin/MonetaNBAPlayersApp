package com.example.monetanbaplayersapp.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.monetanbaplayersapp.Screen
import com.example.monetanbaplayersapp.ui.viewmodels.PlayerViewModel


@Composable
fun PlayerListScreen(viewModel: PlayerViewModel, navController: NavController) {
    val players = viewModel.players.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "No.",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Text(
                text = "Name & Surname",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .weight(3f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Text(
                text = "Position",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .weight(2f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Text(
                text = "Team",
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .weight(2f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(players.value) { index, player ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.PlayerDetail.route + "/${player.id}")
                        }
                        .padding(vertical = 8.dp),
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "${index + 1}.",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${player.first_name} ${player.last_name}",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(3f)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (player.position.isNullOrEmpty()) "N/A" else player.position,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(2f)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = player.team?.name ?: "N/A",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(2f)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (index == players.value.size - 1) viewModel.fetchAllPlayers()
            }
        }
    }
}


