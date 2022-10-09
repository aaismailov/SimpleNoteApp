package com.example.simplenoteapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast

class ShowPopupMenu {

    @SuppressLint("DiscouragedPrivateApi")
    fun showPopMenu(context: Context, view: View, deleteNote: () -> Unit) {

        val pop = PopupMenu(context, view)
        pop.inflate(R.menu.popup_menu_button)

        pop.setOnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.popMenu_Delete -> {
                    deleteNote()
                    Toast.makeText(context, "Note was Deleted", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        try {
            val fieldMpopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMpopup.isAccessible = true
            val mPopup = fieldMpopup.get(pop)
            mPopup.javaClass
                .getDeclaredMethod("setFoeceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)

        } catch (e: Exception) {
        } finally {
            pop.show()
        }

    }
}