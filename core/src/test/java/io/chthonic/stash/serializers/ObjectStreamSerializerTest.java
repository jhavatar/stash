package io.chthonic.stash.serializers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jhavatar on 3/26/2016.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ObjectStreamSerializerTest {

    @Before
    public void setUp() throws Exception {
        ShadowLog.stream = System.out;
        //you other setup here
    }

    @Test
    public void testSimpleObject() throws Exception {
        long datetime = System.currentTimeMillis();
        Event event = new Event("foo", datetime, 12, Event.Type.DAH, 1.01f, 2.02d, true);
        ObjectSerializer<Event> serializer = new ObjectStreamSerializer<Event>();
        String serialized = serializer.serialize(event);
        ShadowLog.d("testSimpleObject", "serialized = " + serialized);
        Event event2 = serializer.deserialize(serialized);

        assertEquals(event2.title, "foo");
        assertEquals(event2.id, 12);
        assertEquals(event2.datetime, datetime);
        assertEquals(event2.type, Event.Type.DAH);
        assertEquals(event2.dim1, event.dim1, 0.01);
        assertEquals(event2.dim2, event.dim2, 0.01);
        assertTrue(event2.critical);
    }


    @Test
    public void testObjectWithCollections() throws Exception {
        CollecObj c = new CollecObj();

        ObjectSerializer<CollecObj> serializer = new ObjectStreamSerializer<CollecObj>();
        String serialized = serializer.serialize(c);

        CollecObj c2 = serializer.deserialize(serialized);
        assertEquals(c2.list.size(), 2);
        assertEquals(c2.list.get(0), "a");
        assertEquals(c2.map.size(), 2);
        assertEquals(c2.map.get(1), "b");
    }


    @Test
    public void testListOfObject() throws Exception {
        long datetime = System.currentTimeMillis();
        Event event1 = new Event("foo", datetime, 1, Event.Type.FUS, 1.01f, 2.02d, true);
        Event event2 = new Event("bar", datetime, 2, Event.Type.ROH, 3.01f, 4.02d, false);
        List<Event> events = new ArrayList<Event>();
        events.add(event1);
        events.add(event2);

        ObjectSerializer<ArrayList<Event>> serializer = new ObjectStreamSerializer<ArrayList<Event>>();
        String serialized = serializer.serialize((ArrayList) events);
        ShadowLog.d("testListObject", "serialized = " + serialized);
        List<Event> events2 = serializer.deserialize(serialized);

        assertEquals(events2.size(), 2);
        Event eventa = events2.get(0);
        Event eventb = events2.get(1);

        assertEquals(eventa.title, "foo");
        assertEquals(eventa.id, 1);
        assertEquals(eventa.datetime, datetime);
        assertEquals(eventa.type, Event.Type.FUS);
        assertEquals(eventa.dim1, event1.dim1, 0.01);
        assertEquals(eventa.dim2, event1.dim2, 0.01);
        assertTrue(eventa.critical);

        assertEquals(eventb.title, "bar");
        assertEquals(eventb.id, 2);
        assertEquals(eventb.datetime, datetime);
        assertEquals(eventb.type, Event.Type.ROH);
        assertEquals(eventb.dim1, event2.dim1, 0.01);
        assertEquals(eventb.dim2, event2.dim2, 0.01);
        assertFalse(eventb.critical);
    }


    @Test
    public void testMapOfObject() throws Exception {
        long datetime = System.currentTimeMillis();
        Event event1 = new Event("foo", datetime, 1, Event.Type.FUS, 1.01f, 2.02d, true);
        Event event2 = new Event("bar", datetime, 2, Event.Type.ROH, 3.01f, 4.02d, false);
        Map<String, Event> eventMap = new HashMap<String, Event>();
        eventMap.put(event1.title, event1);
        eventMap.put(event2.title, event2);

        ObjectSerializer<HashMap<String, Event>> serializer = new ObjectStreamSerializer<HashMap<String, Event>>();
        String serialized = serializer.serialize((HashMap) eventMap);
        ShadowLog.d("testMapOfObject", "serialized = " + serialized);
        Map<String, Event> eventMap2 = serializer.deserialize(serialized);

        assertEquals(eventMap2.size(), 2);
        Event eventa = eventMap2.get("foo");
        Event eventb = eventMap2.get("bar");

        assertEquals(eventa.title, "foo");
        assertEquals(eventa.id, 1);
        assertEquals(eventa.datetime, datetime);
        assertEquals(eventa.type, Event.Type.FUS);
        assertEquals(eventa.dim1, event1.dim1, 0.01);
        assertEquals(eventa.dim2, event1.dim2, 0.01);
        assertTrue(eventa.critical);

        assertEquals(eventb.title, "bar");
        assertEquals(eventb.id, 2);
        assertEquals(eventb.datetime, datetime);
        assertEquals(eventb.type, Event.Type.ROH);
        assertEquals(eventb.dim1, event2.dim1, 0.01);
        assertEquals(eventb.dim2, event2.dim2, 0.01);
        assertFalse(eventb.critical);
    }
}
