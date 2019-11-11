package com.legion1900.mvvmnews.presenters

interface PresentationModel {
    fun updateNewsfeed(topic: String)
    /*
    * articleInd - index of article that was clicked in a List<Article>.
    * */
    fun onArticleClick(articleInd: Int)
}