package io.daniilxt.feature.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.daniilxt.feature.database.models.GifDataBaseModel
import io.daniilxt.feature.domain.model.GifTopic

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGifList(gifList: List<GifDataBaseModel>)

    @Query("SELECT * FROM GIFS_TABLE GT WHERE GT.page = :page and GT.gifTopic = :gifTopic")
    suspend fun getGifListFromPage(page: Int, gifTopic: GifTopic): List<GifDataBaseModel>
}