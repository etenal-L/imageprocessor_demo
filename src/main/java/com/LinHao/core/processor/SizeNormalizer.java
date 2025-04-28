package com.LinHao.core.processor;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;


public class SizeNormalizer implements ImageProcessor{
    private static final int STANDARD_SIZE = 800;


    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        if(width > height * 2 || height > width * 2){
            return centerCrop(image);
        } else {
            return addPadding(image);
        }


    }

    private BufferedImage centerCrop(BufferedImage image){
        int cropSize = Math.min(image.getWidth(), image.getWidth());
        int x = (image.getWidth() - cropSize) / 2;
        int y = (image.getHeight() - cropSize) / 2;

        BufferedImage cropped = image.getSubimage(x, y, cropSize, cropSize);

        BufferedImage resized = new BufferedImage(STANDARD_SIZE, STANDARD_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(cropped, 0, 0, STANDARD_SIZE, STANDARD_SIZE, null);
        g.dispose();
        return resized;

    }
    private BufferedImage addPadding(BufferedImage image){
        BufferedImage padded = new BufferedImage(STANDARD_SIZE, STANDARD_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = padded.createGraphics();
        //白い背景
        g.setColor(Color.white);
        g.fillRect(0, 0, STANDARD_SIZE, STANDARD_SIZE);

        //原図を調整する
        int newWidth, newHeight;
        if(image.getWidth() > image.getHeight()){
            newWidth = STANDARD_SIZE;
            newHeight = image.getHeight() * STANDARD_SIZE / image.getWidth();
        } else {
            newHeight = STANDARD_SIZE;
            newWidth = image.getWidth() * STANDARD_SIZE / image.getHeight();
        }

        //中央に置く
        int x = (STANDARD_SIZE - newWidth) / 2;
        int y = (STANDARD_SIZE - newHeight) / 2;
        g.drawImage(image, x, y, newWidth, newHeight, null);
        g.dispose();
        return padded;

    }
}
