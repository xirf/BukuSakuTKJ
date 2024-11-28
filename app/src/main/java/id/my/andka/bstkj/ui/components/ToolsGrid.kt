package id.my.andka.bstkj.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.my.andka.bstkj.ui.screen.home.HomeScreenConstants

@Composable
fun ToolsGrid(
    navController: NavController, modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HomeScreenConstants.toolCards.forEach { tool ->
            ToolCard(modifier = Modifier.weight(1f),
                title = stringResource(id = tool.title),
                icon = ImageVector.vectorResource(id = tool.icon),
                onClick = { navController.navigate(tool.route) })
        }
    }
}