package com.kivous.notes.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kivous.notes.R
import com.kivous.notes.databinding.FragmentProfileBinding
import com.kivous.notes.utils.Extensions.glideCircle

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())

        googleSignInAccount?.photoUrl?.let {
            binding.ivProfile.glideCircle(requireContext(), it)
        }

        binding.apply {
            cvBackArrow.setOnClickListener {
                findNavController().navigateUp()
            }
            tvName.text = googleSignInAccount?.displayName
            tvEmail.text = googleSignInAccount?.email

            btnSignOut.setOnClickListener {
                googleSignInClient.signOut()
                findNavController().navigate(R.id.action_profileFragment_to_authFragment)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}