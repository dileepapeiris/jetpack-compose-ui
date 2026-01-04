package com.example.jetpackcomposestepper.stepper

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

/**
 * ViewModel to manage stepper state
 */
class StepperViewModel : ViewModel() {
    private val _steps = mutableStateListOf<Step>()
    val steps: List<Step> get() = _steps
    
    var currentStep by mutableStateOf(0)
        private set
    
    var mode by mutableStateOf(StepperMode.LINEAR)
    
    fun initializeSteps(stepList: List<Step>) {
        _steps.clear()
        _steps.addAll(stepList)
        currentStep = 0
    }
    
    fun updateStep(index: Int, step: Step) {
        if (index in _steps.indices) {
            _steps[index] = step
        }
    }
    
    fun setStepState(index: Int, state: StepState) {
        if (index in _steps.indices) {
            _steps[index] = _steps[index].copy(state = state)
        }
    }
    
    fun setStepError(index: Int, errorMessage: String) {
        if (index in _steps.indices) {
            _steps[index] = _steps[index].copy(
                state = StepState.ERROR,
                errorMessage = errorMessage
            )
        }
    }
    
    fun clearStepError(index: Int) {
        if (index in _steps.indices) {
            _steps[index] = _steps[index].copy(
                state = StepState.INACTIVE,
                errorMessage = null
            )
        }
    }
    
    fun nextStep(): Boolean {
        if (currentStep < _steps.lastIndex) {
            // Mark current step as completed
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.COMPLETED)
            currentStep++
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.ACTIVE)
            return true
        }
        return false
    }
    
    fun previousStep(): Boolean {
        if (currentStep > 0) {
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.INACTIVE)
            currentStep--
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.ACTIVE)
            return true
        }
        return false
    }
    
    fun goToStep(index: Int): Boolean {
        if (index in _steps.indices) {
            // In linear mode, can only go to completed or current step
            if (mode == StepperMode.LINEAR) {
                val canNavigate = index <= currentStep || 
                                 _steps.take(index).all { it.state == StepState.COMPLETED }
                if (!canNavigate) return false
            }
            
            _steps[currentStep] = _steps[currentStep].copy(
                state = if (_steps[currentStep].state == StepState.ACTIVE) 
                    StepState.INACTIVE 
                else 
                    _steps[currentStep].state
            )
            currentStep = index
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.ACTIVE)
            return true
        }
        return false
    }
    
    fun completeCurrentStep() {
        _steps[currentStep] = _steps[currentStep].copy(state = StepState.COMPLETED)
    }
    
    fun resetStepper() {
        _steps.forEachIndexed { index, step ->
            _steps[index] = step.copy(
                state = if (index == 0) StepState.ACTIVE else StepState.INACTIVE,
                errorMessage = null
            )
        }
        currentStep = 0
    }
    
    fun isLastStep(): Boolean = currentStep == _steps.lastIndex
    
    fun isFirstStep(): Boolean = currentStep == 0
    
    fun canProceed(): Boolean {
        return _steps[currentStep].state != StepState.ERROR
    }
    
    fun getProgress(): Float {
        val completedSteps = _steps.count { it.state == StepState.COMPLETED }
        return if (_steps.isNotEmpty()) completedSteps.toFloat() / _steps.size else 0f
    }
}

/**
 * Composable function to remember stepper state
 */
@Composable
fun rememberStepperState(
    initialSteps: List<Step>,
    initialStep: Int = 0,
    mode: StepperMode = StepperMode.LINEAR
): StepperState {
    return remember {
        StepperState(
            steps = initialSteps.toMutableList(),
            currentStep = initialStep,
            mode = mode
        )
    }
}

/**
 * State holder for stepper
 */
class StepperState(
    steps: List<Step>,
    currentStep: Int = 0,
    var mode: StepperMode = StepperMode.LINEAR
) {
    private val _steps = mutableStateListOf<Step>().apply { addAll(steps) }
    val steps: List<Step> get() = _steps
    
    var currentStep by mutableStateOf(currentStep)
        private set
    
    fun updateStep(index: Int, step: Step) {
        if (index in _steps.indices) {
            _steps[index] = step
        }
    }
    
    fun setStepState(index: Int, state: StepState) {
        if (index in _steps.indices) {
            _steps[index] = _steps[index].copy(state = state)
        }
    }
    
    fun nextStep(): Boolean {
        if (currentStep < _steps.lastIndex) {
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.COMPLETED)
            currentStep++
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.ACTIVE)
            return true
        }
        return false
    }
    
    fun previousStep(): Boolean {
        if (currentStep > 0) {
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.INACTIVE)
            currentStep--
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.ACTIVE)
            return true
        }
        return false
    }
    
    fun goToStep(index: Int): Boolean {
        if (index in _steps.indices) {
            if (mode == StepperMode.LINEAR) {
                val canNavigate = index <= currentStep || 
                                 _steps.take(index).all { it.state == StepState.COMPLETED }
                if (!canNavigate) return false
            }
            
            _steps[currentStep] = _steps[currentStep].copy(
                state = if (_steps[currentStep].state == StepState.ACTIVE) 
                    StepState.INACTIVE 
                else 
                    _steps[currentStep].state
            )
            currentStep = index
            _steps[currentStep] = _steps[currentStep].copy(state = StepState.ACTIVE)
            return true
        }
        return false
    }
    
    fun isLastStep(): Boolean = currentStep == _steps.lastIndex
    fun isFirstStep(): Boolean = currentStep == 0
    fun getProgress(): Float {
        val completedSteps = _steps.count { it.state == StepState.COMPLETED }
        return if (_steps.isNotEmpty()) completedSteps.toFloat() / _steps.size else 0f
    }
}
