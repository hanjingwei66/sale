package cn.iocoder.yudao.module.hq;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class test {
    public static void main(String[] args) throws Exception {

            //如果未将tessdata放在根目录下需要指定绝对路径
            String path = "C:/Users/hanjingwei/Desktop/";
            ITesseract instance = new Tesseract();
            //设置训练库的位置
            instance.setDatapath("D:/project/hjwsale/sale/yudao-module-hq/yudao-module-hq-biz/src/main/java/cn/iocoder/yudao/module/hq/testdata" );

            //如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中
            // chi_sim ：简体中文， eng    根据需求选择语言库
            instance.setLanguage("chi_sim_vert");

            // 指定识别图片
            File img = new File(path + "123.png");
            long startTime = System.currentTimeMillis();
            String result = instance.doOCR(img);

            // 输出识别结果
            long endTime = System.currentTimeMillis();
            System.out.println("识别结果: \n" + result + "\n 耗时：" + (endTime - startTime) + "ms");


    }
    public static void testZh() throws Exception{
        File imageFile = new File("C:/Users/hanjingwei/Desktop/v2-456a60cc5d44b0356cedb0163cc98cb2_r.jpg");
        BufferedImage image = ImageIO.read(imageFile);
        //对图片进行处理
        image = convertImage(image);
        ITesseract instance = new Tesseract();//JNA Interface Mapping
        String result = instance.doOCR(image); //识别
        System.out.println(result);
    }
    //对图片进行处理 - 提高识别度
    public static BufferedImage convertImage(BufferedImage image) throws Exception {
        //按指定宽高创建一个图像副本
        //image = ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight());
        //图像转换成灰度的简单方法 - 黑白处理
        image = ImageHelper.convertImageToGrayscale(image);
        //图像缩放 - 放大n倍图像
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 3, image.getHeight() * 3);
        return image;
    }
}
