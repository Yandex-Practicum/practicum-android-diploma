package ru.practicum.android.diploma.ui.fragments

import android.R.id.shareText
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private var isLiked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includeToolbar.btnBack.visibility = View.VISIBLE
        binding.includeToolbar.toolbar.titleMarginStart =
            resources.getDimensionPixelSize(R.dimen.indent_56)

        binding.includeToolbar.toolbar.title = getString(R.string.vacancy)

        binding.includeToolbar.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonLike.setOnClickListener {
            toggleLikeButton()
        }

        binding.buttonShare.setOnClickListener {
            shareContent()
        }
    }

    private fun toggleLikeButton() {
        isLiked = !isLiked

        if (isLiked) {
            binding.buttonLike.setImageResource(R.drawable.ic_favorites_on_red_24)
        } else {
            binding.buttonLike.setImageResource(R.drawable.ic_favorites_off_24)
        }
    }
    private fun shareContent() {

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, null))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
