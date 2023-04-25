package com.test.lattesample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.test.lattesample.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by lazy { FragmentMainBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            with(spinner1) {
                adapter = ArrayAdapter.createFromResource(
                    context,
                    R.array.items,
                    android.R.layout.simple_spinner_item
                ).also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
                setSelection(2)
            }

            button1.setOnClickListener {
                Intent(requireContext(), SuplementaryActivity::class.java)
                    .also {
                        startActivity(it)
                    }
            }

            button2.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, SuplementaryFragment(), "fragment:supl")
                    .commit()
            }
        }
    }
}