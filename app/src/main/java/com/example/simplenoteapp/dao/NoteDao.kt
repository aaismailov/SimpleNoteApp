package com.example.simplenoteapp.dao

import androidx.room.*
import com.example.simplenoteapp.entities.Notes

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    @Delete
    suspend fun delete(note: Notes)

    @Update
    suspend fun update(note: Notes)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes(): List<Notes>

    @Query("SELECT * FROM notes WHERE id =:id")
    suspend fun getNote(id: Int): Notes

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteNote(id: Int)
}