package com.github.yunlong.boot.springbootstorm.core.instance;

import com.github.yunlong.boot.springbootstorm.core.StormRepository;
import com.github.yunlong.boot.springbootstorm.core.instance.input.Input;

import java.util.Map;

public class StormGroup {

    private static StormRepository sRepository;

    public static void setRepository(StormRepository sRepository) {
        StormGroup.sRepository = sRepository;
    }

    public static void onInputOpen(Input input, Map conf){
        String id = (String)(conf.get("id"));
        sRepository.get(id).attach(input);
    }
}
