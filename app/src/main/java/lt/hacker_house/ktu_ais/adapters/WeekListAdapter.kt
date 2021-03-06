package lt.hacker_house.ktu_ais.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import lt.hacker_house.ktu_ais.models.RlWeekModel
import lt.hacker_house.ktu_ais.views.components.WeekItem

/**
 * Created by simonas on 5/2/17.
 */

class WeekListAdapter : RecyclerView.Adapter<WeekListAdapter.WeekVH>() {
    val list: MutableList<RlWeekModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekVH? {
        val view = WeekItem(parent.context)
        val vh = WeekVH(view)
        return vh
    }

    override fun onBindViewHolder(holder: WeekVH, index: Int) {
        holder.weekView.setModel(list[index])

//        holder.weekView.setMargin(bottom = 4.dpToPx(), top = 4.dpToPx())
//
//        if (index == 0) {
//            holder.weekView.setMargin(bottom = 8.dpToPx())
//        } else if (index == itemCount-1) {
//            holder.weekView.setMargin(bottom = 8.dpToPx())
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class WeekVH(val weekView: WeekItem) : RecyclerView.ViewHolder(weekView)
}
