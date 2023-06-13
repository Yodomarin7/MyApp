package nebur.data.source.remote

import android.util.Log
import retrofit2.Response
import retrofit2.http.GET

class DishesRemote {

    data class Model(
        val dishes: List<DishesModel>
    )

    data class DishesModel(
        val id: Int,
        val name: String = "",
        val price: Int = 0,
        val weight: Int = 0,
        val description: String = "",
        val image_url: String = "null",
        val tegs: List<String> = listOf(),
    )

    interface DishesApi {
        @GET("v3/c7a508f2-a904-498a-8539-09d96785446e")
        suspend fun get() : Response<Model>
    }

    suspend fun get(teg: String): List<DishesModel>? {
        return try{
            val model = RetrofitApi.dishesApi.get().body()?.dishes
            if(model != null) {
                return model.filter { it.tegs.contains(teg) }
            }
            else null
        } catch (e: Throwable) {
            Log.e("DishesRemote", "e: ${e.message}")
            null
        }
    }

}