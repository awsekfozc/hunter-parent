package com.csair.csairmind.hunter.plugs.proxy;

import com.csair.csairmind.hunter.plugs.Plug;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by fate
 */
public class IpProxyPulg implements Plug {

    private JedisPool pool = null;


    public IpProxyPulg(JedisPool pool) {
        this.pool = pool;
    }
    public IpProxyPulg(String host) {
        pool = new JedisPool(host);
    }

    public IpProxyPulg() {
        pool = new JedisPool("127.0.0.1");
    }

    public String getProxyUserName() {
        Jedis jedis = this.pool.getResource();
        try {
            String host = jedis.hget("r_plugin_config", "ip_proxy_username");
            return host;
        } finally {
            this.pool.returnResource(jedis);
        }
    }

    public String getProxyPassWord() {
        Jedis jedis = this.pool.getResource();
        try {
            String host = jedis.hget("r_plugin_config", "ip_proxy_password");
            return host;
        } finally {
            this.pool.returnResource(jedis);
        }
    }

    public Integer getProxyPort() {
        Jedis jedis = this.pool.getResource();
        try {
            String host = jedis.hget("r_plugin_config", "ip_proxy_port");
            return Integer.parseInt(host);
        } finally {
            this.pool.returnResource(jedis);
        }
    }

    public String getProxyHost() {
        Jedis jedis = this.pool.getResource();
        try {
            String host = jedis.hget("r_plugin_config", "ip_proxy_host");
            return host;
        } finally {
            this.pool.returnResource(jedis);
        }
    }

    @Override
    public String getPlugId() {
        return null;
    }
}