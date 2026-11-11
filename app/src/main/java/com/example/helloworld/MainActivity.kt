package com.example.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Setting up a simple layout programmatically for testing Print Master
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 100, 50, 50)
        }

        // Title text
        val title = android.widget.TextView(this).apply {
            text = "Print Master (Android 14)"
            textSize = 24f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        layout.addView(title)

        // Print Aadhaar Button
        val aadhaarButton = Button(this).apply {
            text = "🪪 Print Aadhaar Card"
            setOnClickListener {
                Toast.makeText(this@MainActivity, "Scanning Aadhaar Front & Back...", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(aadhaarButton)

        // Print Now Button
        val printButton = Button(this).apply {
            text = "🖨️ Print Now (B&W / A4)"
            setOnClickListener {
                Toast.makeText(this@MainActivity, "Connecting to Wi-Fi Printer...", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(printButton)

        setContentView(layout)
    }
}
