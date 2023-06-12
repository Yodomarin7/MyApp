package nebur.myapp.di.app

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nebur.data.source.local.bag.IBagLocal
import nebur.myapp.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideIBagLocal(db: AppDatabase): IBagLocal {
        return db.iBagLocal()
    }

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "myapp_database"
        ).build()
    }
}