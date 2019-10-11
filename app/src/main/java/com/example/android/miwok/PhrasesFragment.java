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
public class PhrasesFragment extends Fragment {

    MediaPlayer mmediaPlayer;
    private AudioManager maudiomanager;
    public PhrasesFragment() {
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
        words.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));
        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_phrases);
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