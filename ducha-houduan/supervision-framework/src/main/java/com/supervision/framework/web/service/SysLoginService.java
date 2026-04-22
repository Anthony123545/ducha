package com.supervision.framework.web.service;

import javax.annotation.Resource;

import com.supervision.common.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.supervision.common.constant.Constants;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.redis.RedisCache;
import com.supervision.common.exception.ServiceException;
import com.supervision.common.exception.user.CaptchaException;
import com.supervision.common.exception.user.CaptchaExpireException;
import com.supervision.common.exception.user.UserPasswordNotMatchException;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.MessageUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.common.utils.ServletUtils;
import com.supervision.common.utils.ip.IpUtils;
import com.supervision.framework.manager.AsyncManager;
import com.supervision.framework.manager.factory.AsyncFactory;
import com.supervision.system.service.ISysConfigService;
import com.supervision.system.service.ISysUserService;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService extends BaseController {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
        /*if (captchaOnOff)
        {
            validateCaptcha(username, code, uuid);
        }*/
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 3. 【新增】单点登录限制逻辑
        if (!"admin".equals(username)) {
            java.util.Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
            for (String key : keys) {
                try {
                    // 这里不直接写死强转为 LoginUser，先拿 Object
                    Object cacheObj = redisCache.getCacheObject(key);

                    // 严谨判断：只有当它确实是 LoginUser 时才处理
                    if (cacheObj instanceof LoginUser) {
                        LoginUser onlineUser = (LoginUser) cacheObj;
                        if (username.equals(onlineUser.getUsername())) {
                            redisCache.deleteObject(key); // 发现同名，踢掉
                        }
                    }
                    // 如果它变成了 JSONArray 或者其他奇怪的东西（这就是报错的原因）
                    else {
                        // 这个 Key 已经坏了，如果不删掉它，下次登录还会报错。
                        // 我们在这里直接把它清理掉，这叫“自动修复”
                        redisCache.deleteObject(key);
                        System.out.println("自动清理了异常格式的 Token 数据: " + key);
                    }
                } catch (Exception e) {
                    // 万一 getCacheObject 内部反序列化就崩溃了，直接在 catch 里删掉这个坏 key
                    redisCache.deleteObject(key);
                    System.err.println("处理 Token 异常，已强制清理损坏的键: " + key);
                }
            }
        }


        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
