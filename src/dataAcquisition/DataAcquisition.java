package dataAcquisition;

import java.util.List;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 * 
 */

public interface DataAcquisition {

    public DeviceReadingEvent getDatapointRead(DeviceID device);

    public DeviceID[] getAvailableDevices();
    
}
