# Number Stepper Component

A Jetpack Compose implementation of a Number Stepper component, similar to iOS/SwiftUI's Stepper.

## Overview

The Number Stepper is a UI component for incrementing and decrementing numeric values with + and - buttons. It's perfect for quantity selectors, volume controls, ratings, and any interface where users need to adjust numeric values.

## Features

- ✅ **Increment/Decrement Controls**: Plus and minus buttons
- ✅ **Configurable Ranges**: Set minimum and maximum values
- ✅ **Custom Step Sizes**: Define increment/decrement amounts
- ✅ **Multiple Style Variants**: Default, Bordered, Compact, Large
- ✅ **Animated Transitions**: Smooth value change animations
- ✅ **Progress Indicator Variant**: Visual representation of value range
- ✅ **Compact Variant**: Space-efficient inline layout
- ✅ **Disabled State Support**: Control interactivity
- ✅ **Customizable Styling**: Colors, sizes, padding, borders

## Usage

### Basic NumberStepper

```kotlin
var quantity by remember { mutableIntStateOf(1) }

NumberStepper(
    label = "Items: $quantity",
    value = quantity,
    onValueChange = { quantity = it },
    min = 0,
    max = 50,
    step = 1
)
```

### With Custom Style

```kotlin
NumberStepper(
    label = "Temperature: $temperature°C",
    value = temperature,
    onValueChange = { temperature = it },
    min = -10,
    max = 50,
    step = 5,
    style = NumberStepperStyle.Bordered
)
```

### Compact Variant

```kotlin
CompactNumberStepper(
    label = "Guests",
    value = guests,
    onValueChange = { guests = it },
    min = 1,
    max = 10,
    step = 1
)
```

### With Progress Indicator

```kotlin
NumberStepperWithFloatingValue(
    label = "Brightness",
    value = brightness,
    onValueChange = { brightness = it },
    min = 0,
    max = 100,
    step = 5,
    unit = "%"
)
```

## Component Variants

### 1. NumberStepper (Default)

Standard stepper with label, value display, and +/- buttons.

### 2. NumberStepperWithFloatingValue

Includes a large value display, progress bar, and full-width buttons.

### 3. CompactNumberStepper

Minimal inline layout perfect for space-constrained UIs.

## Style Configuration

```kotlin
data class NumberStepperStyle(
    val padding: PaddingValues = PaddingValues(16.dp),
    val cornerRadius: Dp = 12.dp,
    val borderWidth: Dp = 1.dp,
    val borderColor: Color? = null,
    val backgroundColor: Color? = null,
    val buttonColor: Color? = null,
    val buttonSize: Dp = 40.dp,
    val showValue: Boolean = true,
    val boldLabel: Boolean = false
)
```

### Built-in Styles

- **Default**: Standard appearance
- **Bordered**: With border outline
- **Compact**: Smaller padding and buttons
- **Large**: Bigger sizing for emphasis

## Parameters

| Parameter       | Type               | Default | Description                 |
| --------------- | ------------------ | ------- | --------------------------- |
| `label`         | String             | -       | Display label text          |
| `value`         | Int                | -       | Current numeric value       |
| `onValueChange` | (Int) -> Unit      | -       | Callback when value changes |
| `min`           | Int                | 0       | Minimum allowed value       |
| `max`           | Int                | 100     | Maximum allowed value       |
| `step`          | Int                | 1       | Increment/decrement amount  |
| `enabled`       | Boolean            | true    | Enable/disable interaction  |
| `style`         | NumberStepperStyle | Default | Style configuration         |

## Use Cases

- Shopping cart quantity selectors
- Volume/brightness controls
- Temperature adjustments
- Rating systems
- Guest/attendee counters
- Speed/level selectors
- Pagination controls
- Zoom level controls

## Comparison with Progress Stepper

| Feature        | Number Stepper          | Progress Stepper            |
| -------------- | ----------------------- | --------------------------- |
| **Purpose**    | Adjust numeric values   | Multi-step workflows        |
| **UI Pattern** | +/- buttons             | Sequential indicators       |
| **Use Case**   | Quantities, settings    | Forms, checkout, onboarding |
| **Navigation** | Single value            | Multiple steps              |
| **Example**    | "Items: 5" with buttons | Step 1 → Step 2 → Step 3    |

## Installation

Copy the files to your project:

- `NumberStepper.kt` - Component implementation
- `NumberStepperScreen.kt` - Demo screen with examples

## Requirements

- Jetpack Compose
- Material Design 3
- Kotlin

## License

MIT License - Free to use in your projects
