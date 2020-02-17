package cn.qiandao.shengqianyoudao.realm;

import cn.qiandao.shengqianyoudao.pojo.User;
import cn.qiandao.shengqianyoudao.service.impl.LoginServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author wt
 * @data
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private LoginServiceImpl lsi;

    @Override
    /**
     * 授权
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        //获取当前登录的用户
        System.out.println("开始授权");
        User user = (User) principal.getPrimaryPrincipal();
        //通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加角色
        simpleAuthorizationInfo.addRole(user.getRole());
        //添加权限
        String[] split = user.getPermissions().split(",");
        ArrayList<String> a = null;
        for (int i = 0 ; i < split.length; i++){
            a.add(split[i]);
        }
        simpleAuthorizationInfo.addStringPermissions(a);
        return simpleAuthorizationInfo;
    }

    @Override
    /**
     * 认证
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户输入的账号
        System.out.println("开始");
        String phone = (String)token.getPrincipal();
        //2.通过username从数据库中查找到user实体
        User user = lsi.getUserInfo(phone);
        if(user == null){
            return null;
        }
        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(user,user.getPwd(),getName());
        //4.用户账号状态验证等其他业务操作
        if(user.getState() == null | user.getState()==0){
            throw new AuthenticationException("该账号已经被禁用");
        }
        //5.返回身份处理对象
        return simpleAuthenticationInfo;
    }
}
