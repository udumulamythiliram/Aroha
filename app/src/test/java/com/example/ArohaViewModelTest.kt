package com.example

import com.example.model.AdherenceStatus
import com.example.model.ProfileType
import com.example.viewmodel.ArohaViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ArohaViewModelTest {

    @Test
    fun testSelectProfileUpdatesState() {
        val viewModel = ArohaViewModel()
        assertEquals(ProfileType.SELF, viewModel.uiState.value.selectedProfile)

        viewModel.selectProfile(ProfileType.ELDER)
        assertEquals(ProfileType.ELDER, viewModel.uiState.value.selectedProfile)

        viewModel.selectProfile(ProfileType.CHILD)
        assertEquals(ProfileType.CHILD, viewModel.uiState.value.selectedProfile)
    }

    @Test
    fun testToggleMedicationAdherence() {
        val viewModel = ArohaViewModel()
        val med = viewModel.uiState.value.medications.first()
        val originalStatus = med.status

        viewModel.toggleMedicationStatus(med.id)
        val updatedMed = viewModel.uiState.value.medications.first { it.id == med.id }

        if (originalStatus == AdherenceStatus.TAKEN) {
            assertEquals(AdherenceStatus.UPCOMING, updatedMed.status)
        } else {
            assertEquals(AdherenceStatus.TAKEN, updatedMed.status)
        }
    }

    @Test
    fun testAddMedicationAndVitals() {
        val viewModel = ArohaViewModel()
        viewModel.selectProfile(ProfileType.ELDER)
        val initialMedCount = viewModel.uiState.value.medications.size
        val initialVitalCount = viewModel.uiState.value.vitals.size

        viewModel.addMedication("Metoprolol", "25 mg", "With morning meal", "07:30 AM")
        assertEquals(initialMedCount + 1, viewModel.uiState.value.medications.size)

        viewModel.addVitalRecord("Heart Rate", "72", "bpm", "Normal")
        assertEquals(initialVitalCount + 1, viewModel.uiState.value.vitals.size)
    }

    @Test
    fun testHighContrastAndSosAlert() {
        val viewModel = ArohaViewModel()
        viewModel.toggleHighContrast()
        assertTrue(viewModel.uiState.value.isHighContrast)

        viewModel.triggerSosAlert()
        assertNotNull(viewModel.uiState.value.sosTriggeredFeedback)

        viewModel.dismissSosFeedback()
        assertEquals(null, viewModel.uiState.value.sosTriggeredFeedback)
    }
}
