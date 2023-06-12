package nebur.myapp.di.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nebur.data.source.remote.CategoriesRemote
import nebur.data.source.remote.DishesRemote

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideCategoriesRemote(): CategoriesRemote {
        return CategoriesRemote()
    }

    @Provides
    fun provideDishesRemote(): DishesRemote {
        return DishesRemote()
    }

}