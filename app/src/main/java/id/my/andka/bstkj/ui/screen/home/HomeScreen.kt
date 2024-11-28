package id.my.andka.bstkj.ui.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.CloudSync
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import id.my.andka.bstkj.R
import id.my.andka.bstkj.ui.common.UiState
import id.my.andka.bstkj.ui.components.BottomSheetArticles
import id.my.andka.bstkj.ui.components.Chip
import id.my.andka.bstkj.ui.components.GreetingCard
import id.my.andka.bstkj.ui.components.ToolsGrid
import kotlinx.coroutines.launch

data class Tool(
    val title: Int, val icon: Int, val route: String
)

object HomeScreenConstants {
    val toolCards = listOf(
        Tool(
            title = R.string.ip_calculator,
            icon = R.drawable.i_wireless_color,
            route = "ip_calculator"
        ), Tool(
            title = R.string.number_system,
            icon = R.drawable.i_numbers_color,
            route = "number_system"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onArticleClick: (String, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewModelResult by viewModel.viewModelResult.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(1),
        horizontalArrangement = Arrangement.Start
    ) {
        // Greeting Section
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GreetingCard(
                    modifier = Modifier.weight(1f)
                )

                Box {

                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .size(48.dp)
                            .padding(8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Menu"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sync)) },
                            onClick = {
                                expanded = false
                                viewModel.fetchArticles()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.CloudSync,
                                    contentDescription = stringResource(R.string.sync)
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.privacy_policy)) },
                            onClick = {
                                expanded = false
                                navController.navigate("privacy_policy")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Shield,
                                    contentDescription = stringResource(R.string.privacy_policy)
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.tos)) },
                            onClick = {
                                expanded = false
                                navController.navigate("tos")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Handshake,
                                    contentDescription = stringResource(R.string.tos)
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    stringResource(R.string.exit),
                                    color = MaterialTheme.colorScheme.error
                                )
                            },
                            onClick = {
                                expanded = false
                                navController.popBackStack()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                                    contentDescription = stringResource(R.string.exit)
                                )
                            }
                        )
                    }
                }
            }
        }

        // Lesson Section
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                SectionTitle(
                    text = stringResource(
                        id = R.string.lesson
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                AnimatedContent(
                    targetState = viewModelResult.groupResult,
                    transitionSpec = { slideInVertically() togetherWith slideOutVertically() },
                    label = "",
                ) { groupState ->
                    when (groupState) {
                        is UiState.Loading -> LoadingGroupContent()
                        is UiState.Success -> {
                            if (groupState.data.isNullOrEmpty()) {
                                Text(
                                    text = "Tidak ada materi pelajaran, coba hidupkan internet untuk melihat konten",
                                    modifier = Modifier.padding(16.dp),
                                )
                            } else {
                                SuccessGroupContent(groupState.data, onClick = {
                                    viewModel.getArticlesByGroup(it)
                                    showBottomSheet = true
                                    coroutineScope.launch { modalSheetState.show() }
                                })
                            }
                        }

                        else -> LoadingGroupContent()
                    }
                }
            }
        }

        // Tools Section
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionTitle(text = stringResource(id = R.string.tools))
                ToolsGrid(navController)
            }
        }

        if (showBottomSheet) {
            item {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = modalSheetState,
                ) {
                    viewModelResult.articleResult.data?.let {
                        BottomSheetArticles(
                            modifier = Modifier.fillMaxWidth(),
                            data = it,
                            onArticleClick = { slug, title ->
                                showBottomSheet = false
                                onArticleClick(slug, title)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun SectionTitle(
    text: String, modifier: Modifier = Modifier
) {
    Text(
        text = text, modifier = modifier, style = MaterialTheme.typography.titleLarge.copy(
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
private fun SuccessGroupContent(data: List<String>, onClick: (String) -> Unit) {
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(2),
        horizontalItemSpacing = 8.dp,
        verticalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .height(136.dp)
            .offset(y = (-10).dp)
    ) {
        items(data.size) {
            Chip(data[it], onClick = {
                onClick(data[it])
            })
        }
    }
}
