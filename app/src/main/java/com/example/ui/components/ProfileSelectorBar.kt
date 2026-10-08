package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Elderly
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProfileType
import com.example.ui.theme.ProfileChildBorder
import com.example.ui.theme.ProfileChildText
import com.example.ui.theme.ProfileChildTint
import com.example.ui.theme.ProfileElderBorder
import com.example.ui.theme.ProfileElderText
import com.example.ui.theme.ProfileElderTint
import com.example.ui.theme.ProfileSelfBorder
import com.example.ui.theme.ProfileSelfText
import com.example.ui.theme.ProfileSelfTint
import com.example.ui.theme.TextPrimary

@Composable
fun ProfileSelectorBar(
    selectedProfile: ProfileType,
    onSelectProfile: (ProfileType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileType.values().forEach { profile ->
            val isSelected = profile == selectedProfile
            val (tintColor, borderColor, textColor) = when (profile) {
                ProfileType.SELF -> Triple(ProfileSelfTint, ProfileSelfBorder, ProfileSelfText)
                ProfileType.CHILD -> Triple(ProfileChildTint, ProfileChildBorder, ProfileChildText)
                ProfileType.ELDER -> Triple(ProfileElderTint, ProfileElderBorder, ProfileElderText)
            }

            val icon = when (profile) {
                ProfileType.SELF -> Icons.Default.Person
                ProfileType.CHILD -> Icons.Default.ChildCare
                ProfileType.ELDER -> Icons.Default.Elderly
            }

            val bgAnim by animateColorAsState(
                targetValue = if (isSelected) tintColor else Color.Transparent,
                label = "profileBg"
            )

            val borderModifier = if (isSelected) {
                Modifier.border(2.dp, TextPrimary, RoundedCornerShape(12.dp))
            } else {
                Modifier.border(1.dp, borderColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .then(borderModifier)
                    .background(bgAnim)
                    .clickable { onSelectProfile(profile) }
                    .testTag("profile_pill_${profile.name.lowercase()}")
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) textColor.copy(alpha = 0.2f) else tintColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = profile.label,
                            tint = textColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.label,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = if (isSelected) TextPrimary else textColor
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = TextPrimary,
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = when (profile) {
                                ProfileType.SELF -> "Account"
                                ProfileType.CHILD -> "Child"
                                ProfileType.ELDER -> "Dad"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = textColor.copy(alpha = 0.85f),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
