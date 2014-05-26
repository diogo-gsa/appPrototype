package dataAcquisition;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 * DeviceReadingEvent.java class of events that will feed the Esper Engine
 * with the readings from the DeviceConnectivityService.
 */

public class DeviceReadingEvent{

    private String id;
    private long ts;    
    private double value;    
    
    public DeviceReadingEvent(String id, long ts, double value) {
        this.id = id;
        this.ts = ts;
        this.value = value;        
    }
    
    public double getValue() {
        return value;
    }
    
    public String getID() {
        return id;
    }

    public long getTS() {   
        return ts;
    }
    
    public String toString(){        
//        return "[id:"+value+" | ts:"+ts+" | value:"+id+"]";
        return "[id:"+id+" | ts:"+ts+" | value:"+value+"]";
    }

}
