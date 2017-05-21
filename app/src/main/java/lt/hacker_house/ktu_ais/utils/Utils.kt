package lt.hacker_house.ktu_ais.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import lt.hacker_house.ktu_ais.models.GetGradesResponse
import lt.hacker_house.ktu_ais.models.GradeModel
import lt.hacker_house.ktu_ais.models.GradeUpdateModel
import lt.hacker_house.ktu_ais.models.WeekModel

/**
 * Created by simonas on 5/2/17.
 */

fun ViewGroup.setMargin(left: Int = -1, top: Int = -1, right: Int = -1, bottom: Int = -1) {
    val params =  this.layoutParams as ViewGroup.MarginLayoutParams

    if (left != -1) {
        params.leftMargin = left
    }
    if (top != -1) {
        params.topMargin = top
    }
    if (right != -1) {
        params.rightMargin = right
    }
    if (bottom != -1) {
        params.bottomMargin = bottom
    }

    this.layoutParams = params
    this.requestLayout()
}

/**
 * @param selectedSemester string of a number in this format ##. e.g 04
 */
fun Collection<GetGradesResponse>.toWeekList(selectedSemester: String): MutableList<WeekModel> {

    val map: HashMap<String, MutableList<GetGradesResponse>> = HashMap()

    this.filter { it.semesterNumber == selectedSemester }.forEach {
        var get = map[it.week!!]
        if (get != null) {
            get.add(it)
        } else {
            get = mutableListOf()
            get.add(it)
        }
        map.put(it.week!!, get)
    }

    val respList = mutableListOf<WeekModel>()
    map.forEach { (key, item) ->
        val model = WeekModel()
        model.weekNumbersString = key
        item.forEach {
            val gradeModel = GradeModel()
            gradeModel.typeId = it.typeId
            gradeModel.type = it.type
            gradeModel.name = it.name
            gradeModel.mark = it.rlMark
            model.grades.add(gradeModel)
        }
        respList.add(model)
    }
    respList.sortBy { it.weekNumbers[0] }
    return respList
}

fun Activity.startActivityNoBack(target: Class<*>) {
    val intent = Intent(this, target)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    this.startActivity(intent)
}

fun Collection<GetGradesResponse>.diff(newList: Collection<GetGradesResponse>): Collection<GradeUpdateModel> {
    val resultList = mutableListOf<GradeUpdateModel>()

    this.forEach { oldItem ->
        val newItemList = newList.findAll(oldItem)
        val oldItemList = this.findAll(oldItem)
        newItemList.forEachIndexed { index, newListItem ->
            var oldListItem = oldItemList[index]
            newListItem.diff(oldListItem) { newGrade ->
                val comp = oldListItem
                val new = newListItem
                resultList.add(newGrade)
            }
        }
    }
    return resultList
}

fun Collection<GetGradesResponse>.findAll(other: GetGradesResponse): List<GetGradesResponse> {
    val resultList = mutableListOf<GetGradesResponse>()
    this.forEach { item ->
        if (item == other) {
            resultList.add(item)
        }
    }
    return resultList
}

fun View.addOnViewShrinkListener(isSmaller: (Boolean)->(Unit)) {
    this.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
        if (bottom < oldBottom) {
            // Opened
            v.postDelayed({
                isSmaller.invoke(true)
            }, 1) // 1ms delay is required.
        } else if (bottom != oldBottom) {
            // Closed
            v.postDelayed({
                isSmaller.invoke(false)
            }, 1) // 1ms delay is required.
        }
    }
}

fun Collection<GetGradesResponse>.filterSemester(semersterNum: String)
        = this.filter { it.semesterNumber == semersterNum }