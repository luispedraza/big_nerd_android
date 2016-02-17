package es.luispedraza.criminalintent;

import java.util.UUID;

/**
 * Created by luis on 16/2/16.
 */
public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime(String title) {
        mTitle = title;
        mId = UUID.randomUUID();
    }

    public Crime() {
        this("");
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
