package nebur.main

import kotlinx.coroutines.flow.Flow

interface LocalityFromApp {
    suspend fun get(): Flow<String?>
}