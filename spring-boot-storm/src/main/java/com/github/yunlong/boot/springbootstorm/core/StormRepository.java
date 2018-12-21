package com.github.yunlong.boot.springbootstorm.core;

import com.github.yunlong.boot.springbootstorm.core.instance.StormGroup;
import com.github.yunlong.boot.springbootstorm.core.instance.StormInstance;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class StormRepository {

    private static Logger logger = LoggerFactory.getLogger(StormRepository.class);

    private Map<String,StormInstance> instanceMap;
    private LocalCluster cluster;
    @Autowired
    private StormIDProvider idProvider;
    @PostConstruct
    private void init(){
        instanceMap = new HashMap<>();
    }

    public void start() {
        if(cluster==null){
            StormGroup.setRepository(this);
            cluster = new LocalCluster();
            logger.info("start");
        }
    }

    public StormInstance create(String name)throws Exception{
        StormInstance stormInstance = new StormInstance(name,idProvider.get());
        stormInstance.onCreate();
        Map confMap = new HashMap();
        confMap.put("id",stormInstance.getId());
        cluster.submitTopology(name, confMap,stormInstance.getTopology());
        stormInstance.onStart();
        instanceMap.put(stormInstance.getId(),stormInstance);
        logger.info("create name:"+name+" id:"+stormInstance.getId());
        return stormInstance;
    }

    public StormInstance get(String id){
        return instanceMap.get(id);
    }

    public void shutdown() {
        cluster.shutdown();
    }

    public List<StormInstance> list(){
        return new ArrayList<>(instanceMap.values());
    }

}
