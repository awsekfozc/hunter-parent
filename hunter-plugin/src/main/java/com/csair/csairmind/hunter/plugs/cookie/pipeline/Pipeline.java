package com.csair.csairmind.hunter.plugs.cookie.pipeline;

import com.csair.csairmind.hunter.plugs.Plug;

import java.util.Map;

/**
 * Created by fate
 */
public interface Pipeline {
    void save(Map<String,String> dataMap, Plug plug);
}
