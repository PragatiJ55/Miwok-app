package com.example.android.miwok;

import java.util.ArrayList;

public class Word {
    private String mdefaultTranslation;
    private String mMiwokTranslation;
    private int mresource_id;
    private int maudio_id;

    public int getImageResource_id() {
        return mresource_id;
    }
    public int getSoumdResource_id(){return maudio_id;}


    public Word(String defaultTranslation, String MiwokTranslation,int audio_id)
    {
        mdefaultTranslation=defaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        maudio_id=audio_id;
    }
    public Word(String defaultTranslation, String MiwokTranslation,int resource_id,int audio_id)
    {
        mdefaultTranslation=defaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        mresource_id=resource_id;
        maudio_id=audio_id;
    }

    public String getEnglishTranslation()
    {
        return mdefaultTranslation;
    }
    public String getMiwokTranslation()
    {
        return mMiwokTranslation;
    }


}
