package com.synrgy.travelid.data.remote.response.main

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.main.FlightById

data class FlightByIdResponse (

    @SerializedName("data"    ) var data    : Data?   = Data(),
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

) {

    data class Data (

        @SerializedName("created_date"       ) var createdDate        : String?   = null,
        @SerializedName("updated_date"       ) var updatedDate        : String?   = null,
        @SerializedName("id"                 ) var id                 : Int?      = null,
        @SerializedName("airlines"           ) var airlines           : Airlines? = Airlines(),
        @SerializedName("passengerClass"     ) var passengerClass     : String?   = null,
        @SerializedName("originAirport"      ) var originAirport      : String?   = null,
        @SerializedName("destinationAirport" ) var destinationAirport : String?   = null,
        @SerializedName("flightNumber"       ) var flightNumber       : String?   = null,
        @SerializedName("originCity"         ) var originCity         : String?   = null,
        @SerializedName("destinationCity"    ) var destinationCity    : String?   = null,
        @SerializedName("gate"               ) var gate               : String?   = null,
        @SerializedName("flightTime"         ) var flightTime         : String?   = null,
        @SerializedName("arrivedTime"        ) var arrivedTime        : String?   = null,
        @SerializedName("duration"           ) var duration           : String?   = null,
        @SerializedName("transit"            ) var transit            : String?   = null,
        @SerializedName("luggage"            ) var luggage            : String?   = null,
        @SerializedName("freeMeal"           ) var freeMeal           : String?   = null,
        @SerializedName("price"              ) var price              : Int?      = null,
        @SerializedName("discountPrice"      ) var discountPrice      : Int?      = null,
        @SerializedName("isDiscount"         ) var isDiscount         : String?   = null

    )
    data class Airlines (

        @SerializedName("id"       ) var id       : Int?    = null,
        @SerializedName("airline"  ) var airline  : String? = null,
        @SerializedName("pathLogo" ) var pathLogo : String? = null

    )
}

fun FlightByIdResponse.Data.toFlightById(): FlightById{
    return FlightById(
        flightTime = flightTime.orEmpty(),
        originCity = originCity.orEmpty(),
        originAirport = originAirport.orEmpty(),
        airline = airlines?.airline.orEmpty(),
        pathLogo = airlines?.pathLogo.orEmpty(),
        flightNumber = flightNumber.orEmpty(),
        passengerClass = passengerClass.orEmpty(),
        duration = duration.orEmpty(),
        arrivedTime = arrivedTime.orEmpty(),
        destinationCity = destinationCity.orEmpty(),
        destinationAirport = destinationAirport.orEmpty(),
        luggage = luggage.orEmpty(),
        freeMeal = freeMeal.orEmpty(),
        discountPrice = discountPrice ?: 0
    )
}