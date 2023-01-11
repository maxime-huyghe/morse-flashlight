package dev.huyghe.morseflashlight.domain;

import static android.content.Context.CAMERA_SERVICE;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class FlashlightService {
    private static final String TAG = FlashlightService.class.getSimpleName();

    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static RunnableFuture<Object> runningFuture = null;
    private static boolean flashlightEnabled;
    private static boolean strobing;
    private static boolean sosOngoing;

    private static volatile int basetime = 200;

    private final Context context;
    private final CameraManager cameraManager;
    private String cameraId;

    @Inject
    public FlashlightService(@ApplicationContext Context context) {
        this.context = context;
        cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (Exception e) {
            Toast.makeText(context, "Couldn't find a camera! I give up.", Toast.LENGTH_SHORT).show();
        }
        flashlightEnabled = false;
    }

    /**
     * From 0 to 100
     *
     * @return the flashing speed
     */
    public int getFlashingSpeed() {
        return 100 - (basetime - 100) / 4;
    }

    /**
     * From 0 to 100
     *
     * @param bt the flashing speed
     */
    public void setFlashingSpeed(int bt) {
        basetime = (100 - bt) * 4 + 100;
    }

    public synchronized void toggleFlashLight(boolean on) {
        try {
            flashlightEnabled = on;
            // Avoid crashes on emulator.
            if (cameraManager
                    .getCameraCharacteristics(cameraId)
                    .get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
            ) {
                cameraManager.setTorchMode(cameraId, on);
            }
        } catch (CameraAccessException e) {
            Toast.makeText(context, "Couldn't enable the flashlight! I give up.", Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleFlashLight() {
        toggleFlashLight(!flashlightEnabled);
    }

    public void toggleStrobe() {
        cancelFutureIfRunning();
        if (!strobing) {
            strobing = true;
            runningFuture = new FutureTask<>(() -> {
                //noinspection InfiniteLoopStatement
                while (true) {
                    toggleFlashLight();
                    //noinspection BusyWait
                    Thread.sleep(basetime);
                }
            });
            executor.execute(runningFuture);
        } else {
            toggleFlashLight(false);
            strobing = false;
        }
    }

    /**
     * Flashes the standard SOS signal. This doesn't just call flashMorse("SOS") because there
     * shouldn't be inter-character spaces between the letters.
     */
    public void toggleSOS() {
        cancelFutureIfRunning();
        if (!sosOngoing) {
            sosOngoing = true;
            runningFuture = new FutureTask<>(() -> {
                List<MorseCharacter.MorseImpulse> sos = Arrays.asList(
                        MorseCharacter.MorseImpulse.Short, MorseCharacter.MorseImpulse.Short, MorseCharacter.MorseImpulse.Short,
                        MorseCharacter.MorseImpulse.Long, MorseCharacter.MorseImpulse.Long, MorseCharacter.MorseImpulse.Long,
                        MorseCharacter.MorseImpulse.Short, MorseCharacter.MorseImpulse.Short, MorseCharacter.MorseImpulse.Short
                );
                //noinspection InfiniteLoopStatement
                while (true) {
                    flashMorseCharacter(sos);
                    //noinspection BusyWait
                    Thread.sleep(basetime * 3L);
                }
            });
            executor.execute(runningFuture);
        } else {
            toggleFlashLight(false);
            sosOngoing = false;
        }
    }

    public interface CharacterStartedFlashingListener {
        void onCharacterStartedFlashing(int i);
    }

    public void flashString(String message) {
        List<MorseCharacter> morseCharacters = new ArrayList<>();
        for (int i = 0; i < message.length(); ++i) {
            morseCharacters.add(MorseCharacter.newFromChar(message.charAt(i)));
        }
        try {
            flashMorseString(
                    morseCharacters,
                    index -> Log.d(TAG, "received char " + morseCharacters.get(index).getCharacter())
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void flashMorseString(List<MorseCharacter> characters, CharacterStartedFlashingListener onCharacterStartedFlashing) throws InterruptedException {
        cancelFutureIfRunning();
        runningFuture = new FutureTask<>(() -> {
            for (int i = 0; i < characters.size(); ++i) {
                int finalI = i;
                new Handler(Looper.getMainLooper())
                        .post(
                                () -> onCharacterStartedFlashing.onCharacterStartedFlashing(finalI)
                        );
                flashMorseCharacter(characters.get(i).getImpulses());
                if (i != characters.size() - 1) {
                    //noinspection BusyWait
                    Thread.sleep(basetime * 3L);
                }
            }
            return null;
        });
        executor.execute(runningFuture);
    }

    @SuppressWarnings("BusyWait")
    public void flashMorseCharacter(List<MorseCharacter.MorseImpulse> impulses) throws InterruptedException {
        for (int i = 0; i < impulses.size(); ++i) {
            switch (impulses.get(i)) {
                case Short:
                    toggleFlashLight(true);
                    Thread.sleep(basetime);
                    break;
                case Long:
                    toggleFlashLight(true);
                    Thread.sleep(basetime * 3L);
                    break;
                case Space:
                    toggleFlashLight(false);
                    Thread.sleep(basetime);
                    break;
            }
            toggleFlashLight(false);
            if (i != impulses.size() - 1) {
                Thread.sleep(basetime);
            }
        }
    }

    public void turnOffCompletely() {
        cancelFutureIfRunning();
        toggleFlashLight(false);
    }

    private void cancelFutureIfRunning() {
        if (runningFuture != null && !runningFuture.isDone()) {
            runningFuture.cancel(true);
        }
    }
}
