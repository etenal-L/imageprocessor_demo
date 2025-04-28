package com.LinHao.core;

import com.LinHao.core.model.ExcelConfig;
import com.LinHao.core.processor.*;
import com.LinHao.core.util.ExcelReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BatchProcessor {
    public void processAll(String excelPath){
        try{
            List<ExcelConfig> configs = ExcelReader.readExcel(excelPath);

            //output
            File outputDir = new File("data/output/");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            //全て処理する
            for (ExcelConfig config : configs) {
                try{

                    System.out.println("処理開始：" + config.getNumber());

                    BufferedImage inputImage = ImageIO.read(new File(config.getImagePath()));

                    //処理器
                    ImageProcessor normalizer = new SizeNormalizer();
                    ImageProcessor watermarkAdder = new WatermarkAdder("test store");
                    ImageProcessor captionDecorator = new CaptionDecorator(
                        config.getProductModel(), 
                        config.getProductName(),
                        config.getBrandName()
                        );
                        //標準化
                        BufferedImage normalizedImage = normalizer.process(inputImage);
                        //watermark
                        BufferedImage watermarkImage = watermarkAdder.process(normalizedImage);
                        //captiondecorator
                        BufferedImage captionImage = captionDecorator.process(watermarkImage);
                        
  
                        ImageIO.write(captionImage, "jpg", new File(config.getOutputPath()));
    
                         //output
                        System.out.println(config.getNumber() + "が処理完了しました");            
                } catch (IOException e){
                    System.err.println("処理失敗：" + config.getNumber());
                    e.printStackTrace();
                }
            }
            System.out.println("全部処理した");
        } catch (Exception e){
            System.err.println("全部が失敗した");
            e.printStackTrace();
        }
    }
}
