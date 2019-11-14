package com.legion1900.mvvmnews.models.data

import android.os.Parcel
import android.os.Parcelable

data class Article(
    val author: String?,
    val title: String?,
    val publishedAt: String?,
    val sourceName: String?,
    val urlToImage: String?,
    val description: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(publishedAt)
        parcel.writeString(sourceName)
        parcel.writeString(urlToImage)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {

        /*
        * As article`s title, source name and text goes into string delimiter must be really unique.
        * */
        private const val DELIMITER = "~_~"

        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }

        fun toDbString(article: Article): String = article.run {
            "$author$DELIMITER$title$DELIMITER$publishedAt$DELIMITER$sourceName$DELIMITER$urlToImage$DELIMITER$description"
        }

        fun fromString(article: String): Article {
            // a ~ args
            val a = article.split(DELIMITER)
            return Article(a[0], a[1], a[2], a[3], a[4], a[5])
        }
    }
}