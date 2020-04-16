package com.petstore.util;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

public class WebUtil {
    static Logger logger = Logger.getLogger(WebUtil.class);

    public static void reponseToAjax(HttpServletResponse response,String msg,String result){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/xhr;charset=utf-8");
        logger.debug(msg + " result:" + result);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void reponseToJson(HttpServletResponse response, Map map) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSON.toJSONString(map));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 以字符串形式返回生成的验证码，同时输出一个图片
     *
     * @param width
     *            图片的宽度
     * @param height
     *            图片的高度
     * @param imgType
     *            图片的类型
     * @param request
     *
     * @param response
     *
     */
    public static void createVerifyCode(final int width, final int height, final String imgType,HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获得当前请求对应的会话对象
        response.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        OutputStream output=response.getOutputStream();//获得可以向客户端返回图片的输出流，字节流
        //从请求中获得URI(统一资源标识符)
        String uri=request.getRequestURI();
        logger.debug("请求uri:"+uri);
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);//支持图片透明的类型
        Graphics graphic = image.getGraphics();
        graphic.setColor(new Color(255,255,255,0));//设置透明颜色
        graphic.fillRect(0, 0, width, height);

        Color[] colors = new Color[] { Color.BLUE, Color.GRAY, Color.GREEN, Color.RED, Color.BLACK, Color.ORANGE,
                Color.CYAN };
        // 在 "画板"上生成干扰线条 ( 50 是线条个数)
        for (int i = 0; i < 50; i++) {
            graphic.setColor(colors[random.nextInt(colors.length)]);
            final int x = random.nextInt(width);
            final int y = random.nextInt(height);
            final int w = random.nextInt(20);
            final int h = random.nextInt(20);
            final int signA = random.nextBoolean() ? 1 : -1;
            final int signB = random.nextBoolean() ? 1 : -1;
            graphic.drawLine(x, y, x + w * signA, y + h * signB);
        }

        // 在 "画板"上绘制字母
        graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        for (int i = 0; i < 6; i++) {
            final int temp = random.nextInt(26) + 97;
            String s = String.valueOf((char) temp);
            sb.append(s);
            graphic.setColor(colors[random.nextInt(colors.length)]);
            graphic.drawString(s, i * (width / 6), height - (height / 3));
        }
        graphic.dispose();
        try {
            ImageIO.write(image, imgType, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("verifyCode："+sb);
        session.setAttribute(uri, sb);
    }

}
