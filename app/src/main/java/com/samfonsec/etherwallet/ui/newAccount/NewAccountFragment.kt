package com.samfonsec.etherwallet.ui.newAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.samfonsec.etherwallet.R
import com.samfonsec.etherwallet.databinding.FragmentNewAccountBinding
import com.samfonsec.etherwallet.model.UiState.Feedback
import com.samfonsec.etherwallet.model.UiState.Loading
import com.samfonsec.etherwallet.model.UserData
import com.samfonsec.etherwallet.utils.Constants.FEEDBACK_MESSAGE_ARG
import com.samfonsec.etherwallet.utils.isValid
import com.samfonsec.etherwallet.utils.value
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewAccountFragment : Fragment() {

    private var _binding: FragmentNewAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewAccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.generateDeviceID(context?.contentResolver)
        setupObservers()
        setupUi()
    }

    private fun setupUi() {
        with(binding) {
            tvDeviceId.text = getString(R.string.label_device_id, viewModel.deviceId)
            btCreateAccount.setOnClickListener {
                viewModel.createAccount(retrieveUserData())
            }
            etName.doOnTextChanged { _, _, _, _ -> validateData() }
            etLastName.doOnTextChanged { _, _, _, _ -> validateData() }
            etEmail.doOnTextChanged { _, _, _, _ -> validateData() }
            etPin.doOnTextChanged { _, _, _, _ -> validateData() }
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> showLoading()
                is Feedback -> feedbackState(state.message)
                else -> initialState()
            }
        }
    }

    private fun initialState() {
        with(binding) {
            contentView.isVisible = true
            pbLoading.isVisible = false
        }
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.isVisible = true
            contentView.isVisible = false
        }
    }

    private fun feedbackState(feedback: String?) {
        findNavController().navigate(
            R.id.to_FeedbackFragment,
            bundleOf(FEEDBACK_MESSAGE_ARG to feedback)
        )
    }

    private fun retrieveUserData() = with(binding) {
        UserData(
            emailAddress = etEmail.value(),
            firstName = etName.value(),
            lastName = etLastName.value(),
            pinNumber = etPin.value()
        )
    }

    private fun validateData() {
        with(binding) {
            btCreateAccount.isEnabled =
                etEmail.isValid() && etName.isValid() && etLastName.isValid() && etPin.isValid()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}