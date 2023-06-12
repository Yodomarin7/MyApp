package nebur.data.source.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val ALL_USER_SAVE_STORE = "allUserSaveStore"
val Context.allUserSaveStore: DataStore<Preferences> by preferencesDataStore(name = ALL_USER_SAVE_STORE)

class AllUserSaveStore(
    private val context: Context
) {
    companion object {
        val LOCALITY = stringPreferencesKey("locality")
    }

    suspend fun save(txt: String) {
        context.allUserSaveStore.edit { preferences->
            preferences[LOCALITY] = txt
        }
    }

    fun get(): Flow<String?> = context.allUserSaveStore.data.map { preferences  ->
        preferences[LOCALITY]
    }
}