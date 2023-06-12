package nebur.myapp.impl.forMain

import kotlinx.coroutines.flow.Flow
import nebur.data.repository.AllUserSaveREPO
import nebur.main.LocalityFromApp
import javax.inject.Inject

class LocalityForMain @Inject constructor(
    private val allUserSaveREPO: AllUserSaveREPO
): LocalityFromApp {

    override suspend fun get(): Flow<String?> {
        return allUserSaveREPO.getLocality()
    }

}