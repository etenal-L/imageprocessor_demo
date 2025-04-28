package com.LinHao.core.model;

public class ExcelConfig {
    private String number;
    private String productModel;
    private String productName;
    private String brandName;
    private String imagePath;
    private String outputPath;

    public ExcelConfig(String number, String productModel, String productName, String brandName){
        this.number = number;
        this.productModel = productModel;
        this.productName = productName;
        this.brandName = brandName;
        this.imagePath = "data/input/" + number + ".jpg";
        this.outputPath = "data/output/" + number + ".jpg";
    }

    public String getNumber() {
        return number;
    }

    public String getProductModel() {
        return productModel;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getOutputPath() {
        return outputPath;
    }

}
