package com.LinHao.core.processor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

class SizeNormalizerTest {

    @Test
    void testStandardizePhoneImage() {
        // 模擬picture
        BufferedImage phoneImage = new BufferedImage(1080, 1920, BufferedImage.TYPE_INT_RGB);

        // SizeNormalizerをtestする
        SizeNormalizer normalizer = new SizeNormalizer();
        BufferedImage result = normalizer.process(phoneImage);

        // 検査
        assertEquals(800, result.getWidth());
        assertEquals(800, result.getHeight());
    }

    @Test
    void testStandardizeWideImage() {
        // 3000x1500の模擬picture
        BufferedImage wideImage = new BufferedImage(3000, 1500, BufferedImage.TYPE_INT_RGB);

        SizeNormalizer normalizer = new SizeNormalizer();
        BufferedImage result = normalizer.process(wideImage);

        // 検査
        assertEquals(800, result.getWidth());
        assertEquals(800, result.getHeight());
    }
}
