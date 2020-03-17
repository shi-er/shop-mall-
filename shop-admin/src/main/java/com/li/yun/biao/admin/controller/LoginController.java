package com.li.yun.biao.admin.controller;

import com.li.yun.biao.common.annotation.Action;
import com.li.yun.biao.common.annotation.Permission;
import com.li.yun.biao.common.dao.Token;
import com.li.yun.biao.common.utils.IpHelper;
import com.li.yun.biao.common.utils.MD5Util;
import com.li.yun.biao.common.utils.TokenUtil;
import com.li.yun.biao.common.utils.VerifyCodeUtil;
import com.li.yun.biao.user.api.UsUserService;
import com.li.yun.biao.user.model.ShUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Objects;

@Controller
public class LoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private UsUserService usUserService;

    /**
     * 登录界面
     *
     * @param request
     * @return
     */
    @Permission(action = Action.Skip)
    @RequestMapping("/login")
    public String toLogin(HttpServletRequest request) {
        Token token = TokenUtil.getToken(request);
        if (Objects.nonNull(token)) return "redirect:/dashboard";
        return "/login/login";
    }

    /**
     * 注册界面
     *
     * @return
     */
    @RequestMapping("/register")
    public String toRegister() {
        return "/login/register";
    }

    /**
     * 图片验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Permission(action = Action.Skip)
    @RequestMapping("/validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        //设置不缓存图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        //指定生成的响应图片,一定不能缺少这句话,否则错误.
        response.setContentType("image/jpeg");
        BufferedImage image = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB); //创建BufferedImage对象,其作用相当于一图片
        Graphics g = image.getGraphics();     //创建Graphics对象,其作用相当于画笔
        String sRand = VerifyCodeUtil.service(image, g);
        session.setAttribute("code", sRand);
        g.dispose();    //释放g所占用的系统资源
        ImageIO.write(image, "JPEG", response.getOutputStream()); //输出图片
    }

    /**
     * 登陆验证
     *
     * @param code
     * @param mobile
     * @param password
     * @param request
     * @param response
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value = "/validate", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8;", "application/json;"})
    public String validate(String code, String mobile, String password, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("code");
        if (!code.equalsIgnoreCase(sessionCode)) return "验证码输入错误";
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, MD5Util.encode(password));
        if (Objects.nonNull(userInfo)) {
            TokenUtil.setToken(response, new Token(userInfo.getUid().toString(), IpHelper.getIpAddr(request)));
            return "true";
        }
        return "账号或密码不正确！";
    }

    /**
     * 注册验证
     *
     * @param code
     * @param mobile
     * @param password
     * @param request
     * @return
     */
    @Permission(action = Action.Skip)
    @ResponseBody
    @RequestMapping(value = "/register/validate", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8;", "application/json;"})
    public String registerValidate(String code, String mobile, String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("code");
        if (!code.equalsIgnoreCase(sessionCode)) return "验证码输入错误";
        ShUserInfo userInfo = usUserService.getUserInfoByMobile(mobile, null);
        if (Objects.nonNull(userInfo)) return "该账号已注册！请直接登录！";
        ShUserInfo shUserInfo = new ShUserInfo();
        shUserInfo.setPassWord(MD5Util.encode(password));
        shUserInfo.setMobile(mobile);
        shUserInfo.setCreateTime(new Date());
        shUserInfo.setModifyTime(new Date());
        Integer i = usUserService.addUserInfo(shUserInfo);
        if (i > 0) return "true";
        return "注册失败！";
    }

    @Permission(action = Action.Skip)
    @RequestMapping("/login/out")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        TokenUtil.removeToken(request, response);
        return "/login/login";
    }
}
