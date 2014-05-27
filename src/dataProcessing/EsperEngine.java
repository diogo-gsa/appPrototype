package dataProcessing;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import dataAcquisition.DeviceReadingEvent;


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
                  + "FROM DeviceReadingEvent.win:length_batch(3) "
                  + "OUTPUT snapshot every 1 events "
                  + "ORDER BY value desc";
        
        query = engineAdmin.createEPL(eplQueryExpression);
        QueryListener listener = new QueryListener("Q1");
        query.addListener(listener);
    }
    
    public void initMinMaxAvgEnergyConsumptionQuery(){
        
        String eplQueryExpression = 
                    "SELECT min(value) as MIN, max(value) as MAX, avg(value) as AVG "
                +   "FROM DeviceReadingEvent.win:time(15 min) "
                +   "WHERE id = 'LIBRARY' "
                +   "OUTPUT snapshot every 1 events ";
        
        query = engineAdmin.createEPL(eplQueryExpression);
        QueryListener listener = new QueryListener("Q2");
        query.addListener(listener);
    }
    
    public void initThresholdQuery(){
        
        String eplQueryExpression = 
                    "SELECT ts, value, 10 as threshold "
                +   "FROM DeviceReadingEvent.win:length(1) "
                +   "WHERE value > 10 "
                +   "OUTPUT snapshot every 1 events ";
        
        query = engineAdmin.createEPL(eplQueryExpression);
        QueryListener listener = new QueryListener("Q3");
        query.addListener(listener);
    }

}
