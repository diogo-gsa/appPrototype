package dataAcquisition;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 * DeviceConnectivityService.java serves as a binding handle to invoke the
 * eu.smartcampus.api.deviceconnectivity API resources.
 */

public class DeviceConnectivityService {

    private static String remoteAddress;
    private static int remotePort;


    public DeviceConnectivityService(String remoteAddress, int remotePort) {
        DeviceConnectivityService.remoteAddress = remoteAddress;
        DeviceConnectivityService.remotePort = remotePort;
    }

    public String getAllDatapoints() {
        return httpGETrequest("deviceconnectivityapi/datapoints");
    }

    public String getDatapointMetadata(String datapointAddress) {
         return httpGETrequest("deviceconnectivityapi/datapoints/"+datapointAddress+"/metadata");
    }

    public String requestDatapointRead(String datapointAddress) {
        return httpGETrequest("deviceconnectivityapi/datapoints/"+datapointAddress);
    }

    public String requestDatapointWindowRead(String datapointAddress, long startTS, long finishTS) {
        return httpGETrequest("deviceconnectivityapi/datapoints/"+datapointAddress+"/"+startTS+"/"+finishTS);        
    }

    public String requestDatapointWrite(String datapointAddress) {
        // TODO not yet implemented
        throw new UnsupportedOperationException();
    }


    // ---------- Auxiliary methods ----------
    private static String httpGETrequest(String endpoint) {
        String url = "http://" + remoteAddress + ":" + remotePort + "/" + endpoint;
        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}
