package dev.huyghe.morseflashlight.ui.morse.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import dev.huyghe.morseflashlight.databinding.FragmentMessageHistoryBinding;
import dev.huyghe.morseflashlight.ui.MessageViewModel;
import dev.huyghe.morseflashlight.ui.morse.MessageListAdapter;

/**
 * A {@link Fragment} tasked with displaying the message history.
 * Use the {@link MessageHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageHistoryFragment extends Fragment {

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
        MessageListAdapter messageListAdapter = new MessageListAdapter(
                message -> messageViewModel.flashAndSaveMessage(message)
        );
        binding.fragmentMessageHistoryRv.setAdapter(messageListAdapter);
        binding.fragmentMessageHistoryRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        messageViewModel
                .getAllMessages()
                .observe(this.getViewLifecycleOwner(), messageListAdapter::update);

        return binding.getRoot();
    }
}