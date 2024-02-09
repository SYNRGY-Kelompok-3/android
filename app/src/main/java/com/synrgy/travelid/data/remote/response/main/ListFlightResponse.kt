package com.synrgy.travelid.data.remote.response.main

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.main.ListFlight

data class ListFlightResponse (

    @SerializedName("data" ) var data : Data? = Data()

) {
    data class Data (

        @SerializedName("content"          ) var content          : ArrayList<Content> = arrayListOf(),
        @SerializedName("pageable"         ) var pageable         : Pageable?          = Pageable(),
        @SerializedName("totalPages"       ) var totalPages       : Int?               = null,
        @SerializedName("totalElements"    ) var totalElements    : Int?               = null,
        @SerializedName("last"             ) var last             : Boolean?           = null,
        @SerializedName("size"             ) var size             : Int?               = null,
        @SerializedName("number"           ) var number           : Int?               = null,
        @SerializedName("sort"             ) var sort             : Sort?              = Sort(),
        @SerializedName("numberOfElements" ) var numberOfElements : Int?               = null,
        @SerializedName("first"            ) var first            : Boolean?           = null,
        @SerializedName("empty"            ) var empty            : Boolean?           = null

    )

    data class Content (

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

    data class Pageable (

        @SerializedName("sort"       ) var sort       : Sort?    = Sort(),
        @SerializedName("offset"     ) var offset     : Int?     = null,
        @SerializedName("pageNumber" ) var pageNumber : Int?     = null,
        @SerializedName("pageSize"   ) var pageSize   : Int?     = null,
        @SerializedName("paged"      ) var paged      : Boolean? = null,
        @SerializedName("unpaged"    ) var unpaged    : Boolean? = null

    )

    data class Sort (

        @SerializedName("empty"    ) var empty    : Boolean? = null,
        @SerializedName("sorted"   ) var sorted   : Boolean? = null,
        @SerializedName("unsorted" ) var unsorted : Boolean? = null

    )

    data class Airlines (

        @SerializedName("id"       ) var id       : Int?    = null,
        @SerializedName("airline"  ) var airline  : String? = null,
        @SerializedName("pathLogo" ) var pathLogo : String? = null

    )
}

fun ListFlightResponse.Content.toListFlight(): ListFlight{
    return ListFlight(
        id = id ?: 0,
        airline = airlines?.airline.orEmpty(),
        pathLogo = airlines?.pathLogo.orEmpty(),
        discountPrice = discountPrice ?: 0,
        transit = transit.orEmpty(),
        flightTime = flightTime.orEmpty(),
        arrivedTime = arrivedTime.orEmpty(),
        originAirport = originAirport.orEmpty(),
        destinationAirport = destinationAirport.orEmpty(),
        duration = duration.orEmpty(),
        luggage = luggage.orEmpty(),
        freeMeal = freeMeal.orEmpty()
    )
}

//fun ListFlightResponse.Data.toListFlight(): ListFlight{
//    return ListFlight(
//        id = content.first().id ?: 0,
//        airline = content.first().airlines?.airline.orEmpty(),
//        pathLogo = content.first().airlines?.pathLogo.orEmpty(),
//        discountPrice = content.first().discountPrice ?: 0,
//        transit = content.first().transit.orEmpty(),
//        flightTime = content.first().flightTime.orEmpty(),
//        arrivedTime = content.first().arrivedTime.orEmpty(),
//        originAirport = content.first().originAirport.orEmpty(),
//        destinationAirport = content.first().destinationAirport.orEmpty(),
//        duration = content.first().duration.orEmpty(),
//        luggage = content.first().luggage.orEmpty(),
//        freeMeal = content.first().freeMeal.orEmpty()
//    )
//}