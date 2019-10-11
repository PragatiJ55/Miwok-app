package com.example.android.miwok;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    MediaPlayer mmediaPlayer;
    private AudioManager maudiomanager;
    public ColorsFragment() {
        // Required empty public constructor
    }
    private  MediaPlayer.OnCompletionListener mcompletionlistener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mmediaPlayer.release();
        }
    };
    private AudioManager.OnAudioFocusChangeListener afchangelistener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                mmediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                mmediaPlayer.pause();
                mmediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                mmediaPlayer.release();
            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.words_list, container, false);
        maudiomanager = (AudioManager) getActivity().getSystemService(getContext().AUDIO_SERVICE);
        final ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                released();
                Word word=words.get(position);
                int result=maudiomanager.requestAudioFocus(afchangelistener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mmediaPlayer = MediaPlayer.create(getActivity(), word.getSoumdResource_id());
                    mmediaPlayer.start();
                    mmediaPlayer.setOnCompletionListener(mcompletionlistener);
                }

            }
        });
        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
        released();
    }

    private void released()
    {
        if(mmediaPlayer!=null)
        {
            mmediaPlayer.release();
            mmediaPlayer=null;
        }
        maudiomanager.abandonAudioFocus(afchangelistener);

    }

}
