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
public class FamilyFragment extends Fragment {

    MediaPlayer mmediaPlayer;
    private AudioManager maudiomanager;
    public FamilyFragment() {
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
        words.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_family);
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