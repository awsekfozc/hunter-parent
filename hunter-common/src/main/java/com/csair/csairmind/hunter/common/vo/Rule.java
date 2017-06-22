package com.csair.csairmind.hunter.common.vo;

import java.io.Serializable;

/**
 * Created by fate
 */
public interface Rule extends Serializable {

    String getUrl();

    Integer getDistinct_type();

    Integer getPipeline_type();
}
