package com.example.simplenoteapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.simplenoteapp.database.NotesDatabase
import com.example.simplenoteapp.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {

    private var currentDate: String? = null
    private var noteId = -1
    private val showPopMenu = ShowPopupMenu()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = requireArguments().getInt("noteId",-1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (noteId != -1) {
            launch {
                context?.let {
                    val notes = NotesDatabase.getDatabase(it).noteDao().getNote(noteId)
                    editNoteTitle.setText(notes.title)
                    editNoteSubTitle.setText(notes.subTitle)
                    editNoteDesc.setText(notes.noteText)
                }
            }

            imgMore.visibility = View.VISIBLE
        } else {
            imgMore.visibility = View.GONE
        }

        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = dateFormat.format(Date())
        textDateTime.text = currentDate

        imgDone.setOnClickListener {
            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }
        }

        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener {
            showPopMenu.showPopMenu(requireActivity(), it, ::deleteNote)
        }
    }

    private fun updateNote() {
        launch {
            context?.let {
                val notes = NotesDatabase.getDatabase(it).noteDao().getNote(noteId)

                notes.title = editNoteTitle.text.toString()
                notes.subTitle = editNoteSubTitle.text.toString()
                notes.noteText = editNoteDesc.text.toString()
                notes.dateTime = currentDate

                NotesDatabase.getDatabase(it).noteDao().update(notes)

                editNoteTitle.setText("")
                editNoteSubTitle.setText("")
                editNoteDesc.setText("")
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun saveNote() {
        when {
            editNoteTitle.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
            }
            editNoteSubTitle.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
            }
            editNoteDesc.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
            }
        }

        launch {
            val notes = Notes()
            notes.title = editNoteTitle.text.toString()
            notes.subTitle = editNoteSubTitle.text.toString()
            notes.noteText = editNoteDesc.text.toString()
            notes.dateTime = currentDate

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insert(notes)
                editNoteTitle.setText("")
                editNoteSubTitle.setText("")
                editNoteDesc.setText("")
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun deleteNote() {
        launch {
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().deleteNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}