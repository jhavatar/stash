package io.chthonic.stash.javaexample;

import java.io.Serializable;

/**
 * Created by jhavatar on 3/28/2016.
 */
public class Note implements Serializable {
    public String text = "";
    public long timestamp = -1;

    public Note() {
    }

    public Note(String text, long timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }
}
