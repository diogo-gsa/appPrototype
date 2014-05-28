package dataAcquisition;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class MockObjectDataAcquisition implements DataAcquisition {
    
    private Queue<DeviceReadingEvent> eventsQueue;
    
    public MockObjectDataAcquisition(){
        eventsQueue = new LinkedList<DeviceReadingEvent>();
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 0, 1));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 1, 2));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 2, 3));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 3, 4));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 4, 5));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 5, 1));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 6, 1));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 7, 1));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 8, 7));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 9, 6));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 10, 7));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 11, 12));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 12, 10));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 13, 11));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 14, 91));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 15, 92));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 16, 90));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 17, 10));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 18, 11));
        eventsQueue.add(new DeviceReadingEvent(DeviceID.LIBRARY.toString(), 19, 12));
    }
    
    @Override
    public DeviceReadingEvent getDatapointRead(DeviceID device) {
        return eventsQueue.poll();
    }

    @Override
    public DeviceID[] getAvailableDevices() {
        return DeviceID.values();
    }
    

}
