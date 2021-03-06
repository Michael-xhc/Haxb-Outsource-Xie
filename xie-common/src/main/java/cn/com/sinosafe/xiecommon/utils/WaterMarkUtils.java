package cn.com.sinosafe.xiecommon.utils;

//import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 平台拍照助手 添加水印
 * Created by xiehanchun on 2020/7/1
 */
//@Slf4j
public class WaterMarkUtils {
    
    /**
     *  srcImgPath		原图片的路径
     *  tarImgPath		新图片的路径
     *  watermarkContent	水印的内容
     *  color		水印的颜色
     *  font		水印的字体
     * Created by xiehanchun on 2020/7/1
     */
    public static void addWatermark(String srcImgPath,String tarImgPath,String watermarkContent,Color color,Font font) {
        try {
            //获取图片文件
            File srcImgfile = new File(srcImgPath);
            //把文件转换成图片
            Image srcImg = ImageIO.read(srcImgfile);
            //获取图片的宽和高
            int srcImgwidth = srcImg.getWidth(null);
            int srcImgheight = srcImg.getHeight(null);
            //画水印需要一个画板    创建一个画板
            BufferedImage buffImg = new BufferedImage(srcImgwidth,srcImgheight,BufferedImage.TYPE_INT_RGB);
            //创建一个2D的图像
            Graphics2D g = buffImg.createGraphics();
            //画出来
            g.drawImage(srcImg, 0, 0, srcImgwidth, srcImgheight,null);
            //设置水印的颜色
            g.setColor(color);
            //设置水印的字体
            g.setFont(font);
            //设置水印坐标
            int x = srcImgwidth*19/20 -getwaterMarkLength(watermarkContent, g);
            int y = srcImgheight*9/10;
            //根据获取的坐标 在相应的位置画出水印
            g.drawString(watermarkContent, x, y);
            //释放画板的资源
            g.dispose();
            //输出新的图片
            FileOutputStream outputStream = new FileOutputStream(srcImgfile);
            //创建新的图片
            ImageIO.write(buffImg, "jpg", outputStream);
            System.out.println("【平台拍照助手】水印添加完成！");
            //刷新流
            outputStream.flush();
            //关闭流
            outputStream.close();
        } catch (IOException e) {
//            log.info("【平台拍照助手】 图片添加水印失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取水印的坐标
     *  watermarkContent 		水印内容
     *  g		2d图像
     *  @return		水印的长度
     * Created by xiehanchun on 2020/7/1
     */
    public static int getwaterMarkLength(String watermarkContent,Graphics2D g) {
        return	g.getFontMetrics(g.getFont()).charsWidth(watermarkContent.toCharArray(), 0, watermarkContent.length());
    }
}
