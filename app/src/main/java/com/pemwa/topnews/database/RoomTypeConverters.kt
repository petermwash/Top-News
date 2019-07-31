package com.pemwa.topnews.database

import androidx.room.TypeConverter
import com.pemwa.topnews.domain.Source

class RoomTypeConverters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun sourceToString(source: Source?): String? {
            return source?.name
        }

        @TypeConverter
        @JvmStatic
        fun stringToSource(name: String?): Source? {
            return name?.let {
                Source("", name)
            }
        }
    }
}
