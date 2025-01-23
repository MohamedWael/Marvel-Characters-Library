package com.github.mohamedwael.marvelcharslibrary.characters.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacter(

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("urls")
	val urls: List<UrlsItem>? = null,

	@field:SerializedName("stories")
	val stories: Events? = null,

	@field:SerializedName("series")
	val series: Events? = null,

	@field:SerializedName("comics")
	val comics: Events? = null,

	@field:SerializedName("events")
	val events: Events? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("resourceURI")
	val resourceURI: String? = null,

)
