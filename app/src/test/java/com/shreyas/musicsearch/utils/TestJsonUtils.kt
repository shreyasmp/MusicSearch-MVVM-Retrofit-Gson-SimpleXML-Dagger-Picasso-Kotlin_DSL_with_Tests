package com.shreyas.musicsearch.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import org.robolectric.Robolectric
import org.simpleframework.xml.core.Persister
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

/**
 *  Utility class to read assets folder in test and bring the recorded json object as json response string
 */
object TestJsonUtils {

    private val TAG = TestJsonUtils::class.java.simpleName

    fun <T> getObjectFromJsonFile(
        jsonFile: String,
        tClass: Class<T>?
    ): T? {
        var inputStream: InputStream? = null
        try {
            inputStream = this.javaClass.classLoader!!.getResourceAsStream(jsonFile)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            val json = String(buffer, StandardCharsets.UTF_8)
            return Gson().fromJson(json, tClass)
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (exception: IOException) {
                    Log.d(TAG, "IOException: ${exception.message}")
                }
            }
        }
        return null
    }

    fun <T> getObjectFromXmlFile(
        xmlFile: String,
        tClass: Class<T>?
    ): T? {
        var inputStream: InputStream? = null
        try {
            inputStream = this.javaClass.classLoader!!.getResourceAsStream(xmlFile)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            val xml = String(buffer, StandardCharsets.UTF_8)
            val serializer = Persister()
            return serializer.read(tClass, xml)
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (exception: IOException) {
                    Log.d(TAG, "IOException: ${exception.message}")
                }
            }
        }
        return null
    }

    fun getResponseAsString(fileName: String): String {
        val location = this.javaClass.classLoader!!.getResource(fileName)
        val file = File(location.path)
        return String(file.readBytes())
    }

    fun startFragment(fragment: Fragment) {
        val activity = Robolectric.buildActivity(FragmentActivity::class.java)
            .create()
            .start()
            .resume()
            .get()
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(fragment, null)
        fragmentTransaction.commit()
    }
}