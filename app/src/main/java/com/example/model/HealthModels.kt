package com.example.model

enum class ProfileType(val label: String, val role: String) {
    SELF("Me", "Account Holder"),
    CHILD("Maya", "Child (8 yrs)"),
    ELDER("Ravi", "Elderly Dad (74 yrs)")
}

enum class AdherenceStatus {
    TAKEN,
    UPCOMING,
    MISSED
}

data class MedicationItem(
    val id: String,
    val profileType: ProfileType,
    val name: String,
    val dosage: String,
    val instruction: String,
    val scheduledTime: String,
    val status: AdherenceStatus,
    val pillCount: Int = 30,
    val refillDueDays: Int = 12
)

data class VitalRecord(
    val id: String,
    val profileType: ProfileType,
    val title: String,
    val value: String,
    val unit: String,
    val statusText: String,
    val isNormal: Boolean = true,
    val recordedTime: String
)

data class CareAppointment(
    val id: String,
    val profileType: ProfileType,
    val doctor: String,
    val specialty: String,
    val date: String,
    val time: String,
    val location: String
)

data class EmergencyContact(
    val id: String,
    val name: String,
    val relationship: String,
    val phone: String,
    val isPrimary: Boolean = false
)
