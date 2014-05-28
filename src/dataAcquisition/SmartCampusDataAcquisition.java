package dataAcquisition;

import java.util.Map;
import java.util.TreeMap;
//wiki: code.google.com/p/json-simple/
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 * 
 */

public class SmartCampusDataAcquisition implements DataAcquisition {
    
    private DeviceConnectivityService smartcampusREST_API;
    private Map<DeviceID, String> deviceAddress;
    
    
    public SmartCampusDataAcquisition() {
        smartcampusREST_API = new DeviceConnectivityService("sb-dev.tagus.ist.utl.pt", 8182);
        deviceAddress = new TreeMap<DeviceID, String>();
        deviceAddress.put(DeviceID.LIBRARY,           "172.20.70.232");
        deviceAddress.put(DeviceID.DEPARTMENT_14,     "172.20.70.229");
        deviceAddress.put(DeviceID.DEPARTMENT_16,     "172.20.70.238");
        deviceAddress.put(DeviceID.ROOM_1_17,         "172.20.70.234");
        deviceAddress.put(DeviceID.ROOM_1_19,         "172.20.70.235");
        deviceAddress.put(DeviceID.UTA_A4,            "172.20.70.237");
        deviceAddress.put(DeviceID.AMPHITHEATER_A4,   "172.20.70.231");
        //deviceAddress.put(DeviceID.AMPHITHEATER_A5,   "172.20.70.233"); //TODO BUG na API nao mostra este sensor
        deviceAddress.put(DeviceID.LABORATORY_1_58,   "172.20.70.236");
        
    }
    
    public DeviceReadingEvent getDatapointRead(DeviceID device){
        String jsonObj = smartcampusREST_API.requestDatapointRead(deviceAddress.get(device));
        JSONParser parser = new JSONParser();
        DeviceReadingEvent res = null;
        try {
            JSONObject measureJSONobject = (JSONObject) parser.parse(jsonObj);
            String id = device.toString();
            long ts = (long) measureJSONobject.get("timestamp");    
            Double value =  Double.parseDouble((String) measureJSONobject.get("value"));
            res = new DeviceReadingEvent(id, ts, value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public DeviceID[] getAvailableDevices() {
        return DeviceID.values();
    }
}
