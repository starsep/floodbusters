package org.example.floodbusters.ui.consent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.example.floodbusters.R

@Composable
fun ConsentScreen(goNext: () -> Unit) {
    val pushPermission = remember { mutableStateOf(false) }
    val locationPermission = remember { mutableStateOf(false) }
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
        val (permissionCard, continueWithoutAllow) = createRefs()
        Card(backgroundColor = Color(0xFF2D2D2D), modifier = Modifier.fillMaxHeight(0.8f)) { ConstraintLayout {
            val (logo, permissionInformation, allowPush, allowLocation, startButton) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "flooded house danger sign",
                modifier = Modifier.constrainAs(logo) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Text(text = "Activating notifications allows you to be actively informed in case of an emergency",
                modifier = Modifier.constrainAs(permissionInformation) {
                    start.linkTo(parent.start, 40.dp)
                    end.linkTo(parent.end, 40.dp)
                    top.linkTo(logo.bottom)
                }, color = Color.White, style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
            Card(modifier = Modifier
                .constrainAs(allowPush) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(permissionInformation.bottom)
                    bottom.linkTo(allowLocation.top)
                }
                .fillMaxWidth()
                .requiredHeight(72.dp)
                .padding(horizontal = 16.dp)) {
                ConstraintLayout {
                    val (radio, text) = createRefs()
                    Checkbox(checked = pushPermission.value, onCheckedChange = { pushPermission.value = pushPermission.value.not() }, modifier = Modifier.constrainAs(radio) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(text.start)
                    })
                    Text(text = "Allow alerts via push notification", modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, style = MaterialTheme.typography.subtitle1)
                }
            }
            Card(modifier = Modifier
                .constrainAs(allowLocation) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(allowPush.bottom)
                    bottom.linkTo(startButton.top)
                }
                .fillMaxWidth()
                .requiredHeight(72.dp)
                .padding(horizontal = 16.dp)) {
                ConstraintLayout {
                    val (radio, text) = createRefs()
                    Checkbox(checked = locationPermission.value, onCheckedChange = { locationPermission.value = locationPermission.value.not() }, modifier = Modifier.constrainAs(radio) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(text.start)
                    })
                    Text(text = "Allow location for current weather\nforecasts and safe verification", modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, style = MaterialTheme.typography.subtitle1)
                }
            }
            TextButton(onClick = goNext, modifier = Modifier.constrainAs(startButton) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(allowLocation.bottom)
                bottom.linkTo(parent.bottom)
            }) {
                Text(text = "start", color = Color.White, textDecoration = TextDecoration.Underline, style = MaterialTheme.typography.body1)
            }
        }}
        TextButton(onClick = goNext, modifier = Modifier.constrainAs(continueWithoutAllow) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(permissionCard.bottom)
            bottom.linkTo(parent.bottom)
        }) {
            Text(text = "continue without allow", color = Color.Black, textDecoration = TextDecoration.Underline)
        }
    }
}