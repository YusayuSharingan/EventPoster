package ru.ituli.eventposter.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.ituli.eventposter.spectacle.Spectacle
import kotlin.random.Random



object GenerateDataUseCase {
    suspend operator fun invoke(): List<Spectacle> = withContext(Dispatchers.IO) {
        fun generateSpectacle (header: String, place: String, description: String) =
            Spectacle(
                "now", "free",
                "picture", header,
                place, description,
                "https://kudago.com/msk"
            )

        delay(Random.Default.nextInt(2500).toLong())

        listOf(
            generateSpectacle("Circus", "1517","no comments"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis"),
            generateSpectacle("Ibis", "Ibis Hotel","Hotel for ibis")

        )
    }
}