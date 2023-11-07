package com.team2127.presentation.ui

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.team2127.presentation.R

object LocationAdapter{
    @JvmStatic
    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: List<String>?) {
        entries?.run {
            val arrayAdapter = ArrayAdapter(context, R.layout.item_dropdown_count, entries)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = arrayAdapter
        }
    }


    @JvmStatic
    @BindingAdapter("selectedValue")
    fun Spinner.setSelectedValue(selectedValue: String) {
        adapter?.run {
            val position =
                (adapter as ArrayAdapter<Any>).getPosition(selectedValue)
            setSelection(position, false)
            tag = position
        }
    }


    // attribute는 바인딩 어뎁터처럼 value을 의미한다. event는 반응할 bindingapdater의 value를 의미한다.
    @JvmStatic
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    fun Spinner.getSelectedValue(): Any? {
        return selectedItem
    }


    // 위에것이 실행되고 아래의 bindingapdater가 실행된다.
    @JvmStatic
    @BindingAdapter("selectedValueAttrChanged")
    fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {

        inverseBindingListener?.run {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (tag != position) {
                        inverseBindingListener.onChange()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }


}
