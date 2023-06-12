package nebur.data.source.remote

import retrofit2.Response
import retrofit2.http.GET

class CategoriesRemote {

    data class Model(
        val сategories: List<CategoriesModel>
    )

    data class CategoriesModel(
        val id: Int,
        val name: String,
        val image_url: String,
    )

    interface CategoriesApi {
        @GET("v3/058729bd-1402-4578-88de-265481fd7d54")
        suspend fun get() : Response<Model>
    }

    suspend fun get(): List<CategoriesModel>? {
        return try{
            val response = RetrofitApi.categoriesApi.get()
            response.body()?.сategories
        } catch (e: Throwable) {
            null
        }
    }

}














