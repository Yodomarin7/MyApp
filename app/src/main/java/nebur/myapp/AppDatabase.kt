package nebur.myapp

import androidx.room.Database
import androidx.room.RoomDatabase
import nebur.data.source.local.bag.BagEntity
import nebur.data.source.local.bag.IBagLocal

@Database(
    version = 1,
    entities = [
        BagEntity::class,
    ],
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun iBagLocal(): IBagLocal
}