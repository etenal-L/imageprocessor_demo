# 画像バッチ処理デモプロジェクト (Image Processor Demo)

このプロジェクトは、Javaを使用して開発したバッチ型画像処理ツールです。  
就職活動において、コーディングスキル・モジュール設計力・システム統合力を示すためのデモンストレーション目的で作成しました。

## プロジェクト概要
- Excel設定ファイルから画像情報を読み込み
- 画像サイズを統一（中央トリミングまたは白背景補完による800x800ピクセル標準化）
- 斜め方向に透かし文字（ウォーターマーク）を追加
- 製品モデル名・製品名・ブランド名のキャプションを追加
- 複数画像を一括で自動処理

## 主な機能
- **Excel駆動設定** (型番、製品名、ブランド名など)
- **標準サイズ統一** (自動トリミングまたはパディング)
- **透かし文字追加** (一定間隔で斜め配置)
- **キャプション挿入** (上部・下部に製品情報を表示)

## 使用方法
1. 元画像を `data/input/` フォルダに保存
2. `worksheet.xlsx` というExcelファイルを作成し、以下のカラムを設定
   | カラム名 | 説明 |
   |:--------|:-----|
   | Number | 入力画像ファイル名（拡張子なし） |
   | Product Model | 表示する製品モデル名 |
   | Product Name | 表示する製品名 |
   | Brand Name | 右下角に表示するブランド名 |
3. 以下のコマンドを実行
   ```bash
   mvn clean package
   java -jar target/image-processor-1.0-SNAPSHOT-jar-with-dependencies.jar

   ```

## システム構成図 (UML Diagram)
```mermaid
classDiagram
    class ImageProcessor {
        <<interface>>
        +process(BufferedImage): BufferedImage
    }
    class SizeNormalizer {
        +STANDARD_SIZE: int
        +process(BufferedImage): BufferedImage
    }
    class WatermarkAdder {
        -watermarkText: String
        +process(BufferedImage): BufferedImage
    }
    class CaptionDecorator {
        -productModel: String
        -productName: String
        -brandName: String
        +process(BufferedImage): BufferedImage
    }
    class BatchProcessor {
        +processAll(String excelPath)
    }
    class ExcelConfig {
        -number: String
        -productModel: String
        -productName: String
        -brandName: String
        -imagePath: String
        -outputPath: String
        +getNumber()
        +getProductModel()
        +getProductName()
        +getBrandName()
        +getImagePath()
        +getOutputPath()
    }
    class ExcelReader {
        +readExcel(String excelPath): List~ExcelConfig~
    }
    class Main {
        +main(String[] args)
    }

    ImageProcessor <|-- SizeNormalizer
    ImageProcessor <|-- WatermarkAdder
    ImageProcessor <|-- CaptionDecorator

    BatchProcessor --> ExcelReader
    BatchProcessor --> ExcelConfig
    BatchProcessor --> ImageProcessor

    Main --> BatchProcessor
```
## 処理前・処理後 (Before and After)

| 処理前 (Before) | 処理後 (After) |
|:---------------:|:--------------:|
| ![before]([before.jpg]([https://github.com/user-attachments/assets/7a377fbd-baa0-4418-a4c0-a9ab04635d57](https://private-user-images.githubusercontent.com/176267406/438457705-7a377fbd-baa0-4418-a4c0-a9ab04635d57.jpg?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDU4Nzg4MzEsIm5iZiI6MTc0NTg3ODUzMSwicGF0aCI6Ii8xNzYyNjc0MDYvNDM4NDU3NzA1LTdhMzc3ZmJkLWJhYTAtNDQxOC1hNGMwLWE5YWIwNDYzNWQ1Ny5qcGc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwNDI4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDQyOFQyMjE1MzFaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT04ZGJjN2IyYzIxNTJmNDI3ODM4Zjk2OWNjZjhkOGZiZTZlNTA4YWYwYTFhMjkxYjgzYjlhMTJhNmQwNjNiYzFlJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.Gq_2DSe9goRjuzZ4cv0_Lo3KJPRefTOlUYvOvU5reJU))) | ![after]([after.jpg]([https://github.com/user-attachments/assets/d80c7836-b40d-4053-9c08-80cfbad11f42](https://private-user-images.githubusercontent.com/176267406/438457763-d80c7836-b40d-4053-9c08-80cfbad11f42.jpg?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDU4NzkwMzEsIm5iZiI6MTc0NTg3ODczMSwicGF0aCI6Ii8xNzYyNjc0MDYvNDM4NDU3NzYzLWQ4MGM3ODM2LWI0MGQtNDA1My05YzA4LTgwY2ZiYWQxMWY0Mi5qcGc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwNDI4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDQyOFQyMjE4NTFaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1iZDY1MGRhODQwYzBiMDI1YTBkNGFiYWFiNWQxMDE2YmVmOWZjNWM5OWE1NmIwMDYzNzRiN2JiMzU1ZjkyNzdiJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.wZAOZ2IG1kS5IOQHJUbuDJ901rWTiTf7vGmszewUn0Q))) |


# Image Processor Demo

This project is a Java-based batch image processing tool designed for demonstration purposes in job applications.

## Project Overview
- Reads image processing configurations from an Excel file.
- Standardizes image sizes (center crop or white padding to 800x800px).
- Adds diagonal watermark text.
- Adds product model and brand captions.
- Batch processes multiple images automatically.

## Features
- **Excel-driven configuration** (model name, product name, brand name, etc.)
- **Automatic image resizing** based on standard size.
- **Watermarking** with adjustable interval and opacity.
- **Caption decoration** for product information.

## How to Use
1. Place original images into `data/input/`.
2. Prepare an Excel file (`worksheet.xlsx`) with the following columns:
   | Column | Description |
   |:------|:------------|
   | Number | Image file name (without extension) |
   | Product Model | Product model to display |
   | Product Name | Product name to display |
   | Brand Name | Brand caption for bottom-right |
3. Execute the following command:
   ```bash
   mvn clean package
   java -jar target/image-processor-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```
