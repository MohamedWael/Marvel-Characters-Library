package com.github.mohamedwael.marvelcharslibrary.characters.data.model

import com.google.gson.annotations.SerializedName

data class MarvelResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: MarvelData? = null,

	@field:SerializedName("status")
	val status: String? = null
)