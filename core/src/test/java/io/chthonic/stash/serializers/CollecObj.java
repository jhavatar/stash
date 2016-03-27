package io.chthonic.stash.serializers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jhavatar on 3/26/2016.
 */
public class CollecObj  implements Serializable {
    List<String> list = new ArrayList<String>();
    Map<Integer, String> map = new HashMap<Integer, String>();

    public CollecObj() {
        list.add("a");
        list.add("b");

        map.put(0, "a");
        map.put(1, "b");
    }
}
