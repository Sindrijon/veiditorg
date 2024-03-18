package com.example.veiditorg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.veiditorg.databinding.FragmentMarketplaceBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MarketplaceFragment : Fragment() {

    private var _binding: FragmentMarketplaceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permitCard = binding.cardViewPermit
        val expandedCard = binding.cardViewPermitExpanded

        permitCard.setOnClickListener {
            if (expandedCard.visibility == View.VISIBLE) {
                // Collapse the card
                permitCard.visibility = View.VISIBLE
                expandedCard.visibility = View.GONE
            } else {
                // Expand the card
                permitCard.visibility = View.GONE
                expandedCard.visibility = View.VISIBLE
            }
        }

/**
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_MarketplaceFragment_to_LoginFragment)
        }
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}