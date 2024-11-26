package id.my.andka.bstkj.ui.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import id.my.andka.bstkj.R
import id.my.andka.bstkj.ui.common.UiState
import id.my.andka.bstkj.ui.components.Chip
import id.my.andka.bstkj.ui.components.GreetingCard
import id.my.andka.bstkj.ui.components.ToolCard
import id.my.andka.bstkj.ui.theme.BsTKJTheme

data class Tool(
    val title: Int,
    val icon: Int,
    val route: String
)

object HomeScreenConstants {
    val toolCards = listOf(
        Tool(
            title = R.string.ip_calculator,
            icon = R.drawable.i_wireless_color,
            route = "ip_calculator"
        ),
        Tool(
            title = R.string.number_system,
            icon = R.drawable.i_numbers_color,
            route = "number_system"
        )
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewModelResult by viewModel.viewModelResult.collectAsState()

    LazyVerticalStaggeredGrid(
        modifier = modifier
            .fillMaxSize()
        ,
        columns = StaggeredGridCells.Fixed(1),
        horizontalArrangement = Arrangement.Start
    ) {
        // Greeting Section
        item {
            GreetingCard(modifier = Modifier.padding(16.dp).fillMaxWidth())
        }

        // Lesson Section
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                SectionTitle(text = stringResource(
                    id = R.string.lesson),
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                AnimatedContent(
                    targetState = viewModelResult.groupResult,
                    transitionSpec = { slideInVertically() togetherWith slideOutVertically() },
                    label = "",
                ) { groupState ->
                    when (groupState) {
                        is UiState.Loading -> LoadingGroupContent()
                        is UiState.Success -> SuccessGroupContent(groupState.data)
                        else -> LoadingGroupContent()
                    }
                }
            }
        }

        // Tools Section
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionTitle(text = stringResource(id = R.string.tools))
                ToolsGrid(navController)
            }
        }
    }
}

@Composable
private fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
private fun LoadingGroupContent() {
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(2),
        horizontalItemSpacing = 8.dp,
        modifier = Modifier.height(100.dp)
    ) {
        items(4) { GreetingCard() }
    }
}

@Composable
private fun SuccessGroupContent(data: List<String>?) {
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(2),
        horizontalItemSpacing = 8.dp,
        verticalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.height(136.dp).offset(y = (-10).dp)
    ) {
        if (data.isNullOrEmpty()) {
            item { Text("Tidak ada pelajaran, coba hidupkan internet untuk melihat konten") }
        } else {
            items(data.size) { Chip(data[it]) }
        }
    }
}

@Composable
private fun ToolsGrid(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HomeScreenConstants.toolCards.forEach { tool ->
            ToolCard(
                modifier = Modifier.weight(1f),
                title = stringResource(id = tool.title),
                icon = ImageVector.vectorResource(id = tool.icon),
                onClick = { navController.navigate(tool.route) }
            )
        }
    }
}

@Preview(device = "id:pixel_9_pro_xl", showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    BsTKJTheme {
        // Placeholder for preview
    }
}