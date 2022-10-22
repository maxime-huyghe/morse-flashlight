package dev.huyghe.morseflashlight

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MorseInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morse_input)

        val flashlightController = FlashlightController(this)

        val edit = findViewById<EditText>(R.id.morse_input_edit)

        findViewById<Button>(R.id.morse_input_button_flash).setOnClickListener {
            val morseCharacters = edit.text.toList().map(MorseCharacter::newFromChar)
            flashlightController.flashMorseString(morseCharacters)
        }
    }
}