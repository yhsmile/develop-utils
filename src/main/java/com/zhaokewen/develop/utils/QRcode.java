package com.zhaokewen.develop.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRcode {
	
	private static Logger logger = LoggerFactory.getLogger(QRcode.class);

	public static final String QRCODE_DEFAULT_CHARSET = "UTF-8";

	public static final int QRCODE_DEFAULT_HEIGHT = 150;

	public static final int QRCODE_DEFAULT_WIDTH = 150;

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 
	 * @Description:生成没有LOGO图片的二维码（默认宽高为150）
	 * @author yang_hui 
	 * @Created 2017年7月14日
	 * @param data 二维码内容
	 * @return
	 */
	public static BufferedImage createQRCode(String data) {
		return createQRCode(data, QRCODE_DEFAULT_WIDTH, QRCODE_DEFAULT_HEIGHT);
	}

	/**
	 * 
	 * @Description:生成没有LOGO图片的二维码，指定宽高，默认字符编码UTF-8
	 * @author yang_hui 
	 * @Created 2017年7月14日
	 * @param data 二维码内容
	 * @param width 二维码宽度
	 * @param height 二维码高度
	 * @return
	 */
	public static BufferedImage createQRCode(String data, int width, int height) {
		return createQRCode(data, QRCODE_DEFAULT_CHARSET, width, height);
	}

	/**
	 * 
	 * @Description:生成没有LOGO的二维码图片（指定字符编码，图片宽高）
	 * @author yang_hui 
	 * @Created 2017年7月14日
	 * @param data	二维码内容
	 * @param charset 字符编码
	 * @param width	二维码宽度
	 * @param height 二维码高度
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BufferedImage createQRCode(String data, String charset, int width, int height) {
		Map hint = new HashMap();
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hint.put(EncodeHintType.CHARACTER_SET, charset);

		return createQRCode(data, charset, hint, width, height);
	}

	/**
	 * 
	 * @Description:
	 * @author yang_hui 
	 * @Created 2017年7月14日
	 * @param data
	 * @param charset
	 * @param hint
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage createQRCode(String data, String charset, Map<EncodeHintType, ?> hint, int width,
			int height) {
		BitMatrix matrix;
		try {
			matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE,
					width, height, hint);
			return toBufferedImage(matrix);
		} catch (WriterException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 
	 * @Description:生成带LOGO的二维码图片（默认宽高）
	 * @author yang_hui 
	 * @Created 2017年7月14日
	 * @param data 二维码内容
	 * @param logoFile LOGO文件
	 * @param content 二维码下面显示的字符
	 * @return
	 */
	public static BufferedImage createQRCodeWithLogo(String data, File logoFile,String content) {
		return createQRCodeWithLogo(data, QRCODE_DEFAULT_WIDTH, QRCODE_DEFAULT_HEIGHT, logoFile,content);
	}

	/**
	 * 
	 * @Description:生成带LOGO的二维码图片，指定宽高
	 * @author yang_hui 
	 * @Created 2017年7月14日
	 * @param data	二维码内容
	 * @param width	二维码宽度
	 * @param height 二维码高度
	 * @param logoFile LOGO文件
	 * @param content 二维码下面显示的字符
	 * @return
	 */
	public static BufferedImage createQRCodeWithLogo(String data, int width, int height, File logoFile,String content) {
		return createQRCodeWithLogo(data, QRCODE_DEFAULT_CHARSET, width, height, logoFile,content);
	}

	/**
	 * 
	 * @Description:生成带LOGO的二维码图片，指定宽高，指定字符编码
	 * @author yang_hui 
	 * @Created 2017年7月14日
	 * @param data	二维码内容
	 * @param charset 字符编码
	 * @param width	二维码宽度
	 * @param height 二维码高度
	 * @param logoFile LOGO文件
	 * @param content 二维码下面显示的字符
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BufferedImage createQRCodeWithLogo(String data, String charset, int width, int height,
			File logoFile,String content) {
		Map hint = new HashMap();
//		hint.put(EncodeHintType.MARGIN, 5);
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hint.put(EncodeHintType.CHARACTER_SET, charset);

		return createQRCodeWithLogo(data, charset, hint, width, height, logoFile,content);
	}
	public static BufferedImage createQRCodeWithLogo(String data, String charset, Map<EncodeHintType, ?> hint,
			int width, int height, File logoFile,String content) {
		try {
			BufferedImage qrcode = createQRCode(data, charset, hint, width, height);
			BufferedImage logo = ImageIO.read(logoFile);
			int deltaHeight = height - logo.getHeight();
			int deltaWidth = width - logo.getWidth();

			BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			//创建画布图形
			Graphics g = combined.getGraphics();
			//设置画布颜色
			g.setColor(new Color(255,255,255));
			//画二维码
			g.drawImage(qrcode, 0, 0, null); 
			
			System.out.println("二维码图片高度为：" + qrcode.getHeight());
			//画LOGO
			g.drawImage(logo, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
			
			//设置字体
			Font font = new Font("宋体",Font.CENTER_BASELINE,12);
			g.setFont(font);
			g.setColor(Color.RED);  
			int strWidth = g.getFontMetrics().stringWidth(content);
			//设置水平居中居中显示
			g.drawString(content,(int) Math.round((width-strWidth) / 2), height-(height/20));
			g.dispose();
			return combined;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}


	public static String getImageBase64String(BufferedImage image) {
		String result = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			OutputStream b64 = new Base64OutputStream(os);
			ImageIO.write(image, "png", b64);
			result = os.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	public static void convertBase64StringToImage(String base64ImageString, File file) {
		FileOutputStream os;
		try {
			Base64 d = new Base64();
			byte[] bs = d.decode(base64ImageString);
			os = new FileOutputStream(file.getAbsolutePath());
			os.write(bs);
			os.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void main(String[] args) throws Exception {
		
		String data = "http://www.baidu.com";
        File logoFile = new File("D:\\b.jpg");
        String targetDir = "D:\\qrcode\\";
        
        int width = 200;
        int height = 200;
        
        
        String initVal = "086201707141";
        String content = "";
        try {
	        for(int i=0;i<=99;i++){
	        	content = initVal + i;
	        	BufferedImage image = QRcode.createQRCodeWithLogo(data, width, height, logoFile, content);
	        	ImageIO.write(image, "png", new File(targetDir,content + ".png"));
	        	logger.info("create QRcode "+content+".png success");
	        	System.out.println("done");
	        }

			//压缩文件
			String sourceFilePath = targetDir;
			String zipFilePath = targetDir;
			String fileName = "qrcode";
			
			
			ZipFileUtil.fileToZip(sourceFilePath, zipFilePath, fileName);
			
			System.out.println("ZIP done");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
