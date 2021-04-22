package com.qbo.apppatitas2qbo.repository

import androidx.lifecycle.LiveData
import com.qbo.apppatitas2qbo.db.dao.PersonaDao
import com.qbo.apppatitas2qbo.db.entity.PersonaEntity

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PersonaRepository(private val personaDao: PersonaDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    suspend fun insertar(personaEntity: PersonaEntity){
        personaDao.insertar(personaEntity)
    }
    suspend fun actualizar(personaEntity: PersonaEntity){
        personaDao.actualizar(personaEntity)
    }

    suspend fun eliminartodo(){
        personaDao.eliminartodo()
    }
    fun obtener():
            LiveData<PersonaEntity> {
        return personaDao.obtener()
    }
}