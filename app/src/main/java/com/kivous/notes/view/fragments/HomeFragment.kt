package com.kivous.notes.view.fragments

import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.kivous.notes.R
import com.kivous.notes.data.model.Note
import com.kivous.notes.databinding.FragmentCreateNoteBinding
import com.kivous.notes.databinding.FragmentHomeBinding
import com.kivous.notes.utils.Extensions.glideCircle
import com.kivous.notes.utils.Extensions.invisible
import com.kivous.notes.utils.Extensions.setImageViewTint
import com.kivous.notes.utils.Extensions.visible
import com.kivous.notes.view.adapters.NoteAdapter
import com.kivous.notes.view.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var bottomSheet: CreateNoteFragment
    private lateinit var googleSignInAccount: GoogleSignInAccount
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())!!

        googleSignInAccount.photoUrl?.let {
            binding.ivProfile.glideCircle(requireContext(), it)
        }

        try {
            bottomSheet = CreateNoteFragment(::bottomSheetViewController)
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
        }

        binding.apply {
            cvBackArrow.setOnClickListener {
                requireActivity().finish()
            }
            vProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
            fab.setOnClickListener {
                bottomSheet.show(childFragmentManager, "")
            }
        }

        setUpRecyclerView()
        observeNoteListEmptyStateAndUpdateUI()

    }

    override fun onPause() {
        super.onPause()
        if (bottomSheet.isVisible) {
            bottomSheet.dismissNow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpRecyclerView() {
        adapter = NoteAdapter(::noteViewController)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            viewModel.getAllNotes(googleSignInAccount.id.toString()).collectLatest { note ->
                adapter.differ.submitList(note)
            }
        }
    }

    private fun noteViewController(holder: NoteAdapter.ViewHolder, note: Note) {
        holder.apply {
            binding.apply {
                tvNote.text = note.note
                cvDelete.setOnClickListener {
                    viewModel.deleteNote(note)
                }
            }
            itemView.setOnClickListener {
                val bundle = bundleOf("KEY" to Gson().toJson(note))
                findNavController().navigate(R.id.action_homeFragment_to_editNoteFragment, bundle)
            }
        }

    }

    private fun observeNoteListEmptyStateAndUpdateUI() {
        lifecycleScope.launch {
            viewModel.isNoteListEmpty(googleSignInAccount.id.toString()).collectLatest {
                binding.tvEmpty.isVisible = it

                if (it) {
                    binding.recyclerView.invisible()
                } else {
                    binding.recyclerView.visible()
                }
            }
        }
    }

    private fun bottomSheetViewController(binding: FragmentCreateNoteBinding) {
        binding.apply {
            etNote.requestFocus()
            cvSave.setOnClickListener {
                val note = Note(
                    note = etNote.text.toString().trim(),
                    userId = googleSignInAccount.id.toString(),
                )
                viewModel.createNote(note)
                bottomSheet.dismiss()
                etNote.text.clear()
            }
            cvSave.isClickable = false

            // if edittext empty disable note save option and toggle save icon color accordingly
            etNote.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

                    cvSave.isClickable = p0.toString().trim().isNotEmpty()

                    if (p0.toString().trim().isNotEmpty()) {
                        setImageViewTint(
                            ivDone, com.google.android.material.R.attr.colorOnSurface
                        )
                    } else {
                        setImageViewTint(
                            ivDone, com.google.android.material.R.attr.colorOutline
                        )
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })

        }

    }

}