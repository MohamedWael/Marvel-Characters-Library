package com.github.mohamedwael.marvelcharslibrary.characters.data.model

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("total")
	val total: String? = null,

	@field:SerializedName("offset")
	val offset: String? = null,

	@field:SerializedName("limit")
	val limit: String? = null,

	@field:SerializedName("count")
	val count: String? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)