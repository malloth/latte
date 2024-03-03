package pl.codesamurai.latte.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import pl.codesamurai.latte.sample.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val spinnerAdapter by lazy {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.items,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
    private val binding by lazy { FragmentMainBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            with(spinner1) {
                adapter = spinnerAdapter
                setSelection(2)
            }

            button1.setOnClickListener {
                showSupplementaryActivity()
            }

            button2.setOnClickListener {
                showSupplementaryFragment()
            }
        }
    }

    private fun showSupplementaryActivity() {
        Intent(requireContext(), SupplementaryActivity::class.java)
            .also {
                startActivity(it)
            }
    }

    private fun showSupplementaryFragment() {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragment_container,
                SupplementaryFragment(),
                FRAGMENT_SUPPLEMENTARY
            )
            .commit()
    }

    private companion object {

        const val FRAGMENT_SUPPLEMENTARY = "fragment:supl"
    }
}