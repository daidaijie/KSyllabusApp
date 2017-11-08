package com.daijie.ksyllabusapp.data

import android.os.Parcel
import android.os.Parcelable
import com.daijie.ksyllabusapp.utils.AssetsUtils
import com.google.gson.annotations.SerializedName
import com.orhanobut.logger.Logger
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * Created by daidaijie on 2017/10/18.
 */
data class OaInfo(
        @SerializedName("DOCCONTENT") val docContent: String,
        @SerializedName("ID") val id: Long,
        @SerializedName("DOCSUBJECT") val docSubject: String,
        @SerializedName("ACCESSORYCOUNT") val accessoryCount: Int,
        @SerializedName("DOCVALIDDATE") val docValidDate: String,
        @SerializedName("DOCVALIDTIME") val docValidTime: String,
        @SerializedName("OWNERID") val ownerId: Int,
        @SerializedName("LOGINID") val loginId: String,
        @SerializedName("LASTNAME") val lastName: String,
        @SerializedName("SUBCOMPANY_ID") val subCompanyId: Int,
        @SerializedName("SUBCOMPANYNAME") val subCompanyName: String,
        @SerializedName("DEPARTMENTNAME") val departmentName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(docContent)
        parcel.writeLong(id)
        parcel.writeString(docSubject)
        parcel.writeInt(accessoryCount)
        parcel.writeString(docValidDate)
        parcel.writeString(docValidTime)
        parcel.writeInt(ownerId)
        parcel.writeString(loginId)
        parcel.writeString(lastName)
        parcel.writeInt(subCompanyId)
        parcel.writeString(subCompanyName)
        parcel.writeString(departmentName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OaInfo> {
        override fun createFromParcel(parcel: Parcel): OaInfo {
            return OaInfo(parcel)
        }

        override fun newArray(size: Int): Array<OaInfo?> {
            return arrayOfNulls(size)
        }
    }
}

val OaInfo.isError
    get() = docValidDate == null && docValidTime == null


val OaInfo.content: String
    get() {
        // 去掉前置label
        val label = "!@#$%^&*"
        val labelIndex = docContent.indexOf(label) + label.length
        val oaContent = docContent.substring(labelIndex)

        val rootDoc = Jsoup.parse(oaContent)

        val elements = rootDoc.allElements
        elements.asSequence()
                .filter {
                    it != null
                }
                .forEach { element: Element ->
                    // 修改字体样式
                    val isHeading = element.nodeName().matches(Regex("^h\\d+$"))
                    val styles = element.attr("style").split(";")
                    val sb = StringBuffer()
                    styles.forEach { style: String ->
                        when {
                            style.contains("text-align") ||
                                    style.contains("margin") && !style.contains("margin-") -> {
                                sb.append(style)
                                sb.append(";")
                            }
                            style.contains("text-indent") -> sb.append("text-indent: 2pm;")
                        }
                    }
                    if (isHeading) {
                        sb.append("font-size:18px;")
                    }
                    element.attr("style", sb.toString())

                    // 去掉图片
                    if (element.nodeName() == "img") {
                        element.remove()
                    }
                }

        // 适配表格
        val tables = rootDoc.getElementsByTag("table")
        tables.asSequence()
                .filter {
                    it != null
                }
                .forEach { table ->
                    table.attr("width", "100%")
                    table.attr("style", "width: 100%;")
                    val trs = table.select("tr")
                    trs.asSequence()
                            .filter {
                                it != null
                            }
                            .forEach { tr ->
                                val tds = tr.select("td")
                                val witdh = 100.0 / tds.size
                                tds.asSequence()
                                        .filter {
                                            it != null
                                        }
                                        .forEach { td ->
                                            val colspan = td.attr("colspan")
                                            if (colspan.isBlank()) {
                                                td.attr("style", "width:$witdh%")
                                            } else {
                                                td.attr("style", "width:" + witdh * Integer.parseInt(colspan) + "%")
                                            }
                                            td.removeAttr("nowrap")
                                        }
                            }
                }


        val linkColor = "#7986cb"
        val links = rootDoc.getElementsByTag("a")
        for (link in links) {
            link.attr("style", "color:" + linkColor)
        }


        val resultDoc = Jsoup.parse(AssetsUtils.getStringFromPath("index.html"))
        val div = resultDoc.select("div#div_doc").first()
        div.append(rootDoc.toString())

        val bodyStyle = StringBuffer()
        bodyStyle.append("color:")
        bodyStyle.append("#212121")
        bodyStyle.append(";")
        bodyStyle.append("background:")
        bodyStyle.append("#FAFAFA")
        bodyStyle.append(";")
        bodyStyle.append("letter-spacing:1px")
        bodyStyle.append(";")
        bodyStyle.append("line-height:150%")
        val body = resultDoc.getElementsByTag("body")[0]
        body.attr("style", bodyStyle.toString())

        return rootDoc.toString()
    }