package com.samfonsec.etherwallet.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samfonsec.etherwallet.databinding.FragmentFeedbackBinding
import com.samfonsec.etherwallet.utils.Constants.FEEDBACK_MESSAGE_ARG

class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildUi()
    }

    private fun buildUi() {
        arguments?.getString(FEEDBACK_MESSAGE_ARG)?.let { message ->
            binding.tvResponse.text = message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}