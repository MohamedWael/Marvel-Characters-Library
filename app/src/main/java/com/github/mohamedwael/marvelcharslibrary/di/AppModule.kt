package com.github.mohamedwael.marvelcharslibrary.di


import com.github.mohamedwael.marvelcharslibrary.BuildConfig
import com.github.mohamedwael.marvelcharslibrary.characters.data.API_KEY
import com.github.mohamedwael.marvelcharslibrary.characters.data.APP_BASE_URL
import com.github.mohamedwael.marvelcharslibrary.characters.data.MarvelApi
import com.github.mohamedwael.marvelcharslibrary.characters.data.CharactersRepoImpl
import com.github.mohamedwael.marvelcharslibrary.characters.data.PRIVATE_KEY
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersRepo
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersUseCase
import com.github.mohamedwael.marvelcharslibrary.characters.domain.CharactersUseCaseImpl
import com.github.mohamedwael.marvelcharslibrary.util.MarvelApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
                .addInterceptor(MarvelApiKeyInterceptor(API_KEY, PRIVATE_KEY))
        }
        return builder.build()
    }

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(APP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideApi(retrofit: Retrofit): MarvelApi = retrofit.create(MarvelApi::class.java)

    @Provides
    fun provideRepo(api: MarvelApi): CharactersRepo = CharactersRepoImpl(api)

    @Provides
    fun provideUseCase(repo: CharactersRepo): CharactersUseCase = CharactersUseCaseImpl(repo)
}
