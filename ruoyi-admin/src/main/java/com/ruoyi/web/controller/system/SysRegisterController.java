package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysRegisterService;
import com.ruoyi.system.service.ISysConfigService;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserService userService;

    @PostMapping("/register")
    public AjaxResult register(@Validated @RequestBody SysUser user)
    {
//        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
//        {
//            return error("当前系统没有开启注册功能！");
//        }
        String msg = registerService.register(user);
        SysUser sysUser = userService.selectUserByUserName(user.getUserName());
        if (sysUser != null ) {
            roleService.insertDefaultRole(sysUser.getUserId());
        }
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
