package com.example.jetpackcomposestepper.stepper

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Vertical Stepper Component
 * 
 * Features:
 * - Linear and non-linear navigation
 * - Step states with visual indicators
 * - Expandable/collapsible steps
 * - Custom content for each step
 * - Optional steps
 * - Error states
 */
@Composable
fun VerticalStepper(
    steps: List<Step>,
    currentStep: Int,
    mode: StepperMode = StepperMode.LINEAR,
    style: StepperStyle = StepperStyle(),
    onStepClick: (Int) -> Unit = {},
    stepContent: @Composable (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        steps.forEachIndexed { index, step ->
            val isClickable = mode == StepperMode.NON_LINEAR || 
                             (mode == StepperMode.LINEAR && index <= currentStep)
            
            VerticalStepItem(
                step = step,
                stepNumber = index + 1,
                isActive = index == currentStep,
                isLast = index == steps.lastIndex,
                style = style,
                isClickable = isClickable,
                onStepClick = { onStepClick(index) },
                stepContent = { stepContent(index) }
            )
        }
    }
}

@Composable
private fun VerticalStepItem(
    step: Step,
    stepNumber: Int,
    isActive: Boolean,
    isLast: Boolean,
    style: StepperStyle,
    isClickable: Boolean,
    onStepClick: () -> Unit,
    stepContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Left side: Indicator and connector
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(50.dp)
        ) {
            // Step indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        when (step.state) {
                            StepState.COMPLETED -> MaterialTheme.colorScheme.primary
                            StepState.ACTIVE -> MaterialTheme.colorScheme.primary
                            StepState.ERROR -> MaterialTheme.colorScheme.error
                            StepState.INACTIVE -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                    .then(
                        if (isActive && step.state == StepState.INACTIVE) {
                            Modifier.border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        } else Modifier
                    )
                    .clickable(enabled = isClickable) { onStepClick() },
                contentAlignment = Alignment.Center
            ) {
                val contentColor = when (step.state) {
                    StepState.COMPLETED, StepState.ACTIVE -> MaterialTheme.colorScheme.onPrimary
                    StepState.ERROR -> MaterialTheme.colorScheme.onError
                    StepState.INACTIVE -> MaterialTheme.colorScheme.onSurfaceVariant
                }
                
                when (step.state) {
                    StepState.COMPLETED -> {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completed",
                            tint = contentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    StepState.ERROR -> {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            tint = contentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    else -> {
                        if (style.showStepNumber) {
                            Text(
                                text = stepNumber.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = contentColor
                            )
                        }
                    }
                }
            }
            
            // Vertical connector
            if (!isLast && style.showConnector) {
                Divider(
                    modifier = Modifier
                        .width(2.dp)
                        .height(80.dp)
                        .background(
                            if (step.state == StepState.COMPLETED) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                )
            }
        }
        
        // Right side: Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            // Title and subtitle
            Text(
                text = step.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    step.state == StepState.ERROR -> MaterialTheme.colorScheme.error
                    isActive -> MaterialTheme.colorScheme.primary
                    step.state == StepState.COMPLETED -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.clickable(enabled = isClickable) { onStepClick() }
            )
            
            if (step.subtitle != null && style.showStepSubtitle) {
                Text(
                    text = step.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (step.isOptional && style.showStepSubtitle) {
                Text(
                    text = "Optional",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Error message
            if (step.state == StepState.ERROR && step.errorMessage != null) {
                Text(
                    text = step.errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
            // Step content (shown when active)
            AnimatedVisibility(
                visible = isActive,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    stepContent()
                }
            }
            
            if (!isLast) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
