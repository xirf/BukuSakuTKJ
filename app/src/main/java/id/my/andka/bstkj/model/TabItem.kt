package id.my.andka.bstkj.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: Int,
    val unSelectedItem: ImageVector? = null,
    val selectedIcon: ImageVector? = null,
)
