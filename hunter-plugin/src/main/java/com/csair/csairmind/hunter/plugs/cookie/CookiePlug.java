package com.csair.csairmind.hunter.plugs.cookie;


import com.csair.csairmind.hunter.common.exception.NoGetReadyException;
import com.csair.csairmind.hunter.common.vo.CookiePlugVo;
import com.csair.csairmind.hunter.plugs.Plug;
import com.csair.csairmind.hunter.plugs.cookie.pipeline.Pipeline;
import com.csair.csairmind.hunter.plugs.phantomjs.PhantomjsUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fate
 * cookie插件，生成cookies信息
 */
public class CookiePlug implements Plug {

    //账号列表
    private List<CookiePlugVo> cookiePlugVoList = new ArrayList<>();

    private String plug_id;

    private Pipeline pipeline;

    /***
     * 生成cookies信息
     */
    public void generateCookies() throws Exception {
        if (checkStatus()) {
            for (CookiePlugVo vo : cookiePlugVoList) {
                Map<String, String> map = new PhantomjsUtils().getCookies(vo);
                pipeline.save(map, this);
            }
        } else {
            throw new NoGetReadyException("调用cookeis生成插件出错，插件未准备就绪");
        }
    }

    /***
     * 检查插件状态
     */
    private boolean checkStatus() {
        if (StringUtils.isBlank(plug_id)) return false;
        return true;
    }

    public CookiePlug setAccountVoList(List<CookiePlugVo> cookiePlugVoList) {
        this.cookiePlugVoList = cookiePlugVoList;
        return this;
    }

    public CookiePlug setAccountVo(CookiePlugVo cookiePlugVo) {
        this.cookiePlugVoList.add(cookiePlugVo);
        return this;
    }

    public CookiePlug setAccountVo(String username, String password) {
        CookiePlugVo cookiePlugVo = new CookiePlugVo();
        cookiePlugVo.setUsername(username);
        cookiePlugVo.setPassword(password);
        this.cookiePlugVoList.add(cookiePlugVo);
        return this;
    }

    @Override
    public String getPlugId() {
        return this.plug_id;
    }
}
