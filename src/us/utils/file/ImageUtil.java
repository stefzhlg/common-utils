package us.utils.file;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import us.utils.date.DateUtil;
import us.utils.string.StringUtil;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片工具类
 * 
 * @author hy.xu
 * 
 */
public class ImageUtil {
	private static final String EXTENSION_MID = "_midd";// 中图文件后缀
	private static final String EXTENSION_SML = "_small";// 大图文件后缀

	private static final String[] PICTURE_EXTENSION = { ".jpg", ".jpeg", ".gif", ".png" };// 图片后缀
	public static final int BIG_WIDTH = 500;// 大图宽度
	public static final int BIG_HEIGHT = 500;// 大图长度
	public static final int MID_WIDTH = 170;// 中图宽度
	public static final int MID_HEIGHT = 120;// 中图长度
	public static final int SML_WIDTH = 100;// 小图宽度
	public static final int SML_HEIGHT = 75;// 小图长度
	public static final float QUALITY = 1.0f;// 清晰度

	/**
	 * 创建小图路径<br>
	 * 
	 * @author hy.xu
	 * @param picPath
	 * @return
	 */
	public static String mkSmallPicPath(String picPath) {
		String path = picPath;
		if (path == null || "".equals(picPath)) {
			return null;
		}
		String extension = FileUtil.getExtension(path);
		String pas = path.substring(0, path.lastIndexOf("."));
		return pas + EXTENSION_SML + extension;
	}

	/**
	 * 创建中图路径<br>
	 * 
	 * @author hy.xu
	 * @param picPath
	 * @return
	 */
	public static String mkMiddlePicPath(String picPath) {
		String path = picPath;
		if (path == null || "".equals(picPath)) {
			return null;
		}
		String extension = FileUtil.getExtension(path);
		String pas = path.substring(0, path.lastIndexOf("."));
		return pas + EXTENSION_MID + extension;
	}

	/**
	 * 创建图片路径<br>
	 * 说明:由静态路径/年/月/日/商品ID/商品评论/ 组成
	 * 
	 * @author hy.xu
	 * @param staticPath
	 * @param strGoodsId
	 * @param strGoodsCommentId
	 * @return
	 */
	public static String mkPicDir(String path) {
		String picPath = "";
		if (path != null) {
			picPath = path + DateUtil.getStringNowTime();
		}
		return picPath;
	}

	/**
	 * 创建图片名称<br>
	 * 说明:由当前时间拼接5位随机数
	 * 
	 * @author hy.xu
	 * @return
	 */
	public static String mkPicName() {
		String picName = null;
		picName = StringUtil.getRandomNumber(5);
		return picName;
	}

	/**
	 * 
	 * 压缩图片方法
	 * 
	 * @author hy.xu
	 * @param oldFile
	 *            将要压缩的图片
	 * @param width
	 *            压缩宽
	 * @param height
	 *            压缩长
	 * @param quality
	 *            压缩清晰度 <b>建议为1.0</b>
	 * @param smallIcon
	 *            压缩图片后,添加的扩展名
	 * @return
	 */
	public static String compress(String oldFile, int width, int height, float quality, String smallIcon) {
		FileOutputStream out = null;
		Image srcFile = null;

		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			File file = new File(oldFile);
			if (!file.exists()) // 文件不存在时
				return null;
			BufferedImage bi = null;
			try {
				bi = ImageIO.read(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bi != null) {
				int old_w = bi.getWidth();
				int old_h = bi.getHeight();
				if (!(old_w > width || old_h > height)) {// 长度或宽度有一个超出指定尺寸
					return oldFile + "不需要压缩";
				}

				srcFile = ImageIO.read(file);
				// 为等比缩放计算输出的图片宽度及高度
				double dbOld_w = ((double) new Integer(old_w).doubleValue());
				double dbOld_h = ((double) new Integer(old_h).doubleValue());
				double rate1 = dbOld_w / (double) width;
				double rate2 = dbOld_h / (double) height;
				double rate = rate1 > rate2 ? rate1 : rate2;
				int new_w = (int) (old_w / rate);
				int new_h = (int) (old_h / rate);
				/** 宽,高设定 */
				BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
				String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
				/** 压缩后的文件名 */
				newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
				/** 压缩之后临时存放位置 */
				out = new FileOutputStream(newImage);

				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				/** 压缩质量 */
				jep.setQuality(quality, true);
				encoder.encode(tag, jep);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (null != srcFile) {
				srcFile.flush();
			}
		}

		return newImage;
	}

	/**
	 * 打水印
	 * 
	 * @author hy.xu
	 * @param targetImg
	 *            将要压缩的图片
	 * @param pressImg
	 *            水印图片路径
	 * @param x
	 * @param y
	 * @param alpha
	 * @return
	 */
	public static String press(String targetImg, String pressImg, float x, float y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			if (x == -1) {// 居中
				x = (width - width_biao) / 2;
			} else if (x > 0 && x < 1) {// 按照百分比计算
				x = width * x;
			}
			if (y == -1) {
				y = (height - height_biao) / 2;
			} else if (y > 0 && y < 1) {
				y = height * y;
			}
			g.drawImage(src_biao, (int) x, (int) y, width_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write(image, "jpg", img);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证是否为图片
	 * 
	 * @author hy.xu
	 * @param pictureName
	 * @return
	 */
	public static boolean isPicture(String pictureName) {
		if (pictureName == null || "".equals(pictureName)) {
			return false;
		}
		for (int i = 0; i < PICTURE_EXTENSION.length; i++) {
			if (PICTURE_EXTENSION[i].equalsIgnoreCase(FileUtil.getExtension(pictureName))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取颜色
	 * 
	 * @author hy.xu
	 * @param lower
	 * @param upper
	 * @return
	 */
	private static Color getRandColor(int lower, int upper) {
		Random random = new Random();
		if (upper > 255)
			upper = 255;
		if (upper < 1)
			upper = 1;
		if (lower < 1)
			lower = 1;
		if (lower > 255)
			lower = 255;
		int r = lower + random.nextInt(upper - lower);
		int g = lower + random.nextInt(upper - lower);
		int b = lower + random.nextInt(upper - lower);
		return new Color(r, g, b);
	}

	/**
	 * 生成图片验证
	 * 
	 * @author hy.xu
	 * @param request
	 * @param response
	 * @param width
	 * @param height
	 * @param length
	 */
	public static void generateImage(HttpServletRequest request, HttpServletResponse response, int width, int height,
			String randomString) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();// 创建一个随机类
		g.setColor(getRandColor(200, 250));// 背景颜色要偏淡
		g.fillRect(0, 0, width, height);// 画背景
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20));// 设置字体//
		// 创建字体，字体的大小应该根据图片的高度来定。
		g.setColor(getRandColor(0, 255));// 边框颜色
		g.drawRect(0, 0, width - 1, height - 1);// 画边框
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		char[] sRandArry = randomString.toCharArray();

		// 用随机产生的颜色将验证码绘制到图像中。
		// 生成随机颜色(因为是做前景，所以偏深)
		// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		for (int i = 0; i < sRandArry.length; i++) {
			String ch = String.valueOf(sRandArry[i]);
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(ch, 13 * i + 6, 16); // g.drawString(rand,13*i+3,14);
		}
		g.dispose();// 图像生效
		// 禁止图像缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 创建二进制的输出流
		ServletOutputStream sos;
		try {
			// 将图像输出到Servlet输出流中。
			sos = response.getOutputStream();
			ImageIO.write(image, "jpeg", sos);
			sos.flush();
			sos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(isPicture(".1jpg"));
	}
}
