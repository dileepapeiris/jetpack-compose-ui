package com.example.jetpackcomposestepper.numberstepper

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Number Stepper Component
 * 
 * A component for incrementing and decrementing numeric values with + and - buttons.
 * Similar to iOS/SwiftUI Stepper component.
 * 
 * @param label The label text to display
 * @param value Current numeric value
 * @param onValueChange Callback when value changes
 * @param min Minimum allowed value (default: 0)
 * @param max Maximum allowed value (default: 100)
 * @param step Increment/decrement step size (default: 1)
 * @param enabled Whether the stepper is enabled (default: true)
 * @param modifier Modifier for the stepper
 * @param style Style configuration for the stepper
 */
@Composable
fun NumberStepper(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    min: Int = 0,
    max: Int = 100,
    step: Int = 1,
    enabled: Boolean = true,
    style: NumberStepperStyle = NumberStepperStyle.Default
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (style.backgroundColor != null) 
                style.backgroundColor 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(style.cornerRadius),
        border = if (style.borderColor != null && style.borderWidth > 0.dp) {
            androidx.compose.foundation.BorderStroke(style.borderWidth, style.borderColor)
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(style.padding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = if (style.boldLabel) FontWeight.Bold else FontWeight.Normal
                ),
                color = if (enabled) 
                    MaterialTheme.colorScheme.onSurfaceVariant 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.weight(1f)
            )
            
            // Stepper buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Minus button
                StepperButton(
                    icon = Icons.Default.Remove,
                    contentDescription = "Decrease",
                    onClick = { 
                        val newValue = value - step
                        if (newValue >= min) {
                            onValueChange(newValue)
                        }
                    },
                    enabled = enabled && value > min,
                    style = style
                )
                
                // Value display with animation
                if (style.showValue) {
                    AnimatedContent(
                        targetState = value,
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInVertically { -it } + fadeIn() togetherWith
                                        slideOutVertically { it } + fadeOut()
                            } else {
                                slideInVertically { it } + fadeIn() togetherWith
                                        slideOutVertically { -it } + fadeOut()
                            }.using(tween(durationMillis = 200))
                        },
                        label = "value_animation"
                    ) { animatedValue ->
                        Text(
                            text = animatedValue.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .widthIn(min = 40.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
                
                // Plus button
                StepperButton(
                    icon = Icons.Default.Add,
                    contentDescription = "Increase",
                    onClick = { 
                        val newValue = value + step
                        if (newValue <= max) {
                            onValueChange(newValue)
                        }
                    },
                    enabled = enabled && value < max,
                    style = style
                )
            }
        }
    }
}

@Composable
private fun StepperButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    enabled: Boolean,
    style: NumberStepperStyle
) {
    FilledTonalIconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(style.buttonSize),
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = if (style.buttonColor != null)
                style.buttonColor
            else
                MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp)
        )
    }
}

/**
 * Style configuration for NumberStepper
 */
data class NumberStepperStyle(
    val padding: PaddingValues = PaddingValues(16.dp),
    val cornerRadius: androidx.compose.ui.unit.Dp = 12.dp,
    val borderWidth: androidx.compose.ui.unit.Dp = 1.dp,
    val borderColor: Color? = null,
    val backgroundColor: Color? = null,
    val buttonColor: Color? = null,
    val buttonSize: androidx.compose.ui.unit.Dp = 40.dp,
    val showValue: Boolean = true,
    val boldLabel: Boolean = false
) {
    companion object {
        val Default = NumberStepperStyle()
        
        val Bordered = NumberStepperStyle(
            borderColor = Color(0xFFE1E5E9),
            borderWidth = 1.dp
        )
        
        val Compact = NumberStepperStyle(
            padding = PaddingValues(12.dp),
            buttonSize = 36.dp,
            cornerRadius = 8.dp
        )
        
        val Large = NumberStepperStyle(
            padding = PaddingValues(20.dp),
            buttonSize = 48.dp,
            cornerRadius = 16.dp,
            boldLabel = true
        )
    }
}

/**
 * Variant of NumberStepper with a floating value display
 */
@Composable
fun NumberStepperWithFloatingValue(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    min: Int = 0,
    max: Int = 100,
    step: Int = 1,
    enabled: Boolean = true,
    unit: String = ""
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge
                )
                
                AnimatedContent(
                    targetState = value,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "value_display"
                ) { animatedValue ->
                    Text(
                        text = "$animatedValue$unit",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Progress indicator
            LinearProgressIndicator(
                progress = (value - min).toFloat() / (max - min).toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilledTonalButton(
                    onClick = { 
                        val newValue = value - step
                        if (newValue >= min) onValueChange(newValue)
                    },
                    enabled = enabled && value > min,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Remove, null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Decrease")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                FilledTonalButton(
                    onClick = { 
                        val newValue = value + step
                        if (newValue <= max) onValueChange(newValue)
                    },
                    enabled = enabled && value < max,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Increase")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

/**
 * Compact variant with inline buttons
 */
@Composable
fun CompactNumberStepper(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    min: Int = 0,
    max: Int = 100,
    step: Int = 1,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalIconButton(
                onClick = { 
                    val newValue = value - step
                    if (newValue >= min) onValueChange(newValue)
                },
                enabled = enabled && value > min,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Remove, 
                    "Decrease",
                    modifier = Modifier.size(16.dp)
                )
            }
            
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.widthIn(min = 30.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            FilledTonalIconButton(
                onClick = { 
                    val newValue = value + step
                    if (newValue <= max) onValueChange(newValue)
                },
                enabled = enabled && value < max,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Add, 
                    "Increase",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
