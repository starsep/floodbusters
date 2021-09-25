package org.example.floodbusters.ui.guidance

import android.app.Activity
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.*
import org.example.floodbusters.api.createApiService
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.isActive
import org.example.floodbusters.R
import org.example.floodbusters.api.apiService
import org.example.floodbusters.dataholder.User
import org.example.floodbusters.dataholder.user
import org.example.floodbusters.services.LocationService
import org.example.floodbusters.ui.AvatarHeader

data class Message(val sos: Boolean, val text: String, val time: String, val incoming: Boolean)

val rubyColor = Color(0xFF9C303D)
val exampleMessages = listOf(
    Message(sos = false, text = "Hi Anna, according to the information we received, you are now in the building on the 34th Street Side of the Lake", time = "16:45", incoming = true),
    Message(sos = false, text = "I will then guide you to a safe place. It is important that you stay calm, ok?", time = "16:47", incoming = true),
    Message(sos = false, text = "yes I understand, now I go down the stairs and walk to the street.", time = "16:52", incoming = false),
)

val locations = listOf(
    48.022121 to 8.563368,
    47.372240 to 8.492740,
    46.927514 to 7.440003,
)

@Composable
fun GuidanceScreen() {
    val apiService = remember { createApiService() }
fun GuidanceScreen(activity: Activity) {
fun GuidanceScreen(activity: FragmentActivity?) {
    val angle1 = remember { Animatable(initialValue = 160f) }
    val angle2 = remember { Animatable(initialValue = 220f) }
    val angle3 = remember { Animatable(initialValue = 300f) }
    val volume = remember { mutableStateOf(0.4f) }
    val buttonColor = remember { mutableStateOf(Color.LightGray) }
    val messages = remember { mutableStateOf(emptyList<Message>()) }

    fun addMessage(message: Message) {
        messages.value = messages.value.plus(message)
    }
    ConstraintLayout(Modifier.background(color = Color.White).fillMaxHeight()) {
    val messages = listOf(
        Message(sos = true, text = "Hi Anna, according to the information we received, you are now in the building on the 34th Street Side of the Lake", time = "16:45", incoming = true),
        Message(sos = false, text = "I will then guide you to a safe place. It is important that you stay calm, ok?", time = "16:47", incoming = true),
        Message(sos = false, text = "yes I understand, now I go down the stairs and walk to the street.", time = "16:52", incoming = false),
    )
    val scrollState = rememberScrollState()
    val locationService = LocationService(activity)
    ConstraintLayout(Modifier.background(color = Color.White)) {
        val (avatarHeader, guide, rings, startButton, volumeSlider, volumeLabel, chat) = createRefs()
        AvatarHeader(user = user, modifier = Modifier.constrainAs(avatarHeader) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        })
        Text("Guide me to a safe place",
            Modifier
                .constrainAs(guide) {
                    top.linkTo(avatarHeader.bottom)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 8.dp),
            style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold,
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .constrainAs(startButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(rings.top)
                bottom.linkTo(rings.bottom)
            }) {
            Box(modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(buttonColor.value)
                .wrapContentSize(align = Alignment.Center)
                .clickable {
                    GlobalScope.launch {
                        val (latitude, longitude) = locations.first()
                        val risk = apiService.getRisk(latitude = latitude, longitude = longitude)
                        buttonColor.value = if (risk.isSpotSafe) Color.Green else rubyColor
                        if (!risk.isSpotSafe) {
                            addMessage(Message(
                                sos = true,
                                text = risk.advise,
                                time = "16:42",
                                incoming = true
                            ))
                            val safeSpot = apiService.getSaveSpot(latitude = latitude, longitude = longitude)
                            delay(200L)
                            addMessage(
                                Message(
                                    sos = false,
                                    text = "Safe spot is (${safeSpot.latitude}, ${safeSpot.longitude})",
                                    time = "16:43",
                                    incoming = true,
                                ))
                            for (message in exampleMessages) {
                                delay(500L)
                                addMessage(message)
                            }
                        }
                    }
                }) {
                Text("Start", modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
            }
        }
        Canvas(modifier = Modifier
            .constrainAs(rings) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(guide.bottom)
            }
            .size(240.dp)
            .padding(8.dp)) {
            fun roundStroke(width: Float) = Stroke(width = width, cap = StrokeCap.Round)
            drawArc(color = rubyColor, angle1.value, 320f, useCenter = false, style = roundStroke(20f))
            drawArc(color = rubyColor, angle2.value, 320f, useCenter = false, style = roundStroke(10f), topLeft = Offset(80f, 80f), size = Size(size.width - 160f, size.height - 160f))
            drawArc(color = rubyColor, angle3.value, 320f, useCenter = false, style = roundStroke(5f), topLeft = Offset(160f, 160f), size = Size(size.width - 320f, size.height - 320f))
        }
        Slider(value = volume.value, onValueChange = { volume.value = it },
            colors = SliderDefaults.colors(
                thumbColor = rubyColor,
                activeTrackColor = rubyColor,
                inactiveTrackColor = Color.LightGray,
            ),
            modifier = Modifier
                .constrainAs(volumeSlider) {
                    top.linkTo(rings.bottom)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 8.dp))
        Text("Volume", Modifier.constrainAs(volumeLabel) {
            start.linkTo(volumeSlider.start)
            end.linkTo(volumeSlider.end)
            top.linkTo(volumeSlider.bottom)
        })
        ChatView(messages = messages.value, Modifier.constrainAs(chat) {
            top.linkTo(volumeLabel.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, 64.dp)
        })
    }

    LaunchedEffect("Square") {
        while (isActive) {
            angle1.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = FastOutSlowInEasing,
                )
            )
            angle2.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing,
                )
            )
            angle3.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = FastOutLinearInEasing
                )
            )
            angle1.snapTo(0f)
            angle2.snapTo(0f)
            angle3.snapTo(0f)
        }
    }
}

@Composable
fun ChatView(messages: List<Message>, modifier: Modifier) {
    Column(modifier = modifier) {
        for (message in messages) ChatMessage(message = message)
    }
}

@Composable
fun ChatMessage(message: Message) {
    ConstraintLayout(modifier = Modifier.padding(vertical = 8.dp)) {
        val (sos, text, time) = createRefs()
        Card(backgroundColor = Color.LightGray, modifier = Modifier
            .constrainAs(sos) {
                start.linkTo(parent.start)
                end.linkTo(text.start, 8.dp)
                top.linkTo(parent.top)
            }
            .width(48.dp)) {
            if (message.sos) Text("SOS", modifier = Modifier.padding(8.dp))
        }
        val messageBackground = if (message.incoming) Color.LightGray else Color.Black
        val messageColor = if (message.incoming) Color.Black else Color.White
        Card(backgroundColor = messageBackground, modifier = Modifier
            .constrainAs(text) {
                start.linkTo(if (message.incoming) sos.end else time.end)
                end.linkTo(if (message.incoming) time.start else parent.end)
                top.linkTo(parent.top)
            }
            .width(200.dp)) {
            Text(message.text, modifier = Modifier.padding(8.dp), overflow = TextOverflow.Visible, color = messageColor)
        }
        Text(message.time, color = Color.LightGray, modifier = Modifier.constrainAs(time) {
            start.linkTo(if (message.incoming) text.end else sos.end)
            end.linkTo(if (message.incoming) parent.end else text.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        })
    }
}