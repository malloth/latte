package com.test.lattesample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            requireFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, SuplementaryFragment(), "fragment:supl")
                .commit()
        }
    }
}