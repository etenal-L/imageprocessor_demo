import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main{
    //設定パラメータ
    private static final int TARGET_SIZE = 800;
    private static final String WATERMARK_TEXT = "Jin Store";
    private static final Font WATERMARK_FONT = new Font("Microsoft YaHei", Font.BOLD, 24);
    private static final Color WATERMARK_COLOR = new Color(255, 255, 255, 128);//半透明白色
    private static final double ANGLE = Math.toRadians(-30);//傾斜角度

    public static void main(String[] args){
	try{
	    //１画像を読み込み
	    File input = new File("/Users/linhao/ImageProcessor_demo/data/input/product.jpg");
	    BufferedImage origin = ImageIO.read(input);

	    //2 標準化処理
	    BufferedImage standardImage = processImage(origin);

	    //3透かし追加
	    addWatermark(standardImage);

	    //結果保存
	    File outputDir = new File("/Users/linhao/ImageProcessor_demo/data/output");
	    if(!outputDir.exists()) outputDir.mkdirs();

	    ImageIO.write(standardImage, "JPG", new File(outputDir, "processed.jpg"));
	    System.out.println("処理完了");

	}catch(Exception e){
	    System.err.println("処理エラー:" + e.getMessage());
	    e.printStackTrace();
	}
    }

    /**
     * 画像を800x800に標準化
     * origin 元画像
     * return 処理済み画像
     */
    private static BufferedImage processImage(BufferedImage origin){
	//新規キャンバス作成
	BufferedImage canvas = new BufferedImage(TARGET_SIZE, TARGET_SIZE, BufferedImage.TYPE_INT_RGB);
	Graphics2D g = canvas.createGraphics();

	//白背景
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, TARGET_SIZE, TARGET_SIZE);

	//スケーリング比率計算
	double ratio = Math.min(
				(double)TARGET_SIZE / origin.getWidth(),
				(double)TARGET_SIZE / origin.getHeight()
				);
	//画像スケーリング
	int scaledWidth = (int)(origin.getWidth() * ratio);
	int scaledHeight = (int)(origin.getHeight() * ratio);
	Image scaled = origin.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

	//中央配置
	int x = (TARGET_SIZE - scaledWidth) / 2;
	int y = (TARGET_SIZE - scaledHeight) / 2;
	g.drawImage(scaled, x, y , null);
	g.dispose();

	return canvas;
    }

    /**
     *透かし追加処理
     *image 処理対象画像
     */
    private static void addWatermark(BufferedImage image){
	Graphics2D g = image.createGraphics();

	//描画パラメータ設定
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g.setFont(WATERMARK_FONT);
	g.setColor(WATERMARK_COLOR);

	//座標変換(回転＋移動)
	AffineTransform originalTransform = g.getTransform();
	g.rotate(ANGLE, image.getWidth()/2, image.getHeight()/2);

	//透かし位置計算
	FontMetrics fm = g.getFontMetrics();
	int textWidth = fm.stringWidth(WATERMARK_TEXT);
	int x = (image.getWidth() - textWidth) / 2;
	int y = image.getHeight() / 2 + fm.getAscent()/2;

	// 透かし描画
	g.drawString(WATERMARK_TEXT, x, y);

	//座標系をリセット
	g.setTransform(originalTransform);
	g.dispose();
    }
}
