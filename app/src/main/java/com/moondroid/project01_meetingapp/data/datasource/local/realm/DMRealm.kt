package com.moondroid.project01_meetingapp.data.datasource.local.realm

import com.moondroid.project01_meetingapp.domain.model.DMUser
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class DMRealm private constructor() {
    companion object {
        private const val DEFAULT_DB_FILE_NAME = "damoim.realm"

        @Volatile
        private var instance: Realm? = null

        private var config: RealmConfiguration = RealmConfiguration.Builder(schema = setOf(DMUser::class))
            .name(DEFAULT_DB_FILE_NAME)
            .build()

        fun init() {
            instance = Realm.open(config)
        }

        @JvmStatic
        fun getInstance(): Realm {
            instance ?: synchronized(this) {
                instance ?: Realm.open(config).also {
                    instance = it
                }
            }
            return instance!!
        }
    }
}