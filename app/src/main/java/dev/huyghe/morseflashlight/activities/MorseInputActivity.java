package dev.huyghe.morseflashlight.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import dev.huyghe.morseflashlight.FlashlightController;
import dev.huyghe.morseflashlight.MorseCharacter;
import dev.huyghe.morseflashlight.R;

public class MorseInputActivity extends AppCompatActivity {
    private static String TAG = "MorseInputActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_input);

        FlashlightController flashlightController = new FlashlightController(this);

        EditText edit = findViewById(R.id.morse_input_edit);
        Button btn_flash = findViewById(R.id.morse_input_button_flash);
        btn_flash.setOnClickListener(view -> {
            String s = edit.getText().toString();
            List<MorseCharacter> morseCharacters = new ArrayList<>();
            for (int i = 0; i < s.length(); ++i) {
                morseCharacters.add(MorseCharacter.newFromChar(s.charAt(i)));
            }
            try {
                flashlightController.flashMorseString(
                        morseCharacters,
                        index -> Log.d(TAG, "received char " + morseCharacters.get(index).getCharacter())
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
