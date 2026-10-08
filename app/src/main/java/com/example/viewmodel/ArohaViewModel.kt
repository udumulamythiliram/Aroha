package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.AdherenceStatus
import com.example.model.CareAppointment
import com.example.model.EmergencyContact
import com.example.model.MedicationItem
import com.example.model.ProfileType
import com.example.model.VitalRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ArohaUiState(
    val selectedProfile: ProfileType = ProfileType.SELF,
    val isHighContrast: Boolean = false,
    val fontScaleMultiplier: Float = 1.0f,
    val currentTab: String = "today", // today, meds, vitals, care
    val showSosDialog: Boolean = false,
    val showAddMedDialog: Boolean = false,
    val showAddVitalDialog: Boolean = false,
    val medications: List<MedicationItem> = initialMedications,
    val vitals: List<VitalRecord> = initialVitals,
    val appointments: List<CareAppointment> = initialAppointments,
    val emergencyContacts: List<EmergencyContact> = initialContacts,
    val sosTriggeredFeedback: String? = null
)

private val initialMedications = listOf(
    MedicationItem(
        id = "m1",
        profileType = ProfileType.SELF,
        name = "Omega-3 & CoQ10",
        dosage = "1000 mg",
        instruction = "Take with morning meal",
        scheduledTime = "08:00 AM",
        status = AdherenceStatus.TAKEN,
        pillCount = 45,
        refillDueDays = 22
    ),
    MedicationItem(
        id = "m2",
        profileType = ProfileType.SELF,
        name = "Vitamin D3",
        dosage = "2000 IU",
        instruction = "Daily with water",
        scheduledTime = "01:00 PM",
        status = AdherenceStatus.UPCOMING,
        pillCount = 18,
        refillDueDays = 9
    ),
    MedicationItem(
        id = "m3",
        profileType = ProfileType.SELF,
        name = "Magnesium Glycinate",
        dosage = "200 mg",
        instruction = "Take 30 mins before sleep",
        scheduledTime = "09:30 PM",
        status = AdherenceStatus.UPCOMING,
        pillCount = 30,
        refillDueDays = 15
    ),
    // Child Maya
    MedicationItem(
        id = "m4",
        profileType = ProfileType.CHILD,
        name = "Multivitamin Pediatric Chewable",
        dosage = "1 tablet",
        instruction = "After breakfast",
        scheduledTime = "08:30 AM",
        status = AdherenceStatus.TAKEN,
        pillCount = 15,
        refillDueDays = 7
    ),
    MedicationItem(
        id = "m5",
        profileType = ProfileType.CHILD,
        name = "Amoxicillin Suspension",
        dosage = "5 mL (250mg)",
        instruction = "Finish 7-day course (Day 5)",
        scheduledTime = "02:00 PM",
        status = AdherenceStatus.UPCOMING,
        pillCount = 10,
        refillDueDays = 3
    ),
    // Elder Ravi
    MedicationItem(
        id = "m6",
        profileType = ProfileType.ELDER,
        name = "Amlodipine Besylate",
        dosage = "5 mg",
        instruction = "Blood pressure control, morning",
        scheduledTime = "08:00 AM",
        status = AdherenceStatus.TAKEN,
        pillCount = 14,
        refillDueDays = 6
    ),
    MedicationItem(
        id = "m7",
        profileType = ProfileType.ELDER,
        name = "Metformin HCl",
        dosage = "500 mg",
        instruction = "Take with lunch to avoid GI upset",
        scheduledTime = "12:30 PM",
        status = AdherenceStatus.UPCOMING,
        pillCount = 28,
        refillDueDays = 14
    ),
    MedicationItem(
        id = "m8",
        profileType = ProfileType.ELDER,
        name = "Atorvastatin Calcium",
        dosage = "20 mg",
        instruction = "Evening cholesterol dose",
        scheduledTime = "09:00 PM",
        status = AdherenceStatus.UPCOMING,
        pillCount = 20,
        refillDueDays = 10
    )
)

private val initialVitals = listOf(
    VitalRecord("v1", ProfileType.SELF, "Blood Pressure", "118/76", "mmHg", "Optimal", true, "Today, 8:15 AM"),
    VitalRecord("v2", ProfileType.SELF, "Resting Heart Rate", "64", "bpm", "Normal", true, "Today, 8:15 AM"),
    VitalRecord("v3", ProfileType.SELF, "Sleep Duration", "7.5", "hrs", "Restorative", true, "Last night"),
    
    VitalRecord("v4", ProfileType.CHILD, "Body Temperature", "98.4", "°F", "Normal", true, "Today, 8:45 AM"),
    VitalRecord("v5", ProfileType.CHILD, "Hydration Log", "4", "glasses", "On Track", true, "Today"),

    VitalRecord("v6", ProfileType.ELDER, "Blood Pressure", "132/84", "mmHg", "Slightly Elevated", true, "Today, 9:00 AM"),
    VitalRecord("v7", ProfileType.ELDER, "Fasting Blood Glucose", "108", "mg/dL", "Well Controlled", true, "Today, 7:30 AM"),
    VitalRecord("v8", ProfileType.ELDER, "Oxygen Saturation", "97", "% SpO2", "Stable", true, "Today, 9:00 AM")
)

private val initialAppointments = listOf(
    CareAppointment("a1", ProfileType.SELF, "Dr. Sarah Lin", "Preventive Care & Wellness", "Oct 18, 2026", "10:30 AM", "Evergreen Medical Center, Rm 302"),
    CareAppointment("a2", ProfileType.CHILD, "Dr. Marcus Vance", "Pediatric Checkup", "Oct 24, 2026", "02:15 PM", "Children's Health Clinic"),
    CareAppointment("a3", ProfileType.ELDER, "Dr. Anita Desai", "Cardiology Review", "Oct 14, 2026", "11:00 AM", "Memorial Heart Institute, Suite 410")
)

private val initialContacts = listOf(
    EmergencyContact("c1", "Nurse Advice Hotline 24/7", "Clinical Triage", "1-800-555-0199", true),
    EmergencyContact("c2", "Dr. Anita Desai", "Primary Physician (Dad)", "(555) 234-5678", false),
    EmergencyContact("c3", "Paramedic / Ambulance Service", "Emergency Dispatch", "911", true)
)

class ArohaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ArohaUiState())
    val uiState: StateFlow<ArohaUiState> = _uiState.asStateFlow()

    fun selectProfile(profile: ProfileType) {
        _uiState.update { it.copy(selectedProfile = profile) }
    }

    fun toggleHighContrast() {
        _uiState.update { it.copy(isHighContrast = !it.isHighContrast) }
    }

    fun setFontScale(scale: Float) {
        _uiState.update { it.copy(fontScaleMultiplier = scale) }
    }

    fun selectTab(tab: String) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun toggleMedicationStatus(medicationId: String) {
        _uiState.update { state ->
            val updated = state.medications.map { med ->
                if (med.id == medicationId) {
                    val nextStatus = when (med.status) {
                        AdherenceStatus.TAKEN -> AdherenceStatus.UPCOMING
                        AdherenceStatus.UPCOMING -> AdherenceStatus.TAKEN
                        AdherenceStatus.MISSED -> AdherenceStatus.TAKEN
                    }
                    med.copy(status = nextStatus)
                } else med
            }
            state.copy(medications = updated)
        }
    }

    fun markMedicationMissed(medicationId: String) {
        _uiState.update { state ->
            val updated = state.medications.map { med ->
                if (med.id == medicationId) med.copy(status = AdherenceStatus.MISSED) else med
            }
            state.copy(medications = updated)
        }
    }

    fun addMedication(name: String, dosage: String, instruction: String, time: String) {
        val newMed = MedicationItem(
            id = "m_${System.currentTimeMillis()}",
            profileType = _uiState.value.selectedProfile,
            name = name,
            dosage = dosage,
            instruction = instruction,
            scheduledTime = time,
            status = AdherenceStatus.UPCOMING
        )
        _uiState.update {
            it.copy(
                medications = it.medications + newMed,
                showAddMedDialog = false
            )
        }
    }

    fun addVitalRecord(title: String, value: String, unit: String, statusText: String) {
        val newVital = VitalRecord(
            id = "v_${System.currentTimeMillis()}",
            profileType = _uiState.value.selectedProfile,
            title = title,
            value = value,
            unit = unit,
            statusText = statusText,
            isNormal = true,
            recordedTime = "Just now"
        )
        _uiState.update {
            it.copy(
                vitals = it.vitals + newVital,
                showAddVitalDialog = false
            )
        }
    }

    fun setSosDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(showSosDialog = visible) }
    }

    fun triggerSosAlert() {
        val profile = _uiState.value.selectedProfile
        _uiState.update {
            it.copy(
                showSosDialog = false,
                sosTriggeredFeedback = "SOS broadcast dispatched for ${profile.label} (${profile.role}). Emergency contacts alerted."
            )
        }
    }

    fun dismissSosFeedback() {
        _uiState.update { it.copy(sosTriggeredFeedback = null) }
    }

    fun setShowAddMedDialog(show: Boolean) {
        _uiState.update { it.copy(showAddMedDialog = show) }
    }

    fun setShowAddVitalDialog(show: Boolean) {
        _uiState.update { it.copy(showAddVitalDialog = show) }
    }
}
