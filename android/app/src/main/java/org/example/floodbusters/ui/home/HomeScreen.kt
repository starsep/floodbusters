package org.example.floodbusters.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.example.floodbusters.R
import org.example.floodbusters.api.user
import org.example.floodbusters.services.AlarmDetection
import org.example.floodbusters.ui.AvatarHeader

@Composable
fun HomeScreen() {
    AlarmDetection().startDetection()
    Column(modifier = Modifier.background(Color.White)) {
        AvatarHeader(user, modifier = Modifier.height(64.dp))
        Column {
            NotificationBox(
                dotColor = Color(0xFF9C303D),
                backgroundColor = Color(0xFFF9EEEE),
                headerText = "High flood risk",
                descriptionText = "The water level is raising rapidly. The house on the 34th Street Side of the lake is at high risk of flooding in the next few hours.",
                linkText = "> You need to know this, to stay safe"
            )
            NotificationBox(
                dotColor = Color(0xB0E3B400),
                backgroundColor = Color(0xFFF9F6EE),
                headerText = "Warning flood",
            )
        }
        Image(
            painter = painterResource(id = R.drawable.wheather),
            contentDescription = "weather graphs"
        )
        Text(text = "Risk of my home", style = MaterialTheme.typography.h5)
        Image(
            painter = painterResource(id = R.drawable.risk),
            contentDescription = "weather graphs",
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
        )
        Text(text = "Risk of my home")
    }
}

@Composable
fun NotificationHeader(text: String, dotColor: Color, backgroundColor: Color, enlarged: Boolean, onChangeState: () -> Unit) {
    ConstraintLayout(Modifier.background(color = backgroundColor).fillMaxWidth().height(32.dp).clickable { onChangeState() }) {
        val (dot, header, arrow) = createRefs()
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color = dotColor)
                .constrainAs(dot) {
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(text = text, modifier = Modifier.constrainAs(header) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(dot.end, 16.dp)
        }, style = MaterialTheme.typography.body1, fontWeight = FontWeight.Bold)
        Icon(
            painter = painterResource(id = if (enlarged) R.drawable.ic_arrow_down else R.drawable.ic_arrow_right),
            contentDescription = "arrow",
            modifier = Modifier.clickable { onChangeState() }.constrainAs(arrow) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
        )
    }
}

@Composable
fun NotificationBox(
    dotColor: Color,
    backgroundColor: Color,
    headerText: String,
    descriptionText: String? = null,
    linkText: String? = null,
) {
    val enlarged = remember { mutableStateOf(false) }
    Card {
        Column {
            NotificationHeader(
                text = headerText,
                dotColor = dotColor,
                backgroundColor = backgroundColor,
                enlarged = enlarged.value,
                onChangeState = { enlarged.value = enlarged.value.not() }
            )
            if (enlarged.value && descriptionText != null && linkText != null) {
                Text(text = descriptionText, modifier = Modifier.padding(horizontal = 8.dp))
                Text(text = linkText, textDecoration = TextDecoration.Underline, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}