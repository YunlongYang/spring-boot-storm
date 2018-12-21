package com.github.yunlong.boot.springbootstorm.core.instance.flow;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Flow implements IRichBolt {
    private static final String TAG = "Flow";
    private static Logger logger = LoggerFactory.getLogger(Flow.class);

    private Map conf;
    private TopologyContext context;
    private OutputCollector collector;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.conf = conf;
        this.context = context;
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        if(input.size()>0){
            String data = input.getStringByField("data");
            logger.info("flow data:"+data);
            collector.ack(input);
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
