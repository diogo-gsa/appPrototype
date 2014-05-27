package dataProcessing;

import dataAcquisition.DataAcquisition;
import dataAcquisition.DeviceID;
import dataAcquisition.DeviceReadingEvent;

public class App {

    public static void main(String[] args) throws InterruptedException{
        DataAcquisition deviceAPI= new DataAcquisition();
        
//        DeviceReadingEvent event =  deviceAPI.getDatapointRead(DeviceID.LIBRARY);
//        System.out.println(event.toString());

        //-------------------------------------------------------------
        EsperEngine engine = new EsperEngine();
        //engine.initSortEnergyStreamsQuery();
        //engine.initMinMaxAvgEnergyConsumptionQuery();
        engine.initThresholdQuery();
        
        while(true){
           for(DeviceID device : DeviceID.values()){
               DeviceReadingEvent event =  deviceAPI.getDatapointRead(device);               
               engine.push(event);
           }
        
//           Thread.sleep(10000);
        
        }
    }  
    
}
