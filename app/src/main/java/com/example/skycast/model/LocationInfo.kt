package com.example.skycast.model

data class LocationInfo(
	val osm_id: Long? = null,
	val place_rank: Int? = null,
	val licence: String? = null,
	val boundingbox: List<String?>? = null,
	val address: Address? = null,
	val importance: Any? = null,
	val lon: String? = null,
	val type: String? = null,
	val display_name: String? = null,
	val osm_type: String? = null,
	val name: String? = null,
	val addresstype: String? = null,
	val jsonMemberClass: String? = null,
	val place_id: Int? = null,
	val lat: String? = null
)

data class Address(
	val country: String? = null,
	val country_code: String? = null,
	val road: String? = null,
	val city: String? = null,
	val neighbourhood: String? = null,
	val iSO31662Lvl4: String? = null,
	val postcode: String? = null,
	val house_number: String? = null,
	val state: String? = null
)

