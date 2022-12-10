package dev.huyghe.morseflashlight.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import dev.huyghe.morseflashlight.databinding.ActivityMorseInputBinding;
import dev.huyghe.morseflashlight.domain.FlashlightController;
import dev.huyghe.morseflashlight.domain.MorseCharacter;

@AndroidEntryPoint
public class MorseInputActivity extends AppCompatActivity {
    private static final String TAG = "MorseInputActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMorseInputBinding binding = ActivityMorseInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FlashlightController flashlightController = new FlashlightController(this);

        EditText edit = binding.morseInputEdit;
        Button btn_flash = binding.morseInputButtonFlash;
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
