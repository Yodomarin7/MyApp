package nebur.data.repository

import kotlinx.coroutines.flow.Flow
import nebur.data.source.store.AllUserSaveStore

class AllUserSaveREPO(
    private val allUserSaveStore: AllUserSaveStore
) {
    suspend fun saveLocality(locality: String) {
        allUserSaveStore.save(locality)
    }

    fun getLocality(): Flow<String?> {
        return allUserSaveStore.get()
    }
}