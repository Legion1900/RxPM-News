package com.legion1900.mvvmnews.dao

import com.legion1900.mvvmnews.models.room.dao.CacheDataDao
import com.legion1900.mvvmnews.models.room.entity.CacheDataEntity
import com.legion1900.mvvmnews.util.DataProvider.TOPICS
import com.legion1900.mvvmnews.util.DatabaseProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.util.*
import kotlin.random.Random

class CacheDataDaoTest {

    @Before
    fun onPopulateTable() {
        for (cache in data)
            dao.update(cache)
    }

    @After
    fun onClearTable() {
        dao.clear()
    }

    @Test
    fun getCacheFor_test() {
        val out = mutableListOf<CacheDataEntity>()
        for (topic in TOPICS) {
            out += dao.getCacheFor(topic)!!
        }

        assertEquals(
            "Written and loaded data must be the same",
            data.toSet(),
            out.toSet()
        )
    }

    @Test
    fun getDateFor_test() {
        val dates = mutableListOf<Date>()
        for (topic in TOPICS) {
            dates += dao.getDateFor(topic)!!
        }

        for (i in data.indices)
            assertEquals(
                "Written and loaded data must be the same",
                data[i].date,
                dates[i]
            )
    }

    private companion object Data {
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