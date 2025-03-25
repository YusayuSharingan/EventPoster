package ru.ituli.eventposter.ktx

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ituli.eventposter.ChoiceScreenState
import ru.ituli.eventposter.use_cases.LoadDataUseCase

class CoroutinesViewModel: ViewModel() {
    private val tag = "ViewModel"


    private val _loadState: MutableStateFlow<ChoiceScreenState> =
        MutableStateFlow(ChoiceScreenState.Init)
    val loadState: StateFlow<ChoiceScreenState> get() = _loadState.asStateFlow()


    init {
        viewModelScope.launch {
            _loadState.emit(ChoiceScreenState.Loading)
            _loadState.emit(goToApi())
        }
    }

    fun loadSpectacles() = viewModelScope.launch { _loadState.emit(goToApi()) }


    private suspend fun goToApi(): ChoiceScreenState {
        Log.d(tag, "Go to Api!")
        var errorMessage = "Refresh app"

        val result = runCatchingNonCancellation(LoadDataUseCase::invoke)
            .onFailure {
                Log.e(tag, "Data extraction failed!", it)
                errorMessage = it.toString()
            }
            .getOrNull()

        return result?.let{ ChoiceScreenState.Data(it) }
            ?: ChoiceScreenState.Error(errorMessage)
    }



//    fun callError() = viewModelScope.launch { _loadState.emit(ChoiceScreenState.Error) }

}