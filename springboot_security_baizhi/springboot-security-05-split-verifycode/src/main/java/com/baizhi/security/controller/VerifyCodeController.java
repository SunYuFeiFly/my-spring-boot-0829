package com.baizhi.security.controller;

import com.google.code.kaptcha.Producer;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author syf_12138
 * @Description 验证那生成控制类（前后端系统不能使用ImageIO页面输出，需返回生疮图片的base64格式数据）
 * @Date 2022/8/14 17:03
 **/

@RestController
public class VerifyCodeController {

    private final Producer producer;

    public VerifyCodeController(Producer producer) {
        this.producer = producer;
    }


    @RequestMapping("/vc.jpg")
    public String verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        // 1、生成验证码
        String verifyCode = producer.createText();
        // 2、保存到中 session
        session.setAttribute("kaptcha", verifyCode);
        // 3、生成图片
        BufferedImage image = producer.createImage(verifyCode);
        // 4、返回图片Base64格式信息
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpg", fos);
        return Base64.encodeBase64String(fos.toByteArray());
    }
}
