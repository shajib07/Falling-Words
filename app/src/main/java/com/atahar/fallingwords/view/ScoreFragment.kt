package com.atahar.fallingwords.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.atahar.fallingwords.R
import com.atahar.fallingwords.databinding.FragmentScoreBinding
import com.atahar.fallingwords.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreFragment : Fragment() {

    private val sharedViewModel: WordViewModel by activityViewModels()

    private var binding: FragmentScoreBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentScoreBinding.inflate(inflater)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            scoreFragment = this@ScoreFragment
            viewModel = sharedViewModel
        }
    }

    fun openGameFragment() = findNavController().navigate(
        R.id.action_scoreFragment_to_gameFragment
    )

    private fun openHomeFragment() = findNavController().navigate(
        R.id.action_scoreFragment_to_homeFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    openHomeFragment()
                }
            })
    }

}