package com.future.recordadmin.config.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.future.recordadmin.common.constants.CommonConstants;
import com.future.recordadmin.sys.sysuser.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userInfo = (String)session.getAttribute(CommonConstants.USER_SESSION_ID);
        if(StrUtil.isNotEmpty(userInfo)){
            SysUser sysUser = JSONUtil.toBean(userInfo,SysUser.class);
            threadLocal.set(sysUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }

    public static SysUser getSysUser() {
        return threadLocal.get();
    }

}
