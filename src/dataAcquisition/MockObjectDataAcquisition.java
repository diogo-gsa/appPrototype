package dataAcquisition;

import java.util.List;

public class MockObjectDataAcquisition implements DataAcquisition {
    
    @Override
    public DeviceReadingEvent getDatapointRead(DeviceID device) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeviceID[] getAvailableDevices() {
        return DeviceID.values();
    }

}
