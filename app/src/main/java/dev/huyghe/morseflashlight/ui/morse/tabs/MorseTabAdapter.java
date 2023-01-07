package dev.huyghe.morseflashlight.ui.morse.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import dev.huyghe.morseflashlight.R;

public class MorseTabAdapter extends FragmentStateAdapter {

    public MorseTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return MessageHistoryFragment.newInstance();
            case 1:
                return SavedMessagesFragment.newInstance();
            default:
                throw new IllegalArgumentException("wrong position");
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * Gets the page title corresponding to the given page position.
     *
     * @param position the page position
     * @return the title's resource id
     */
    public static int getPageTitle(int position) {
        switch (position) {
            case 0:
                return R.string.morse_tab_history;
            case 1:
                return R.string.morse_tab_saved_messages;
            default:
                throw new IllegalArgumentException("wrong position");
        }
    }
}
