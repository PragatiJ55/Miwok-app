package com.example.android.miwok;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mcolorResourceid;


    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceid) {
        super(context, 0, words);
        mcolorResourceid = colorResourceid;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);
        TextView mowakTextView = (TextView) listItemView.findViewById(R.id.mowak_textView);
        mowakTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_textView);
        defaultTextView.setText(currentWord.getEnglishTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to

        int color = ContextCompat.getColor(getContext(), mcolorResourceid);
        textContainer.setBackgroundColor(color);
        if (currentWord.getImageResource_id() == 0) {
            imageView.setVisibility(View.GONE);
            return listItemView;
        } else {
            imageView.setImageResource(currentWord.getImageResource_id());
            return listItemView;
        }


    }





}
