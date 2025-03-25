package ru.ituli.eventposter.webApi

import kotlinx.serialization.Serializable
import ru.ituli.eventposter.spectacle.Spectacle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Serializable
data class EventResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Event>
)

@Serializable
data class Event(
    val id: Int,
    val dates: List<DateRange>?,
    val title: String,
    val place: Place?,
    val description: String?,
    val price: String?,
    val images: List<Image>? = null,
    val site_url: String?
    )


@Serializable
data class DateRange(
    val start: Long,
    val end: Long
)


@Serializable
data class Place(
    val id: Int,
    val title: String,
    val slug: String,
    val address: String,
    val phone: String,
    val is_stub: Boolean,
    val site_url: String?,
    val coords: Coordinates,
    val subway: String,
    val is_closed: Boolean,
    val location: String
)

@Serializable
data class Coordinates(
    val lat: Double,
    val lon: Double
)


@Serializable
data class Image(
    val image: String
)


fun Event.toSpectacle() = Spectacle(
        date = dates?.convertTime(Calendar.getInstance()),
        cost = if (price?.length in 1..20) price else null,
        image = images?.firstOrNull()?.image?: "",
        header = title,
        place = place?.address,
        description = if (description == null || description == "") "Нет описания" else description,
        source = if (site_url == null || site_url == "") "Нет информации об источнике" else site_url
)


fun List<DateRange>.convertTime(calendar: Calendar): String {
    val yearBefore = calendar.apply{add(Calendar.YEAR, -5)}.timeInMillis
    val tenYearsAgo = calendar.apply{add(Calendar.YEAR, 10)}.timeInMillis

    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val startDate = formatter.format(Date(first().start * 1000))
    val endDate = formatter.format(Date(last().end * 1000))

    return "$startDate - $endDate"
//    when {
//        first().start < yearBefore && last().end > tenYearsAgo -> "Всегда"
//        first().start >= yearBefore && last().end > tenYearsAgo -> "c $startDate"
//        first().start < yearBefore && last().end <= tenYearsAgo -> "до $endDate"
//        else -> "$startDate - $endDate"
//    }
}