package dev.huyghe.morseflashlight.ui.morse.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import dev.huyghe.morseflashlight.R;
import dev.huyghe.morseflashlight.data.Category;
import dev.huyghe.morseflashlight.databinding.FragmentMessageHistoryBinding;
import dev.huyghe.morseflashlight.ui.MessageViewModel;
import dev.huyghe.morseflashlight.ui.morse.MessageListAdapter;

/**
 * A {@link Fragment} tasked with displaying the message history.
 * Use the {@link MessageHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageHistoryFragment extends Fragment {
    private static final String TAG = MessageHistoryFragment.class.getSimpleName();

    public MessageHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessageHistoryFragment.
     */
    public static MessageHistoryFragment newInstance() {
        return new MessageHistoryFragment();
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
        FragmentMessageHistoryBinding binding = FragmentMessageHistoryBinding.inflate(inflater, container, false);

        // Message list setup
        messageViewModel
                .getAllCategories()
                .observe(this.getViewLifecycleOwner(), newVal -> {
                });
        MessageListAdapter messageListAdapter = new MessageListAdapter(
                // Item click.
                message -> messageViewModel.flashAndSaveMessage(message),
                // Save icon click.
                message -> MessageListAdapter.openDialog(this.getContext(), messageViewModel, message)
        );
        binding.fragmentMessageHistoryRv.setAdapter(messageListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.fragmentMessageHistoryRv.setLayoutManager(linearLayoutManager);
        binding.fragmentMessageHistoryRv.addItemDecoration(
                new MaterialDividerItemDecoration(
                        binding.fragmentMessageHistoryRv.getContext(),
                        linearLayoutManager.getOrientation()
                )
        );
        messageViewModel
                .getAllMessages()
                .observe(this.getViewLifecycleOwner(), messageListAdapter::update);

        return binding.getRoot();
    }
}