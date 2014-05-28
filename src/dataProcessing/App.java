package dataProcessing;

import dataAcquisition.DataAcquisition;
import dataAcquisition.MockObjectDataAcquisition;
import dataAcquisition.DeviceID;
import dataAcquisition.DeviceReadingEvent;
import dataAcquisition.SmartCampusDataAcquisition;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 */

public class App {

    private static boolean DEBUG_MODE = false;


    public static void main(String[] args) throws InterruptedException {

        DataAcquisition deviceAPI;

        if (DEBUG_MODE == false) {
            deviceAPI = new SmartCampusDataAcquisition();
        } else {
            deviceAPI = new MockObjectDataAcquisition();
        }

        //-------------------------------------------------------------
        EsperEngine engine = new EsperEngine();
        //        engine.initSortEnergyStreamsQuery();
        //        engine.initMinMaxAvgEnergyConsumptionQuery();
        //        engine.initThresholdQuery();

        while (true) {
            for (DeviceID device : deviceAPI.getAvailableDevices()) {
                DeviceReadingEvent event = deviceAPI.getDatapointRead(device);
                engine.push(event);
            }

            Thread.sleep(10000);

        }
    }

}
