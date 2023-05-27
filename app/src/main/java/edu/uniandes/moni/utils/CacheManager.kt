package edu.uniandes.moni.utils

import android.util.LruCache
import edu.uniandes.moni.model.dto.TutoringDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheManager @Inject constructor() {
    private var memoryCache: LruCache<String, TutoringDTO>? = null

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        memoryCache = object : LruCache<String, TutoringDTO>(cacheSize) {
            override fun sizeOf(key: String?, value: TutoringDTO?): Int {
                return 203 / 1024
            }
        }
    }

    fun putTutoring(tutoring: TutoringDTO) {
        memoryCache?.put(tutoring.id, tutoring)
    }

    fun getTutoringById(id: String): TutoringDTO? {
        return memoryCache?.get(id)
    }

}