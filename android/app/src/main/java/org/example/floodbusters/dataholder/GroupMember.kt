package org.example.floodbusters.dataholder

import androidx.annotation.DrawableRes

// possibly change isSafe to Status enum?
data class GroupMember(@DrawableRes val avatarId: Int, val isSafe: Boolean, val name: String, val lastCheck: String)