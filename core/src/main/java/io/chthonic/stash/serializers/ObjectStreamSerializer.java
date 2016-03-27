package io.chthonic.stash.serializers;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by jhavatar on 3/28/2016.
 */
public class ObjectStreamSerializer<T extends Serializable> implements ObjectSerializer<T> {

    @Override
    public String serialize(T t) {

        ObjectOutputStream so = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            so = new ObjectOutputStream(bo);
            so.writeObject(t);
            so.flush();
            return new String(Base64.encode(bo.toByteArray(), Base64.DEFAULT));

        } catch (Exception e) {
            throw new  RuntimeException(e);

        } finally {
            if (so != null) {
                try {
                    so.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }

    @Override
    public T deserialize(String serialized) throws Exception {

        ObjectInputStream si = null;
        try {
            byte b[] = Base64.decode(serialized.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            si = new ObjectInputStream(bi);
            return (T) si.readObject();

        } catch (Exception e) {
            throw new  RuntimeException(e);

        }  finally {
            if (si != null) {
                try {
                    si.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}
