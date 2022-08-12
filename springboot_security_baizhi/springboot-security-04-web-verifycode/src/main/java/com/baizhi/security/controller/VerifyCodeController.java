package com.baizhi.security.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author syf_12138
 * @Description 图形验证码控制类
 * @create 2022/8/12 15:59
 */

@Controller
public class VerifyCodeController {

    private final Producer producer;

    public VerifyCodeController(Producer producer) {
        this.producer = producer;
    }

    @RequestMapping("/vc.jpg")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        // 1、生成验证码
        String verifyCode = producer.createText();
        // 2、保存到中 session
        session.setAttribute("kaptcha", verifyCode);
        // 3、生成图片
        BufferedImage image = producer.createImage(verifyCode);
        // 4、响应图片
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);

    }
}
