package com.LinHao.core.processor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class WatermarkAdder implements ImageProcessor {
    

    private static final int Font_SIZE = 36;
    private static final float OPACITY = 0.5f;
    private static final int GAP_X = 500;
    private static final int GAP_Y = 300;
    private String watermarkText;
    



    public WatermarkAdder(String text) {
        this.watermarkText = text;
    }




    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = watermarked.createGraphics();
        g.drawImage(image, 0, 0, null);

        g.setColor(Color.LIGHT_GRAY);//色
        g.setFont(new Font("Sanserif", Font.BOLD, Font_SIZE));//字体
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, OPACITY));//透明感
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//antialias
        g.rotate(Math.toRadians(-30), image.getWidth() / 2, image.getHeight() / 2);

        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = -width; x < width * 2; x += GAP_X){
            for (int y = -height; y < height * 2; y += GAP_Y){
                g.drawString(watermarkText, x, y);
            }
        }
        

        g.dispose();
        return watermarked;
    }

}
