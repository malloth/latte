package com.test.lattesample

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

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
            Intent(this, SuplementaryActivity::class.java)
                .also {
                    startActivity(it)
                }
        }
    }
}