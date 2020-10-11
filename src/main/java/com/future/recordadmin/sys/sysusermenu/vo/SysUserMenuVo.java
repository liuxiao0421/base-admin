package com.future.recordadmin.sys.sysusermenu.vo;

import com.future.recordadmin.common.pojo.PageCondition;
import com.future.recordadmin.sys.sysmenu.vo.SysMenuVo;
import com.future.recordadmin.sys.sysuser.vo.SysUserVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserMenuVo extends PageCondition implements Serializable {
    private String userMenuId;//用户菜单表id

    private String userId;//用户id

    private String menuId;//菜单id

    private SysUserVo sysUser;//用户

    private SysMenuVo sysMenu;//菜单

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    private String menuIdList;////新增、修改用户菜单时菜单id集合
}
