package com.example.jetpackcomposestepper.numberstepper

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Demo screen showcasing NumberStepper component with various use cases
 * Similar to the Expo/SwiftUI Stepper example
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberStepperScreen(
    onBackClick: () -> Unit = {}
) {
    var quantity by remember { mutableIntStateOf(1) }
    var temperature by remember { mutableIntStateOf(20) }
    var volume by remember { mutableIntStateOf(50) }
    var speed by remember { mutableIntStateOf(5) }
    var rating by remember { mutableIntStateOf(3) }
    var cartItems by remember { mutableIntStateOf(0) }
    var brightness by remember { mutableIntStateOf(75) }
    var fontSize by remember { mutableIntStateOf(16) }
    var guests by remember { mutableIntStateOf(2) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Number Stepper Component") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "Interactive stepper controls for incrementing and decrementing values",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Basic Steppers Section
            SectionTitle("Basic Steppers")
            
            NumberStepper(
                label = "Items: $quantity",
                value = quantity,
                onValueChange = { quantity = it },
                min = 0,
                max = 50,
                step = 1,
                style = NumberStepperStyle.Bordered
            )
            
            NumberStepper(
                label = "Temperature: $temperature°C",
                value = temperature,
                onValueChange = { temperature = it },
                min = -10,
                max = 50,
                step = 5,
                style = NumberStepperStyle.Bordered
            )
            
            NumberStepper(
                label = "Volume: $volume%",
                value = volume,
                onValueChange = { volume = it },
                min = 0,
                max = 100,
                step = 10,
                style = NumberStepperStyle.Bordered
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // Different Use Cases
            SectionTitle("Different Use Cases")
            
            NumberStepper(
                label = "Speed: $speed/10",
                value = speed,
                onValueChange = { speed = it },
                min = 1,
                max = 10,
                step = 1,
                style = NumberStepperStyle.Default
            )
            
            NumberStepper(
                label = "Rating: $rating stars",
                value = rating,
                onValueChange = { rating = it },
                min = 1,
                max = 5,
                step = 1,
                style = NumberStepperStyle.Default
            )
            
            NumberStepper(
                label = "Cart Items: $cartItems items",
                value = cartItems,
                onValueChange = { cartItems = it },
                min = 0,
                max = 20,
                step = 2,
                style = NumberStepperStyle.Default
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // Different Styles
            SectionTitle("Different Styles")
            
            NumberStepper(
                label = "Compact Style",
                value = brightness,
                onValueChange = { brightness = it },
                min = 0,
                max = 100,
                step = 5,
                style = NumberStepperStyle.Compact
            )
            
            NumberStepper(
                label = "Large Style",
                value = fontSize,
                onValueChange = { fontSize = it },
                min = 12,
                max = 24,
                step = 2,
                style = NumberStepperStyle.Large
            )
            
            NumberStepper(
                label = "Without Value Display",
                value = guests,
                onValueChange = { guests = it },
                min = 1,
                max = 10,
                step = 1,
                style = NumberStepperStyle.Default.copy(showValue = false)
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // Advanced Variants
            SectionTitle("Advanced Variants")
            
            NumberStepperWithFloatingValue(
                label = "Brightness",
                value = brightness,
                onValueChange = { brightness = it },
                min = 0,
                max = 100,
                step = 5,
                unit = "%"
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            CompactNumberStepper(
                label = "Guests",
                value = guests,
                onValueChange = { guests = it },
                min = 1,
                max = 10,
                step = 1
            )
            
            CompactNumberStepper(
                label = "Quantity",
                value = quantity,
                onValueChange = { quantity = it },
                min = 0,
                max = 50,
                step = 1
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // Disabled State
            SectionTitle("Disabled State")
            
            NumberStepper(
                label = "Disabled Stepper",
                value = 5,
                onValueChange = {},
                enabled = false,
                style = NumberStepperStyle.Bordered
            )
            
            // Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "✨ Features",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    val features = listOf(
                        "Increment/decrement numeric values",
                        "Configurable min/max ranges",
                        "Custom step sizes",
                        "Multiple style variants",
                        "Animated value transitions",
                        "Disabled state support",
                        "Compact and large layouts",
                        "Progress indicator variant"
                    )
                    
                    features.forEach { feature ->
                        Text(
                            text = "• $feature",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
