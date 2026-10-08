package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AddMedicationDialog
import com.example.ui.components.AddVitalDialog
import com.example.ui.components.SosEmergencyDialog
import com.example.ui.screens.CareCircleScreen
import com.example.ui.screens.MedicationsScreen
import com.example.ui.screens.TodayDashboardScreen
import com.example.ui.screens.VitalsScreen
import com.example.ui.theme.ArohaHealthTheme
import com.example.ui.theme.PrimaryTeal
import com.example.ui.theme.SosDanger
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.ArohaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ArohaViewModel = viewModel()
            val state by viewModel.uiState.collectAsState()

            ArohaHealthTheme(isHighContrast = state.isHighContrast) {
                val baseDensity = LocalDensity.current
                val scaledDensity = remember(baseDensity, state.fontScaleMultiplier) {
                    Density(
                        density = baseDensity.density,
                        fontScale = baseDensity.fontScale * state.fontScaleMultiplier
                    )
                }

                CompositionLocalProvider(LocalDensity provides scaledDensity) {
                    ArohaApp(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArohaApp(
    viewModel: ArohaViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PrimaryTeal),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Aroha",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.5).sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Health Companion",
                                style = MaterialTheme.typography.labelSmall,
                                color = PrimaryTeal
                            )
                        }
                    }
                },
                actions = {
                    // Quick Accessibility Controls: High-Contrast & Text Scaling
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // High Contrast Toggle
                        IconButton(
                            onClick = { viewModel.toggleHighContrast() },
                            modifier = Modifier.testTag("toggle_contrast_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Contrast,
                                contentDescription = "Toggle High Contrast",
                                tint = if (state.isHighContrast) PrimaryTeal else TextSecondary
                            )
                        }

                        // Text Scaling Cycle (100% -> 125% -> 150%)
                        IconButton(
                            onClick = {
                                val nextScale = when (state.fontScaleMultiplier) {
                                    1.0f -> 1.25f
                                    1.25f -> 1.5f
                                    else -> 1.0f
                                }
                                viewModel.setFontScale(nextScale)
                            },
                            modifier = Modifier.testTag("toggle_font_scale_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = "Text Size Scale",
                                tint = if (state.fontScaleMultiplier > 1.0f) PrimaryTeal else TextSecondary
                            )
                        }

                        // Persistent 56px SOS Emergency Button in Header / Persistent Action
                        Button(
                            onClick = { viewModel.setSosDialogVisible(true) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SosDanger,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .height(44.dp)
                                .testTag("header_sos_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "SOS",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "SOS",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = state.currentTab == "today",
                    onClick = { viewModel.selectTab("today") },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Today") },
                    label = { Text("Today") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_item_today")
                )
                NavigationBarItem(
                    selected = state.currentTab == "meds",
                    onClick = { viewModel.selectTab("meds") },
                    icon = { Icon(Icons.Default.Medication, contentDescription = "Meds") },
                    label = { Text("Medications") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_item_meds")
                )
                NavigationBarItem(
                    selected = state.currentTab == "vitals",
                    onClick = { viewModel.selectTab("vitals") },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Vitals") },
                    label = { Text("Vitals") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_item_vitals")
                )
                NavigationBarItem(
                    selected = state.currentTab == "care",
                    onClick = { viewModel.selectTab("care") },
                    icon = { Icon(Icons.Default.Group, contentDescription = "Care Circle") },
                    label = { Text("Care Circle") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("nav_item_care")
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // SOS Notification Alert Bar if triggered
            AnimatedVisibility(
                visible = state.sosTriggeredFeedback != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                state.sosTriggeredFeedback?.let { feedbackMsg ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .border(2.dp, SosDanger, RoundedCornerShape(12.dp)),
                        color = Color(0xFFFEF2F2),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = SosDanger,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = feedbackMsg,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color = TextPrimary
                                )
                            }
                            IconButton(onClick = { viewModel.dismissSosFeedback() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Main Screens by Tab
            when (state.currentTab) {
                "today" -> TodayDashboardScreen(
                    state = state,
                    onSelectProfile = { viewModel.selectProfile(it) },
                    onToggleMedication = { viewModel.toggleMedicationStatus(it) },
                    onOpenAddMedication = { viewModel.setShowAddMedDialog(true) },
                    onOpenAddVital = { viewModel.setShowAddVitalDialog(true) },
                    onNavigateTab = { viewModel.selectTab(it) }
                )
                "meds" -> MedicationsScreen(
                    state = state,
                    onSelectProfile = { viewModel.selectProfile(it) },
                    onToggleMedication = { viewModel.toggleMedicationStatus(it) },
                    onOpenAddMedication = { viewModel.setShowAddMedDialog(true) }
                )
                "vitals" -> VitalsScreen(
                    state = state,
                    onSelectProfile = { viewModel.selectProfile(it) },
                    onOpenAddVital = { viewModel.setShowAddVitalDialog(true) }
                )
                "care" -> CareCircleScreen(
                    state = state,
                    onSelectProfile = { viewModel.selectProfile(it) },
                    onTriggerSos = { viewModel.setSosDialogVisible(true) }
                )
            }
        }
    }

    // Modal Dialogs
    if (state.showSosDialog) {
        SosEmergencyDialog(
            currentProfile = state.selectedProfile,
            emergencyContacts = state.emergencyContacts,
            onDismiss = { viewModel.setSosDialogVisible(false) },
            onConfirmSos = { viewModel.triggerSosAlert() }
        )
    }

    if (state.showAddMedDialog) {
        AddMedicationDialog(
            currentProfile = state.selectedProfile,
            onDismiss = { viewModel.setShowAddMedDialog(false) },
            onAdd = { name, dosage, instruction, time ->
                viewModel.addMedication(name, dosage, instruction, time)
            }
        )
    }

    if (state.showAddVitalDialog) {
        AddVitalDialog(
            currentProfile = state.selectedProfile,
            onDismiss = { viewModel.setShowAddVitalDialog(false) },
            onAdd = { title, value, unit, status ->
                viewModel.addVitalRecord(title, value, unit, status)
            }
        )
    }
}
