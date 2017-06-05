package com.csair.csairmind.hunter.common.plug;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedisService {

    public boolean hset(String key, String value, String value1);

    public String hget(String key, String value);

    public Map<String,String> hgetAll(String key);

    public boolean set(String key, String value);

    public String get(String key);

    public boolean expire(String key, long expire);

    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public Long lpush(String key, String obj);

    public long rpush(String key, Object obj);

    public String lpop(String key);

    public boolean hexists(String key, String mkey);

}