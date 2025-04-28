package com.LinHao.core.processor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.font.GlyphVector;
import java.awt.geom.RoundRectangle2D;

public class CaptionDecorator implements ImageProcessor{



    private String productModel;
    private String productName;
    private String brandName;

    private static final Color modelColor = new Color(255, 204,0);
    private static final String fontType = new String("Arial Black");


    private static final int SHOP_FONT_SIZE = 65; //専門のsize
    private static final int NAME_FONT_SIZE = 50; //名称のsize
    private static final int MODEL_FONT_SIZE = 50; //商品のtype


    public CaptionDecorator(String productModel, String productName, String brandName) {
        this.productModel = productModel;
        this.productName = productName;
        this.brandName = brandName;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        // 原

        BufferedImage captioned = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = captioned.createGraphics();
        g.drawImage(image, 0, 0, null);
        // antialiasting
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        //商品のnumber

        g.setFont(new Font(fontType, Font.BOLD, MODEL_FONT_SIZE));
        drawOutlinedText(g, productModel, 30, 60, modelColor, Color.BLACK);

        //商品のtype
        g.setFont(new Font(fontType, Font.PLAIN, NAME_FONT_SIZE));
        drawOutlinedText(g, productName, 30, 60 + MODEL_FONT_SIZE + 10, Color.WHITE, Color.BLACK);
        
        //標識
        g.setFont(new Font(fontType, Font.BOLD, SHOP_FONT_SIZE));
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(brandName);//
        int textHeight = metrics.getHeight();


        int padding = 20;
        int rectWidth = textWidth + padding * 2;
        int rectHeight = textHeight + padding;

        int rectX = image.getWidth() - rectWidth;
        int rectY = image.getHeight() - rectHeight;

        g.setColor(modelColor);
        RoundRectangle2D roundedRect = new RoundRectangle2D.Float(
            rectX, rectY, rectWidth, rectHeight, 20, 20); // 四角形
        g.fill(roundedRect);

        g.setColor(Color.BLACK);
        g.drawString(brandName, rectX + padding, rectY + (rectHeight - textHeight) / 2 + metrics.getAscent());

        g.dispose();
        return captioned;
    }

    private void drawOutlinedText(Graphics2D g, String text, int x, int y, Color fillColor, Color outlineColor){

        GlyphVector glyphVector = g.getFont().createGlyphVector(g.getFontRenderContext(), text);
        Shape textShape = glyphVector.getOutline(x, y);
    
        // 1. 光晕层（发白光）
        for (int i = 12; i >= 2; i -= 2) { 
            g.setColor(new Color(255, 255, 255, 60)); // 白色，透明度30
            g.setStroke(new BasicStroke(i));
            g.draw(textShape);
        }
    
        // 2. 外轮廓（黑色描边）
        g.setColor(outlineColor);
        g.setStroke(new BasicStroke(6f));
        g.draw(textShape);
    
        // 3. 填内部（黄色/白色）
        g.setColor(fillColor);
        g.fill(textShape);
    }
}
