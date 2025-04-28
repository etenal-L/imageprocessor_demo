package com.LinHao.core;



public class Main {
    public static void main(String[] args) {
        BatchProcessor processor = new BatchProcessor();
        processor.processAll("data/input/worksheet.xlsx");
    }
}