package com.github.yunlong.boot.springbootstorm.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.yunlong.boot.springbootstorm.core.StormRepository;
import com.rabbitmq.tools.json.JSONUtil;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class StormController {
    @Autowired
    private StormRepository repository;

    @RequestMapping("/start")
    @ResponseBody
    public String start(){
        repository.start();
        return "OK";
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create(String name){
        try {
            return repository.create(name).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/action")
    @ResponseBody
    public String message(String id,String message){
        Map<String,Object> arg = new HashMap<>();
        arg.put("message",message);
        repository.get(id).accept(arg);
        return "OK";
    }
    @RequestMapping("/list")
    @ResponseBody
    public String list(){
        return repository.list().toString();
    }
}
