package ru.ituli.eventposter

import ru.ituli.eventposter.spectacle.Spectacle

sealed class ChoiceScreenState {

    class Error(val message: String) : ChoiceScreenState()

    object Loading : ChoiceScreenState()

    class Data(val data: List<Spectacle>) : ChoiceScreenState()

    object Init : ChoiceScreenState()

}