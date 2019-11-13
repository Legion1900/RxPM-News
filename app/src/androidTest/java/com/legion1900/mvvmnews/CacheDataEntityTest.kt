package com.legion1900.mvvmnews

import com.legion1900.mvvmnews.models.room.dao.CacheDataDao
import com.legion1900.mvvmnews.models.room.entity.CacheDataEntity
import org.junit.BeforeClass
import org.junit.Test
import java.util.*
import kotlin.random.Random

class CacheDataEntityTest {

    @Test
    fun update_test() {
        writeData()
    }

    @Test
    fun getDataFor_test() {
        writeData()
        val out = mutableListOf<CacheDataEntity>()
        for (topic in TOPICS) {
            out += dao.getDataFor(topic)
        }

        org.junit.Assert.assertEquals(
            "Written and loaded data must be the same",
            data.toSet(),
            out.toSet()
        )
    }

    private fun writeData() {
        for (cache in data)
            dao.update(cache)
    }

    private companion object Data {

        val TOPICS: Array<String> =
            DatabaseProvider.context.resources.getStringArray(R.array.topics)

        @JvmStatic
        lateinit var dao: CacheDataDao

        @JvmStatic
        val data = mutableListOf<CacheDataEntity>()

        @BeforeClass
        @JvmStatic
        fun onDbSetup() {
            dao = DatabaseProvider.provideNewsCacheDb().cacheDataDao()
        }

        @BeforeClass
        @JvmStatic
        fun onDataSetup() {
            for (topic in TOPICS) {
                val date = Date(Random.nextLong(10_000_000))
                data += CacheDataEntity(topic, date)
            }
        }
    }
}