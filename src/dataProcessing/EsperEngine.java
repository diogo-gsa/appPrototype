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
    int                 countInitializedQueries;
    
    public EsperEngine(){
        esperEngine = EPServiceProviderManager.getDefaultProvider();
        engineRuntime = esperEngine.getEPRuntime();
        engineAdmin = esperEngine.getEPAdministrator();
        countInitializedQueries = 0;
    }
       
    public void push(DeviceReadingEvent event){
        if(countInitializedQueries == 0){
            System.out.println("*** There is no initialized queries at the engine ***");
        }
            
        System.out.println("Input:\t"+event);        
        engineRuntime.sendEvent(event);
    }

    public void installSortEnergyStreamsQuery(){
        
        String eplQueryExpression = 
                "SELECT id, ts, value "
              + "FROM dataAcquisition.DeviceReadingEvent.win:length_batch(8) " //TODO dataAcquisition.DeviceReadingEvent, change 8 to 9 
              + "OUTPUT snapshot every 1 events "
              + "ORDER BY value desc";
        
        installQuery(eplQueryExpression, "Q1");
    }
    
    public void installMinMaxAvgEnergyConsumptionQuery(){
        
        String eplQueryExpression = 
                "SELECT min(value) as MIN, max(value) as MAX, avg(value) as AVG "
            +   "FROM dataAcquisition.DeviceReadingEvent.win:time(60 sec) "
            +   "WHERE id = 'LIBRARY' "
            +   "OUTPUT snapshot every 1 events ";
        
        installQuery(eplQueryExpression, "Q2");
    }
    
    public void installThresholdQuery(){
        
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
        
        installQuery(eplQueryExpression, "Q3");
    }
    
    public void installPaperQuery1(){
//      Works Fine!  
//      String eplQueryExpression = 
//              "SELECT avg(value) AS avg, avg(value)*1.20 AS l "
//          +   "FROM dataAcquisition.DeviceReadingEvent.win:length(5) "
//          +   "OUTPUT snapshot every 1 events ";
//      
        
      String eplQueryExpression = 
              "SELECT ts, value "
          +   "FROM dataAcquisition.DeviceReadingEvent.win:length(5) "
          +   "GROUP BY ts, value "
          +   "HAVING value >= ("
          +                     "SELECT avg(value)*1.20 "
          +                     "FROM dataAcquisition.DeviceReadingEvent.win:length(5) "
          +                    ") "
          +   "OUTPUT snapshot every 1 events ";
      
        
        
      installQuery(eplQueryExpression, "Q4");
  }
    
    
    private void installQuery(String eplQuery, String queryId){
        query = engineAdmin.createEPL(eplQuery);
        QueryListener listener = new QueryListener(queryId);
        query.addListener(listener);
        countInitializedQueries++;        
    }

}
