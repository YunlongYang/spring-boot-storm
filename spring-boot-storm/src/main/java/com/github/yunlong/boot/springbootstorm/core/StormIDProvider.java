package com.github.yunlong.boot.springbootstorm.core;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class StormIDProvider {
    private Set<String> idSet;

    @PostConstruct
    private void init(){
        idSet = new HashSet<>();
    }

    public String get(){
        String id = UUID.randomUUID().toString();
        while (!idSet.add(id)){
            id = UUID.randomUUID().toString();
        }
        return id;
    }

}
