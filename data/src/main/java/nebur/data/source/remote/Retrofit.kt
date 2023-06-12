package nebur.data.source.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitApi {
    val categoriesApi: CategoriesRemote.CategoriesApi by lazy {
        runMocky.create(CategoriesRemote.CategoriesApi::class.java)
    }

    val dishesApi: DishesRemote.DishesApi by lazy {
        runMocky.create(DishesRemote.DishesApi::class.java)
    }

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val runMocky: retrofit2.Retrofit by lazy {
        retrofit2.Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(
                OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .baseUrl("https://run.mocky.io/")
            .build()
    }
}