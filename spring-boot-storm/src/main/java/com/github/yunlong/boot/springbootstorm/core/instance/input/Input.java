package com.github.yunlong.boot.springbootstorm.core.instance.input;

import com.github.yunlong.boot.springbootstorm.core.instance.StormGroup;
import com.github.yunlong.boot.springbootstorm.core.instance.StormInstance;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Input implements IRichSpout {

    private static Logger logger = LoggerFactory.getLogger(Input.class);


    private Map conf;
    private TopologyContext context;
    private SpoutOutputCollector collector;
    private BlockingQueue<Map> messageTemp;
    private Fields fields = new Fields("data");

    private Input(){
    }

    public static Input newInstance(){
        return new Input();
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.conf = conf;
        this.context = context;
        this.collector = collector;
        messageTemp = new ArrayBlockingQueue<Map>(100);
        StormGroup.onInputOpen(this,conf);
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        logger.info(ManagementFactory.getRuntimeMXBean().getName()+" nextTuple nowSize:"+messageTemp.size()+" tempTag:"+messageTemp.hashCode()+" inputTag:"+hashCode());
        if(messageTemp.size()==0){
//                collector.emit(new Values());
            logger.info("no emit for empty");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            Values values = null;
            try {
                values = new Values(messageTemp.take().get("message"));
                logger.info("emit " + values.toString());
                collector.emit(values);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void accept(Map arg){
        if (messageTemp==null){

            logger.info(ManagementFactory.getRuntimeMXBean().getName() +"accept new:"+arg.get("message")+" nowSize:null"+" tempTag:null"+" inputTag:"+hashCode());
        }else{
            messageTemp.add(arg);
            logger.info(ManagementFactory.getRuntimeMXBean().getName()+"accept new:"+arg.get("message")+" nowSize:"+messageTemp.size()+" tempTag:"+messageTemp.hashCode()+" inputTag:"+hashCode());
        }

    }

    @Override
    public void ack(Object msgId) {
        logger.info("ack "+msgId);
    }

    @Override
    public void fail(Object msgId) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(fields);
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
