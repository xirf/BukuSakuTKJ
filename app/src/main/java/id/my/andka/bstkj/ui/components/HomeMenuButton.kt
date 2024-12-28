package id.my.andka.bstkj.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.CloudSync
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import id.my.andka.bstkj.R
import id.my.andka.bstkj.ui.screen.home.HomeViewModel

@Composable
fun HomeMenuButton(
    isExpanded: Boolean,
    navController: NavController,
    viewModel: HomeViewModel
) {
    var expanded by remember { mutableStateOf(isExpanded) }
    val context = LocalContext.current

    Box {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
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
                    viewModel.clearArticles()
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
                    (context as? Activity)?.finishAffinity()
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