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
public class NumbersFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    MediaPlayer mmediaPlayer;
    private AudioManager maudiomanager;
    public static NumbersFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        NumbersFragment fragment = new NumbersFragment();
        fragment.setArguments(args);
        return fragment;
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
        words.add(new Word("One","Lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five","massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six","temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("Nine","wo’e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","na’aacha",R.drawable.number_ten,R.raw.number_ten));
        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
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
