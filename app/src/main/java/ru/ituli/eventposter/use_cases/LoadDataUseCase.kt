package ru.ituli.eventposter.use_cases

import ru.ituli.eventposter.spectacle.Spectacle
import ru.ituli.eventposter.webApi.KudaGoApiClient
import ru.ituli.eventposter.webApi.Event
import ru.ituli.eventposter.webApi.toSpectacle

object LoadDataUseCase {
    private val apiService = KudaGoApiClient().apiService

    suspend operator fun invoke(): List<Spectacle> =
        apiService.getEvents().results.map(Event::toSpectacle)
}