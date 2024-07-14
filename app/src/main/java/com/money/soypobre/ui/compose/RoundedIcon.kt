package com.money.soypobre.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.money.soypobre.ui.theme.SoyPobreTheme

@Composable
fun RoundedIcon(
    icon: ImageVector,
    iconColor: Color = Color.White,
    backgroundColor: Color = LocalContentColor.current
) {
    Icon(
        icon,
        modifier = Modifier.drawBehind {
            drawCircle(backgroundColor)
        },
        tint = iconColor,
        contentDescription = null
    )
}

@Preview
@Composable
private fun RoundedIconPrev() {
    SoyPobreTheme {
        RoundedIcon(icon = Icons.Rounded.Add)
    }
}