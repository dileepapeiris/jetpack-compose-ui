# Jetpack Compose Stepper

A comprehensive Android project showcasing a feature-rich Stepper component built with Jetpack Compose.

## 🎯 Features

### Stepper Component Features

- **Multiple Layouts**: Horizontal and Vertical stepper layouts
- **Navigation Modes**:
  - **Linear Mode**: Sequential step completion required
  - **Non-Linear Mode**: Free navigation between any steps
- **Step States**:
  - Active (current step)
  - Completed (finished steps)
  - Inactive (upcoming steps)
  - Error (validation failed)
- **Visual Indicators**:
  - Step numbers
  - Check icons for completed steps
  - Error icons for failed validations
  - Connecting lines between steps
- **Customization**:
  - Optional steps
  - Custom styling
  - Animations
  - Compact mode
  - Show/hide subtitles and connectors
- **State Management**:
  - ViewModel integration
  - Composable state holder
  - Progress tracking

### Demo Examples Included

1. **Horizontal Linear Stepper**

   - Sequential step navigation
   - Progress indicator
   - Step validation

2. **Horizontal Non-Linear Stepper**

   - Free step navigation
   - Click any step to jump
   - Independent step completion

3. **Vertical Linear Stepper**

   - Expandable step content
   - Sequential flow
   - Optional steps support

4. **Vertical Non-Linear Stepper**

   - Collapsible content areas
   - Free navigation
   - Edit completed steps

5. **Multi-Step Form Example**

   - Registration form with validation
   - Error handling
   - Optional preferences step
   - Terms acceptance

6. **Checkout Process Example**
   - E-commerce checkout flow
   - Cart → Shipping → Payment → Review
   - Order summary sidebar
   - Payment method selection

## 📁 Project Structure

```
app/src/main/java/com/example/jetpackcomposestepper/
├── stepper/
│   ├── StepperModels.kt          # Data models and enums
│   ├── HorizontalStepper.kt      # Horizontal stepper component
│   ├── VerticalStepper.kt        # Vertical stepper component
│   └── StepperViewModel.kt       # State management
├── screens/
│   ├── HomeScreen.kt             # Main navigation screen
│   ├── HorizontalStepperScreens.kt
│   ├── VerticalStepperScreens.kt
│   ├── FormExampleScreen.kt      # Registration form example
│   └── CheckoutExampleScreen.kt  # Checkout process example
├── navigation/
│   └── Navigation.kt             # App navigation setup
├── ui/theme/
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
└── MainActivity.kt

```

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or newer
- JDK 8 or higher
- Android SDK with minimum API level 24

### Building the Project

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

### Running the App

```bash
./gradlew assembleDebug
```

Or click the "Run" button in Android Studio.

## 🎨 Usage Examples

### Basic Horizontal Stepper

```kotlin
val stepperState = rememberStepperState(
    initialSteps = listOf(
        Step(title = "Step 1", state = StepState.ACTIVE),
        Step(title = "Step 2"),
        Step(title = "Step 3")
    ),
    mode = StepperMode.LINEAR
)

HorizontalStepper(
    steps = stepperState.steps,
    currentStep = stepperState.currentStep,
    mode = stepperState.mode,
    onStepClick = { index -> stepperState.goToStep(index) }
)
```

### Vertical Stepper with Content

```kotlin
VerticalStepper(
    steps = stepperState.steps,
    currentStep = stepperState.currentStep,
    mode = StepperMode.LINEAR,
    onStepClick = { index -> stepperState.goToStep(index) },
    stepContent = { index ->
        // Custom content for each step
        Text("Content for step $index")
    }
)
```

### Custom Styling

```kotlin
HorizontalStepper(
    steps = steps,
    currentStep = currentStep,
    style = StepperStyle(
        showStepNumber = true,
        showConnector = true,
        showStepSubtitle = true,
        animateTransition = true,
        compactMode = false
    )
)
```

## 🔧 API Reference

### StepperState

```kotlin
class StepperState {
    val steps: List<Step>
    var currentStep: Int
    var mode: StepperMode

    fun nextStep(): Boolean
    fun previousStep(): Boolean
    fun goToStep(index: Int): Boolean
    fun setStepState(index: Int, state: StepState)
    fun updateStep(index: Int, step: Step)
    fun isLastStep(): Boolean
    fun isFirstStep(): Boolean
    fun getProgress(): Float
}
```

### Step Data Model

```kotlin
data class Step(
    val title: String,
    val subtitle: String? = null,
    val isOptional: Boolean = false,
    val state: StepState = StepState.INACTIVE,
    val errorMessage: String? = null
)
```

### StepState Enum

```kotlin
enum class StepState {
    COMPLETED,  // Step is finished
    ACTIVE,     // Current step
    INACTIVE,   // Not yet reached
    ERROR       // Validation failed
}
```

### StepperMode Enum

```kotlin
enum class StepperMode {
    LINEAR,      // Sequential navigation
    NON_LINEAR   // Free navigation
}
```

## 📱 Screenshots

The app includes:

- Home screen with all examples
- Multiple stepper layouts (horizontal & vertical)
- Real-world form and checkout examples
- Error handling demonstrations
- Progress tracking

## 🛠️ Technologies Used

- **Kotlin**: 1.9.20
- **Jetpack Compose**: Latest BOM (2023.10.01)
- **Material Design 3**: For modern UI components
- **Navigation Compose**: For screen navigation
- **ViewModel**: For state management
- **Compose Animation**: For smooth transitions

## 📄 License

This project is created for educational purposes to demonstrate Jetpack Compose stepper implementations.

## 🤝 Contributing

Feel free to fork this project and submit pull requests for improvements!

## 📧 Contact

For questions or feedback about this project, please open an issue on GitHub.

---

**Note**: This is a demonstration project showcasing various stepper features in Jetpack Compose. It's designed to be a comprehensive reference for implementing steppers in Android applications.
