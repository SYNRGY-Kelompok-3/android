package com.synrgy.travelid.data.remote.response.main

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.main.OrderHistory
import com.synrgy.travelid.domain.model.main.OrderHistoryById

data class OrderHistoryByIdResponse (

    @SerializedName("data"    ) var data    : Data?   = Data(),
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("status"  ) var status  : Int?    = null

) {

    data class Data (

        @SerializedName("created_date"       ) var createdDate        : String?                  = null,
        @SerializedName("updated_date"       ) var updatedDate        : String?                  = null,
        @SerializedName("id"                 ) var id                 : Int?                     = null,
        @SerializedName("customer"           ) var customer           : Customer?                = Customer(),
        @SerializedName("totalPrice"         ) var totalPrice         : Int?                     = null,
        @SerializedName("paid"               ) var paid               : String?                  = null,
        @SerializedName("bankPembayaran"     ) var bankPembayaran     : String?                  = null,
        @SerializedName("namaRekening"       ) var namaRekening       : String?                  = null,
        @SerializedName("nomorRekening"      ) var nomorRekening      : String?                  = null,
        @SerializedName("masaBerlaku"        ) var masaBerlaku        : String?                  = null,
        @SerializedName("cvvCvn"             ) var cvvCvn             : String?                  = null,
        @SerializedName("notificationSent"   ) var notificationSent   : Boolean?                 = null,
        @SerializedName("bookingDetail"      ) var bookingDetail      : ArrayList<BookingDetail> = arrayListOf(),
        @SerializedName("addOnSelectingSeat" ) var addOnSelectingSeat : Int?                     = null,
        @SerializedName("addOnLuggagePrice"  ) var addOnLuggagePrice  : String?                  = null,
        @SerializedName("addOnLuggage"       ) var addOnLuggage       : String?                  = null

    )

    data class BookingDetail (

        @SerializedName("created_date"   ) var createdDate    : String? = null,
        @SerializedName("updated_date"   ) var updatedDate    : String? = null,
        @SerializedName("id"             ) var id             : Int?    = null,
        @SerializedName("flight"         ) var flight         : Flight? = Flight(),
        @SerializedName("customerName"   ) var customerName   : String? = null,
        @SerializedName("identityNumber" ) var identityNumber : String? = null,
        @SerializedName("seatNumber"     ) var seatNumber     : String? = null,
        @SerializedName("luggage"        ) var luggage        : String? = null,
        @SerializedName("price"          ) var price          : Int?    = null,
        @SerializedName("category"       ) var category       : String? = null

    )

    data class Customer (

        @SerializedName("created_date"   ) var createdDate    : String? = null,
        @SerializedName("updated_date"   ) var updatedDate    : String? = null,
        @SerializedName("id"             ) var id             : Int?    = null,
        @SerializedName("name"           ) var name           : String? = null,
        @SerializedName("identityNumber" ) var identityNumber : String? = null,
        @SerializedName("email"          ) var email          : String? = null,
        @SerializedName("dateOfBirth"    ) var dateOfBirth    : String? = null,
        @SerializedName("gender"         ) var gender         : String? = null,
        @SerializedName("profilePicture" ) var profilePicture : String? = null,
        @SerializedName("phoneNumber"    ) var phoneNumber    : String? = null

    )

    data class Flight (

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

fun OrderHistoryByIdResponse.Data.toOrderHistoryById(): OrderHistoryById {
    return OrderHistoryById(
        totalPrice = totalPrice ?: 0,
        paid = paid.orEmpty(),
        flightNumber = bookingDetail.first().flight?.flightNumber.orEmpty(),
        originCity = bookingDetail.first().flight?.originCity.orEmpty(),
        destinationCity = bookingDetail.first().flight?.destinationCity.orEmpty(),
        flightTime = bookingDetail.first().flight?.flightTime.orEmpty(),
        arrivedTime = bookingDetail.first().flight?.arrivedTime.orEmpty(),
        originAirport = bookingDetail.first().flight?.originAirport.orEmpty(),
        destinationAirport = bookingDetail.first().flight?.destinationAirport.orEmpty(),
        name = customer?.name.orEmpty(),
        airline = bookingDetail.first().flight?.airlines?.airline.orEmpty(),
        pathLogo = bookingDetail.first().flight?.airlines?.pathLogo.orEmpty(),
        passengerClass = bookingDetail.first().flight?.passengerClass.orEmpty(),
        duration = bookingDetail.first().flight?.duration.orEmpty(),
        luggage = bookingDetail.first().flight?.luggage.orEmpty(),
        freeMeal = bookingDetail.first().flight?.freeMeal.orEmpty()
    )
}