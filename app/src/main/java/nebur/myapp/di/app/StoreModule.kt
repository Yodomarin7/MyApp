package nebur.myapp.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nebur.data.source.store.AllUserSaveStore

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {

    @Provides
    fun provideAllUserSaveStore(@ApplicationContext appContext: Context): AllUserSaveStore {
        return AllUserSaveStore(appContext)
    }

}