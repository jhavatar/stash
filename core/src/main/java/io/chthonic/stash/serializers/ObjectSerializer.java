package io.chthonic.stash.serializers;

/**
 * Created by jhavatar on 3/26/2016.
 */
public interface ObjectSerializer<T> {
    String serialize(T t);
    T deserialize(String serialized) throws Exception;
}
