package org.example.floodbusters.dataholder

data class User(
    val name: String,
    val bloodGroup: String,
    val organDonor: Boolean,
    val address: String,
    val floorNumber: String,
    val cityPostalCode: String,
    val mobileNumber: String,
)

val user = User(
    name = "Anna L.",
    bloodGroup = "A Rh+",
    organDonor = true,
    address = "34th Street Side of the Lake",
    floorNumber = "5th",
    cityPostalCode = "Bern, 3001",
    mobileNumber = "0041 79 782 3765",
)