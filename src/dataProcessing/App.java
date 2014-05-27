package dataProcessing;

import com.espertech.esper.epl.core.EngineImportService;

import dataAcquisition.DataAcquisition;
import dataAcquisition.DeviceID;
import dataAcquisition.DeviceReadingEvent;



public class App {

    public static void main(String[] args){
        DataAcquisition deviceAPI= new DataAcquisition();
        
        DeviceReadingEvent event =  deviceAPI.getDatapointRead(DeviceID.LIBRARY);
        System.out.println(event.toString());

        //-------------------------------------------------------------
        EsperEngine engine = new EsperEngine();
        engine.initSortEnergyStreamsQuery();
        //engine.initMinMaxAvgEnergyConsumptionQuery();
        //engine.initThresholdQuery();
        
        while(true){
           for(DeviceID device : DeviceID.values()){
               DeviceReadingEvent newEvent =  deviceAPI.getDatapointRead(device);               
               engine.push(newEvent);
           }
        }
    }  
    
}
