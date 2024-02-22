package com.kivous.notes.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kivous.notes.databinding.FragmentCreateNoteBinding


class CreateNoteFragment(private val viewController: (FragmentCreateNoteBinding) -> Unit) :
    BottomSheetDialogFragment() {
    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewController(binding)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}