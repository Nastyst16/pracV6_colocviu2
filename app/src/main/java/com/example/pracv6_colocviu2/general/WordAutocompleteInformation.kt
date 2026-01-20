package com.example.pracv6_colocviu2.general

data class WordAutocompleteInformation(
    val autocomplete_information: String
//    ,
//    val windSpeed: String,
//    val condition: String,
//    val pressure: String,
//    val humidity: String
) {
    // MODIFICARE: Folosim \n (New Line) in loc de virgula
    override fun toString(): String {
        return "Autocomplete: $autocomplete_information\n"
//        return "Temperature: $temperature\n" +
//                "Wind Speed: $windSpeed\n" +
//                "Condition: $condition\n" +
//                "Pressure: $pressure\n" +
//                "Humidity: $humidity"
    }
}