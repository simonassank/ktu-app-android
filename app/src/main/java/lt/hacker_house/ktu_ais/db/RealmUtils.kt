package lt.hacker_house.ktu_ais.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by simonas on 4/30/17.
 */

object RealmUtils {
    fun init(context: Context) {
        Realm.init(context)

        val config = RealmConfiguration.Builder()
                .name("db.realm")
                .deleteRealmIfMigrationNeeded() // I ain't paid enough for this shit...
                .build()

        Realm.setDefaultConfiguration(config)
    }
}