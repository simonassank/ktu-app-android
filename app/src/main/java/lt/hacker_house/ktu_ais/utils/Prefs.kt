package lt.hacker_house.ktu_ais.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import lt.hacker_house.ktu_ais.App
import lt.hacker_house.ktu_ais.db.User
import lt.hacker_house.ktu_ais.models.SemesterInfoModel
import lt.hacker_house.ktu_ais.models.UserModel

/**
 * Created by simonas on 5/20/17.
 */

object Prefs {
    val SELECTED_SEMESTER = "selected_semester"
    val UPDATE_INTERVAL = "update_interval"
    val SHOW_NOTIFICATION = "show_notification"

    val UPDATE_INTERVAL_DEFAULT = "30"
    val UPDATE_INTERVAL_ENTRIES_VALUES = arrayOf("15", "30", "60", "120", "240", "480")
    val UPDATE_INTERVAL_ENTRIES_NAMES = arrayOf("15 min", "30 min", "1 val", "2 val", "4 val", "8 val")

    val SHOW_NOTIFICATION_DEFAULT = true

    val LOGOUT = "logout"
    val ABOUT = "about"

    val sp: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(App.context)

    fun getCurrentSemester(userModel: UserModel = User.getSync()!!): SemesterInfoModel {
        val defaultSemesterStr = userModel.defaultSemester.toDataString()
        val dataString = sp.getString(SELECTED_SEMESTER, defaultSemesterStr)
        return SemesterInfoModel.fromString(dataString)
    }

    fun getRefreshInterval()
            = sp.getString(UPDATE_INTERVAL, UPDATE_INTERVAL_DEFAULT).toInt()

    fun areNotificationEnabled(): Boolean
            = sp.getBoolean(SHOW_NOTIFICATION, SHOW_NOTIFICATION_DEFAULT)

    fun clear() {
        sp.edit().clear().commit()
    }
}