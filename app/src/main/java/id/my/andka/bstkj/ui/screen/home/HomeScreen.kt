package id.my.andka.bstkj.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import id.my.andka.bstkj.R
import id.my.andka.bstkj.ui.common.UiState
import id.my.andka.bstkj.ui.components.GreetingCard
import id.my.andka.bstkj.ui.components.TextCard
import id.my.andka.bstkj.ui.components.ToolCard
import id.my.andka.bstkj.ui.theme.BsTKJTheme

data class Tool(
    val title: Int,
    val icon: Int,
    val route: String
)

private val toolCards = listOf(
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

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val groups by viewModel.groups.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
    ) {
        GreetingCard()

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.lesson),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(0.dp),
            horizontalItemSpacing = 8.dp,
            modifier = Modifier.fillMaxWidth(),
            content = {
                when(groups){
                    is UiState.Loading -> {
                        items(4) {
                            GreetingCard()
                        }
                    }
                    is UiState.Success -> {
                        items(groups.data?.size ?: 0) { index ->
                            TextCard(
                                title = groups.data?.get(index) ?: "Tidak ada materi",
//                                onClick = {
//                                    navController.navigate("lesson/${groups.data[index]}")
//                                }
                            )
                        }
                    }
                    else -> {
                        items(4) {
                            GreetingCard()
                        }
                    }
                }
            }
        )


        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.tools),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(0.dp),
            verticalItemSpacing = 8.dp,
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(toolCards) { tool ->
                    ToolCard(
                        title = stringResource(id = tool.title),
                        icon = ImageVector.vectorResource(id = tool.icon),
                        onClick = {
                            navController.navigate(tool.route)
                        }
                    )
                }
            }
        )
    }
}

@Composable
@Preview(device = "id:pixel_9_pro_xl", showSystemUi = true, backgroundColor = 0xF0F1F2)
fun HomeScreenPreview() {
    BsTKJTheme {
//        HomeScreen(navController = NavController())
    }
}