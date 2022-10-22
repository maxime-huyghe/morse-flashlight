package dev.huyghe.morseflashlight;

import static android.content.Context.CAMERA_SERVICE;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class FlashlightController {
    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static RunnableFuture<Object> runningFuture = null;
    private static boolean flashlightEnabled;
    private static boolean strobing;
    private static boolean sosOngoing;

    private static volatile int basetime = 200;

    private Context context;
    private CameraManager cameraManager;
    private String cameraId;

    public FlashlightController(Context context) {
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
            cameraManager.setTorchMode(cameraId, on);
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
                while (true) {
                    toggleFlashLight();
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
                while (true) {
                    flashMorseCharacter(sos);
                    Thread.sleep(basetime * 3);
                }
            });
            executor.execute(runningFuture);
        } else {
            toggleFlashLight(false);
            sosOngoing = false;
        }
    }

    public void flashMorseString(List<MorseCharacter> characters) throws InterruptedException {
        throw new Error("Unimplemented!");
    }

    public void flashMorseCharacter(List<MorseCharacter.MorseImpulse> impulses) throws InterruptedException {
        for (int i = 0; i < impulses.size(); ++i) {
            switch (impulses.get(i)) {
                case Short:
                    toggleFlashLight(true);
                    Thread.sleep(basetime);
                    break;
                case Long:
                    toggleFlashLight(true);
                    Thread.sleep(basetime * 3);
                    break;
                case Space:
                    toggleFlashLight(false);
                    Thread.sleep(basetime * 3);
                    break;
            }
            toggleFlashLight(false);
            if (i != impulses.size() - 1) {
                Thread.sleep(basetime);
            }
        }
    }

    public void cancelFutureIfRunning() {
        if (runningFuture != null && !runningFuture.isDone()) {
            runningFuture.cancel(true);
        }
    }
}
