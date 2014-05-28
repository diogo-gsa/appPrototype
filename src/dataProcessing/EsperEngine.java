package dataProcessing;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import dataAcquisition.DeviceReadingEvent;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 * 
 */

public class EsperEngine {

    EPServiceProvider   esperEngine;
    EPRuntime           engineRuntime;
    EPAdministrator     engineAdmin;
    EPStatement         query;
    
    public EsperEngine(){
        esperEngine = EPServiceProviderManager.getDefaultProvider();
        engineRuntime = esperEngine.getEPRuntime();
        engineAdmin = esperEngine.getEPAdministrator();            
    }
       
    public void push(DeviceReadingEvent event){
        System.out.println("Input:\t"+event);        
        engineRuntime.sendEvent(event);
    }

    public void initSortEnergyStreamsQuery(){
        
        String eplQueryExpression = 
                    "SELECT id, ts, value "
                  + "FROM dataAcquisition.DeviceReadingEvent.win:length_batch(8) " //TODO dataAcquisition.DeviceReadingEvent, change 8 to 9 
                  + "OUTPUT snapshot every 1 events "
                  + "ORDER BY value desc";
        
        query = engineAdmin.createEPL(eplQueryExpression);
        QueryListener listener = new QueryListener("Q1");
        query.addListener(listener);
    }
    
    public void initMinMaxAvgEnergyConsumptionQuery(){
        
        String eplQueryExpression = 
                    "SELECT min(value) as MIN, max(value) as MAX, avg(value) as AVG "
                +   "FROM dataAcquisition.DeviceReadingEvent.win:time(60 sec) "
                +   "WHERE id = 'LIBRARY' "
                +   "OUTPUT snapshot every 1 events ";
        
        query = engineAdmin.createEPL(eplQueryExpression);
        QueryListener listener = new QueryListener("Q2");
        query.addListener(listener);
    }
    
    public void initThresholdQuery(){
        
//        String eplQueryExpression = 
//                    "SELECT ts, value, 5250 as threshold "
//                +   "FROM dataAcquisition.DeviceReadingEvent.win:length(1) "
//                +   "WHERE value > 5250 AND id = 'LIBRARY' "
//                +   "OUTPUT snapshot every 1 events ";
    
        String eplQueryExpression = 
                "SELECT ts, value, 5380 as threshold "
            +   "FROM dataAcquisition.DeviceReadingEvent.win:time(5 min) "
            +   "WHERE id = 'LIBRARY' "
            +   "GROUP BY value "
            +   "HAVING value > 5380"
            +   "OUTPUT snapshot every 1 events ";
    
        
        
        
        query = engineAdmin.createEPL(eplQueryExpression);
        QueryListener listener = new QueryListener("Q3");
        query.addListener(listener);
    }

}
