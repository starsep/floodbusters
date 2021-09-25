package org.example.floodbusters.ui.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.example.floodbusters.R
import org.example.floodbusters.api.createApiService
import org.example.floodbusters.api.GroupMember
import org.example.floodbusters.api.user
import org.example.floodbusters.ui.AvatarHeader

val surnames = listOf("Lanz", "Berg", "Senn")
val exampleGroupData = mapOf(
    "Lanz" to GroupMember(avatarId = R.drawable.group0, isSafe = true, name = "Martin Lanz", lastCheck = "26.09.21 21:00"),
    "Berg" to GroupMember(avatarId = R.drawable.group1, isSafe = true, name = "Susane Berg", lastCheck = "26.09.21 20:00"),
    "Senn" to GroupMember(avatarId = R.drawable.group2, isSafe = false, name = "Nicole Senn", lastCheck = "2 minutes ago"),
)

@Composable
fun GroupScreen() {
    val group = remember { mutableStateOf(exampleGroupData) }
    val plusIconPainter: Painter = painterResource(id = R.drawable.ic_plus)
    val scrollState = remember { ScrollState(initial = 0)}
    ConstraintLayout(modifier = Modifier
        .background(Color.White)
        .scrollable(scrollState, orientation = Orientation.Vertical)) {
        val (avatarHeader, groupDescription, myGroupTitle, addToGroupButton, groupAvatars, socialMediaHeader, socialMediaMap) = createRefs()
        AvatarHeader(user = user, modifier = Modifier.constrainAs(avatarHeader) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        })
        Text(text = "In case of an emergency, you may need to know where the people you love are. To form a group we need the approval of the person you want to join, because in case of an emergency we will track the movements of each person in order to induce if the person is safe.",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .constrainAs(groupDescription) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(avatarHeader.bottom)
                }
                .padding(16.dp)
        )
        Text(text = "My group", style = MaterialTheme.typography.h6, modifier = Modifier
            .constrainAs(myGroupTitle) {
                top.linkTo(groupDescription.bottom)
                start.linkTo(parent.start)
            }
            .padding(16.dp))
        IconButton(onClick = { /*TODO*/ }, Modifier.constrainAs(addToGroupButton) {
            end.linkTo(parent.end)
            top.linkTo(myGroupTitle.top)
            bottom.linkTo(myGroupTitle.bottom)
        }) {
            Icon(painter = plusIconPainter, contentDescription = "add group member")
        }
        GroupStatus(modifier = Modifier.constrainAs(groupAvatars) {
            start.linkTo(myGroupTitle.start)
            top.linkTo(myGroupTitle.bottom)
        }, group = group.value.values)
        /*Text(
            text = "Live social media information",
            modifier = Modifier.constrainAs(socialMediaHeader) {
                start.linkTo(parent.start)
                top.linkTo(groupAvatars.bottom)
            })

        AndroidView(factory = {
            MapView(it).apply {
                map = ArcGISMap(BasemapStyle.ARCGIS_IMAGERY)
                setViewpoint(Viewpoint(47.3774, 8.4766, 72000.0))
            }
        }, modifier = Modifier.constrainAs(socialMediaMap) {
            top.linkTo(socialMediaHeader.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }.height(400.dp).fillMaxWidth()) {
        }*/
    }
    LaunchedEffect("get") {
        val apiService = createApiService()
        GlobalScope.launch {
            for (name in surnames) {
                val oldStatus = group.value.getValue(name)
                val status = apiService.getStatus(name)
                group.value = group.value.plus(name to oldStatus.copy(lastCheck = status.lastUpdate, isSafe = status.status == "is in a safe place"))
            }
        }
    }
}

@Composable
fun GroupStatus(modifier: Modifier, group: Collection<GroupMember>) {
    Column(modifier = modifier) {
        for (member in group) GroupMemberStatus(member)
    }
}

@Composable
fun GroupMemberStatus(groupMember: GroupMember) {
    ConstraintLayout {
        val (avatar, icon, name, status, lastCheck) = createRefs()
        val painter: Painter = painterResource(id = groupMember.avatarId)
        val color = if (groupMember.isSafe) Color(0xFF63C01A) else Color(0xFF878686)
        Image(
            painter = painter,
            contentDescription = "group member avatar",
            modifier = Modifier
                .constrainAs(avatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .size(84.dp)
                .padding(8.dp)
                .border(width = 2.dp, color = color, shape = CircleShape)
        )
        val iconPainter = painterResource(id = if (groupMember.isSafe) R.drawable.ic_check_circle_outline else R.drawable.ic_question_mark)
        val iconBackground = if (groupMember.isSafe) Color.Transparent else color
        val iconTint = if (groupMember.isSafe) color else Color.Black
        Icon(painter = iconPainter, contentDescription = if (groupMember.isSafe) "checkmark" else "question mark", modifier = Modifier
            .size(16.dp)
            .constrainAs(icon) {
                bottom.linkTo(avatar.bottom, 8.dp)
                end.linkTo(avatar.end, 8.dp)
            }
            .clip(CircleShape)
            .background(color = iconBackground), tint = iconTint)
        Text(text = groupMember.name, modifier = Modifier.constrainAs(name) {
            start.linkTo(avatar.end)
            bottom.linkTo(status.top)
            top.linkTo(avatar.top)
        })
        val statusText = if (groupMember.isSafe) "is in a safe place" else "we have no information yet"
        Text(text = statusText, modifier = Modifier.constrainAs(status) {
            start.linkTo(avatar.end)
            top.linkTo(name.bottom)
            bottom.linkTo(lastCheck.top)
        })
        Text(text = "Last check: ${groupMember.lastCheck}", modifier = Modifier.constrainAs(lastCheck) {
            start.linkTo(avatar.end)
            top.linkTo(status.bottom)
            bottom.linkTo(avatar.bottom)
        }, color = Color.Gray)
    }
}