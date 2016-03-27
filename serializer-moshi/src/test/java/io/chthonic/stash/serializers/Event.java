package io.chthonic.stash.serializers;

/**
 * Created by jhavatar on 3/26/2016.
 */
public class Event {
    String title;
    long datetime;
    int id;
    Type type;
    float dim1;
    double dim2;
    boolean critical;

    enum Type {
        FUS, ROH, DAH;
    }


    public Event(String title, long datetime, int id, Type type, float dim1, double dim2, boolean critical) {
        this.title = title;
        this.datetime = datetime;
        this.id = id;
        this.type = type;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.critical = critical;
    }
}
