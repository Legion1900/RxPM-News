package com.legion1900.mvvmnews.suite

import com.legion1900.mvvmnews.dao.ArticleDaoTest
import com.legion1900.mvvmnews.dao.CacheDataDaoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CacheDataDaoTest::class,
    ArticleDaoTest::class
)
class DaoSuite