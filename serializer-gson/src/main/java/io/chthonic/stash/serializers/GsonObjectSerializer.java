package io.chthonic.stash.serializers;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by jhavatar on 3/26/2016.
 */
public class GsonObjectSerializer<T> implements ObjectSerializer<T> {
    private Type type;
    private Gson gson;

    public GsonObjectSerializer(Type type) {
        this.type = type;
    }

    synchronized Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }

        return gson;
    }

    @Override
    public String serialize(T val) {
        return getGson().toJson(val);
    }

    @Override
    public T deserialize(String serialized) throws IOException {
        return getGson().fromJson(serialized, type);
    }
}
