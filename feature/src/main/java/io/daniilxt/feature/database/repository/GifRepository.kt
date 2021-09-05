package io.daniilxt.feature.database.repository

import io.daniilxt.feature.database.dao.GifDao
import io.daniilxt.feature.database.models.GifDataBaseModel
import io.daniilxt.feature.domain.model.GifTopic

class GifRepository(private val gifDao: GifDao) : GifDao {

    override suspend fun addGifList(gifList: List<GifDataBaseModel>) {
        return gifDao.addGifList(gifList)
    }

    override suspend fun getGifListFromPage(page: Int, gifTopic: GifTopic): List<GifDataBaseModel> {
        return gifDao.getGifListFromPage(page, gifTopic)
    }
}