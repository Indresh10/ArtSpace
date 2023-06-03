package com.example.artspace

import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader

class Util {
    companion object{
        private fun getListFromCSV(file: InputStream): JSONArray {
            val jsonList = JSONArray()

            try {
                val inputStreamReader = InputStreamReader(file)
                val lines = inputStreamReader.readLines()
                val keys = lines[0].split(",")
                for (i in 1 until lines.size) {
                    val data = lines[i].split(",")
                    val obj = JSONObject()
                    var j = 0
                    keys.forEach { ele ->
                        obj.put(ele, data[j])
                        j += 1
                    }
                    jsonList.put(obj)
                }
            } catch (ignored: Exception) {
            }
            return jsonList
        }
        fun getData(file: InputStream): ArrayList<ArtData> {
            val data = ArrayList<ArtData>()
            val jsonArray = getListFromCSV(file)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val artData = ArtData(
                    jsonObject.getString("imageId").toInt(),
                    jsonObject.getString("title"),
                    jsonObject.getString("maker"),
                    jsonObject.getString("year").toInt()
                )
                data.add(artData)
            }
            return data
        }
    }
}