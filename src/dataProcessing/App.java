package dataProcessing;

import dataAcquisition.DataAcquisition;
import dataAcquisition.DeviceID;
import dataAcquisition.DeviceReadingEvent;

//import dataAcquisition.DeviceConnectivityService;


public class App {

    public static void main(String[] args){
        //DeviceConnectivityService service = new DeviceConnectivityService("sb-dev.tagus.ist.utl.pt", 8182);       
        DataAcquisition meters = new DataAcquisition();
        DeviceReadingEvent event =  meters.getDatapointRead(DeviceID.LIBRARY);
        System.out.println(event.toString());
    }  
    
}
