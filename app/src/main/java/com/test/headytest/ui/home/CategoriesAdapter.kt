package com.test.headytest.ui.home

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.test.headytest.R


class CategoriesAdapter(private val _context: Context, private val categoriesList: List<Category>, private val childCategoriesMap: HashMap<Int, List<Category>>) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosititon: Int): Category {
        return this.childCategoriesMap[this.categoriesList[groupPosition].id!!]!![childPosititon]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val childCategory = getChild(groupPosition, childPosition)

        if (convertView == null) {
            val infalInflater = this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_item_child_category, null)
        }

        val txtListChild = convertView!!
                .findViewById(R.id.tv_child_category) as TextView

        txtListChild.text = childCategory.name
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this.childCategoriesMap[this.categoriesList[groupPosition].id]!!.size
    }

    override fun getGroup(groupPosition: Int): Category {
        return this.categoriesList[groupPosition]
    }


    override fun getGroupCount(): Int {
        return this.categoriesList.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val mainCategory = getGroup(groupPosition)
        if (convertView == null) {
            val infalInflater = this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_item_header_category, null)
        }

        val lblListHeader = convertView!!
                .findViewById(R.id.tv_header_category) as TextView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.text = mainCategory.name


        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}