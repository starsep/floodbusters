package org.example.floodbusters.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.example.floodbusters.R
import org.example.floodbusters.dataholder.User

@Composable
fun AvatarHeader(user: User, modifier: Modifier) {
    val image: Painter = painterResource(id = R.drawable.user_avatar)
    ConstraintLayout(modifier = modifier.height(64.dp)) {
        val (avatar, name) = createRefs()
        Image(painter = image, contentDescription = "user avatar",
            Modifier
                .padding(8.dp)
                .size(64.dp)
                .constrainAs(avatar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        Text(
            user.name,
            Modifier.constrainAs(name) {
                top.linkTo(avatar.top)
                bottom.linkTo(avatar.bottom)
                start.linkTo(avatar.end)
            },
            style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold,
        )
    }
}