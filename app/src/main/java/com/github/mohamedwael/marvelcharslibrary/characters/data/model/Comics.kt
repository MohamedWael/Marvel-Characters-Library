package com.github.mohamedwael.marvelcharslibrary.characters.data.model

import com.google.gson.annotations.SerializedName

data class Comics(

	@field:SerializedName("collectionURI")
	val collectionURI: String? = null,

	@field:SerializedName("available")
	val available: String? = null,

	@field:SerializedName("returned")
	val returned: String? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)