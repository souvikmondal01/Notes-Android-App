package com.kivous.notes.view.fragments

import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.kivous.notes.data.model.Note
import com.kivous.notes.databinding.FragmentEditNoteBinding
import com.kivous.notes.view.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonNote = arguments?.getString("KEY")
        note = Gson().fromJson(jsonNote, Note::class.java)

        binding.apply {
            cvBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            cvDelete.setOnClickListener {
                viewModel.deleteNote(note)
                findNavController().navigateUp()
            }
            etNote.setText(note.note)
            etNote.requestFocus()

            btnSaveChanges.setOnClickListener {
                val note = Note(note.id, etNote.text.toString(), note.userId)
                viewModel.updateNote(note)
                findNavController().navigateUp()
            }

            // if edittext empty disable save changes button
            etNote.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                    binding.btnSaveChanges.isEnabled = p0.toString().trim().isNotEmpty()
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}