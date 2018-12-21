package com.github.yunlong.boot.springbootstorm.core.instance;

import com.github.yunlong.boot.springbootstorm.core.instance.flow.Flow;
import com.github.yunlong.boot.springbootstorm.core.instance.input.Input;
import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Map;

public class StormInstance {

    private String name;
    private String id;
    private StormTopology topology;
    private Input input;

    public StormInstance(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "StormInstance{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public StormTopology getTopology() {
        return topology;
    }

    public void onCreate(){
        //定义一个拓扑
        TopologyBuilder builder=new TopologyBuilder();
        //设置一个Executeor(线程)，默认一个
        builder.setSpout("input", Input.newInstance());
        //设置一个Executeor(线程)，和一个task
        builder.setBolt("flow", new Flow(),1).setNumTasks(1).shuffleGrouping("input");
        topology = builder.createTopology();
    }

    public void attach(Input input){
        this.input = input;
    }

    public void onStart(){

    }

    public void accept(Map args){
        input.accept(args);
    }

    public void onStop(){

    }

    public void onDestroy(){

    }
}
