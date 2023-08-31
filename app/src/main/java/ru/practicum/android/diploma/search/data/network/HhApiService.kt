package ru.practicum.android.diploma.search.data.network

import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.network.test.TracksSearchResponse


interface HhApiService {
    /* @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String) : TracksSearchResponse */
   /*  @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun search(@Query("term") text: String) : TracksSearchResponse
     */
    
    @FormUrlEncoded
    @POST("/oauth/token")
    fun getToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
    ): Call<TokenResponse>
}

@Serializable
data class TokenResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)
