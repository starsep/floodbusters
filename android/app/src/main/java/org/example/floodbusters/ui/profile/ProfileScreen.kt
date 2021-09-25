package org.example.floodbusters.ui.profile

import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.example.floodbusters.R
import org.example.floodbusters.api.user


@Composable
fun ProfileScreen() {
    val scrollState = remember { ScrollState(initial = 0) }
    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
    ) {
        val (
            profileCard, generalInformationLabel, addressLabel, addressTextField,
            floorNumberLabel, floorNumberTextField, cityPostalNumberLabel, cityPostalNumberTextField,
            mobileNumberLabel, mobileNumberTextField,
        ) = createRefs()
        val userProfileState = remember { mutableStateOf(user) }

        Card(backgroundColor = Color(0xFFF2F3F7), modifier = Modifier
            .constrainAs(profileCard) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .fillMaxHeight(0.4f)
            .fillMaxWidth()) {
            ConstraintLayout {
                val (avatar, name, bloodGroupLabel, bloodGroup,
                    organDonorLabel, organDonor) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.user_avatar),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .constrainAs(avatar) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top, 32.dp)
                        }
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.Gray)

                )
                Text(
                    text = userProfileState.value.name, modifier = Modifier.constrainAs(name) {
                        start.linkTo(avatar.start)
                        end.linkTo(avatar.end)
                        top.linkTo(avatar.bottom)
                    }, style = MaterialTheme.typography.h4)
                Text(text = "Blood Group", modifier = Modifier.constrainAs(bloodGroupLabel) {
                    top.linkTo(name.bottom)
                    bottom.linkTo(bloodGroup.top)
                    start.linkTo(parent.start)
                    end.linkTo(organDonorLabel.start)
                }, color = Color.Gray,
                style = MaterialTheme.typography.h6)
                Text(
                    text = userProfileState.value.bloodGroup,
                    modifier = Modifier.constrainAs(bloodGroup) {
                        top.linkTo(bloodGroupLabel.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(bloodGroupLabel.start)
                        end.linkTo(bloodGroupLabel.end)
                    },
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.h5,
                )
                Text(text = "Organ Donor", modifier = Modifier.constrainAs(organDonorLabel) {
                    top.linkTo(name.bottom)
                    bottom.linkTo(organDonor.top)
                    start.linkTo(bloodGroupLabel.end)
                    end.linkTo(parent.end)
                }, color = Color.Gray,
                style = MaterialTheme.typography.h6)

                Text(
                    text = if (userProfileState.value.organDonor) "yes" else "no",
                    modifier = Modifier.constrainAs(organDonor) {
                        top.linkTo(organDonorLabel.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(organDonorLabel.start)
                        end.linkTo(organDonorLabel.end)
                    },
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.h5,
                )
            }
        }

        Text(
            text = "General Information",
            modifier = Modifier.constrainAs(generalInformationLabel) {
                top.linkTo(profileCard.bottom)
                start.linkTo(addressTextField.start)
            }.padding(8.dp),
            style = MaterialTheme.typography.h6)
        Text(text = "Address", modifier = Modifier.constrainAs(addressLabel) {
            top.linkTo(generalInformationLabel.bottom)
            start.linkTo(addressTextField.start)
        }, style = MaterialTheme.typography.overline)
        TextField(
            value = userProfileState.value.address,
            onValueChange = { userProfileState.value = userProfileState.value.copy(address = it) },
            modifier = Modifier.constrainAs(addressTextField) {
                top.linkTo(addressLabel.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        Text(text = "Floor Number", modifier = Modifier.constrainAs(floorNumberLabel) {
            top.linkTo(addressTextField.bottom)
            start.linkTo(floorNumberTextField.start)
        }, style = MaterialTheme.typography.overline)
        TextField(
            value = userProfileState.value.floorNumber,
            onValueChange = {
                userProfileState.value = userProfileState.value.copy(floorNumber = it)
            },
            modifier = Modifier.constrainAs(floorNumberTextField) {
                top.linkTo(floorNumberLabel.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        Text(text = "City, Postal Number", modifier = Modifier.constrainAs(cityPostalNumberLabel) {
            top.linkTo(floorNumberTextField.bottom)
            start.linkTo(cityPostalNumberTextField.start)
        }, style = MaterialTheme.typography.overline)
        TextField(
            value = userProfileState.value.cityPostalCode,
            onValueChange = {
                userProfileState.value = userProfileState.value.copy(cityPostalCode = it)
            },
            modifier = Modifier.constrainAs(cityPostalNumberTextField) {
                top.linkTo(cityPostalNumberLabel.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        Text(text = "Mobile Number", modifier = Modifier.constrainAs(mobileNumberLabel) {
            top.linkTo(cityPostalNumberTextField.bottom)
            start.linkTo(mobileNumberTextField.start)
        }, style = MaterialTheme.typography.overline)
        TextField(
            value = userProfileState.value.mobileNumber,
            onValueChange = {
                userProfileState.value = userProfileState.value.copy(mobileNumber = it)
            },
            modifier = Modifier.constrainAs(mobileNumberTextField) {
                top.linkTo(mobileNumberLabel.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
    }
}