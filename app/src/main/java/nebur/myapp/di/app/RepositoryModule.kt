package nebur.myapp.di.app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nebur.data.repository.AllUserSaveREPO
import nebur.data.repository.BagREPO
import nebur.data.repository.CategoriesREPO
import nebur.data.repository.DishesREPO
import nebur.data.source.local.bag.IBagLocal
import nebur.data.source.remote.CategoriesRemote
import nebur.data.source.remote.DishesRemote
import nebur.data.source.store.AllUserSaveStore

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBagREPO(iBagLocal: IBagLocal): BagREPO {
        return BagREPO(iBagLocal)
    }

    @Provides
    fun provideAllUserSaveREPO(allUserSaveStore: AllUserSaveStore): AllUserSaveREPO {
        return AllUserSaveREPO(allUserSaveStore)
    }

    @Provides
    fun provideCategoriesREPO(categoriesRemote: CategoriesRemote): CategoriesREPO {
        return CategoriesREPO(categoriesRemote)
    }

    @Provides
    fun provideDishesREPO(dishesRemote: DishesRemote): DishesREPO {
        return DishesREPO(dishesRemote)
    }

}













