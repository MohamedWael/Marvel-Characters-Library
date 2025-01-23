package com.github.mohamedwael.marvelcharslibrary.characters.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("extension")
    val extension: String? = null
) {
    fun getFullPath(): String {
        return "$path.$extension"
    }
}