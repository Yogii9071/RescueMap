package com.example.disaster_management_app

data class RescueAgency(
    val name: String,
    val type: String,
    val latitude: Double,
    val longitude: Double,
    val phoneNumber: String,
    val email: String
)

object RescueData {
    val agencies = listOf(
        RescueAgency("Fire Department - Chandigarh", "Fire", 30.7333, 76.7794, "0172-5551234", "firechd@example.com"),
        RescueAgency("Government Hospital - Amritsar", "Medical", 31.6340, 74.8723, "0183-2223334", "ghamritsar@example.com"),
        RescueAgency("Police Station - Ludhiana", "Police", 30.9008, 75.8573, "0161-4445678", "policeludhiana@example.com"),
        RescueAgency("Fire Department - Jalandhar", "Fire", 31.3250, 75.5793, "0181-3337777", "firejal@example.com"),
        RescueAgency("Medical Center - Patiala", "Medical", 30.3395, 76.3859, "0175-8881122", "medpatiala@example.com"),
        RescueAgency("Police Station - Bathinda", "Police", 30.2118, 74.9481, "0164-1234567", "policebti@example.com"),
        RescueAgency("Rescue Team - Hoshiarpur", "Fire", 31.5326, 75.9230, "01882-111234", "hoshiarpurfirerescue@example.com"),
        RescueAgency("Civil Hospital - Mohali", "Medical", 30.6903, 76.7113, "0172-9090909", "civilmohali@example.com")
    )
}
