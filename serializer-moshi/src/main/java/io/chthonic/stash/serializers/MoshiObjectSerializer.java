package io.chthonic.stash.serializers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by jhavatar on 3/26/2016.
 */
public class MoshiObjectSerializer<T> implements ObjectSerializer<T> {
    private JsonAdapter<T> jsonAdapter;
    private final Type type;

    public MoshiObjectSerializer(Type type) {
        this.type = type;
    }

    synchronized JsonAdapter<T> getAdapter() {
        if (jsonAdapter == null) {
            Moshi moshi = new Moshi.Builder().build();
            this.jsonAdapter = moshi.adapter(type);
        }
        return this.jsonAdapter;
    }

    @Override
    public String serialize(T val) {
        JsonAdapter<T> jsonAdapter = getAdapter();
        return jsonAdapter.toJson(val);
    }

    @Override
    public T deserialize(String serialized) throws IOException {
        JsonAdapter<T> jsonAdapter = getAdapter();
        return jsonAdapter.fromJson(serialized);
    }
}
