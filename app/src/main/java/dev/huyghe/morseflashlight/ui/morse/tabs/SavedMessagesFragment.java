package dev.huyghe.morseflashlight.ui.morse.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import dev.huyghe.morseflashlight.databinding.FragmentSavedMessagesBinding;
import dev.huyghe.morseflashlight.ui.MessageViewModel;
import dev.huyghe.morseflashlight.ui.morse.CategoryListAdapter;
import dev.huyghe.morseflashlight.ui.morse.MessageListAdapter;

/**
 * A {@link Fragment} tasked with displaying saved messages.
 * Use the {@link SavedMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedMessagesFragment extends Fragment {
    private static final String TAG = SavedMessagesFragment.class.getSimpleName();

    public SavedMessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessageHistoryFragment.
     */
    public static SavedMessagesFragment newInstance() {
        return new SavedMessagesFragment();
    }

    private MessageViewModel messageViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSavedMessagesBinding binding = FragmentSavedMessagesBinding.inflate(inflater, container, false);

        // New category input setup
        binding.fragmentSavedMessagesButtonAddCategory.setOnClickListener(view -> {
            String name = binding.fragmentSavedMessagesEditAddCategory.getText().toString();
            messageViewModel.createCategory(name);
            binding.fragmentSavedMessagesEditAddCategory.setText("");
        });

        // Category list setup
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(
                message -> messageViewModel.flashAndSaveMessage(message),
                message -> MessageListAdapter.openDialog(this.getContext(), messageViewModel, message)
        );
        binding.fragmentSavedMessagesRv.setAdapter(categoryListAdapter);
        binding.fragmentSavedMessagesRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        messageViewModel
                .getAllCategoriesWithMessages()
                .observe(this.getViewLifecycleOwner(), categoryListAdapter::update);
        messageViewModel
                .getAllCategoriesWithMessages()
                .observe(this.getViewLifecycleOwner(), newData -> Log.d(TAG, newData.toString()));

        return binding.getRoot();
    }
}