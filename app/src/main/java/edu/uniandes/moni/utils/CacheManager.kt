package edu.uniandes.moni.utils

import android.util.LruCache
import com.example.monitores.*
import edu.uniandes.moni.model.dto.TutoringDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheManager @Inject constructor() {
    private var memoryCache: LruCache<String, TutoringDTO>? = null
    private var memoryCacheTopic: LruCache<Int, String>? = null
    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, TutoringDTO>(cacheSize) {
            override fun sizeOf(key: String?, value: TutoringDTO?): Int {
                return 203 / 1024
            }
        }
        memoryCacheTopic = object : LruCache<Int, String>(cacheSize) {
            override fun sizeOf(key: Int?, value: String?): Int {
            return 8 / 1024
        }}

    }

    fun putTutoring(tutoring: TutoringDTO) {
        memoryCache?.put(tutoring.id, tutoring)
    }

    fun getTutoringById(id: String): TutoringDTO? {
        return memoryCache?.get(id)
    }
    fun putTutoringTopic(tutoringtopic: String) {
        var llave=0
        if (tutoringtopic=="physics"){
            llave=1

        }else if(tutoringtopic=="calculus"){
            llave=2
        }else if(tutoringtopic=="dancing"){

            llave=3
        }else if(tutoringtopic=="fitness"){
            llave=4
        }
        memoryCacheTopic?.put(llave, tutoringtopic)


    }

    fun getTutoringTopicById(id: Int): String? {
        return memoryCacheTopic?.get(id)
    }
    fun isEmptyTopic(): Boolean {
        return memoryCacheTopic?.get(1)==null &&memoryCacheTopic?.get(2)==null &&memoryCacheTopic?.get(3)==null &&memoryCacheTopic?.get(4)==null
    }

}