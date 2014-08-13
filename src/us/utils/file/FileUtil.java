package us.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.springframework.web.multipart.MultipartFile;

import us.utils.net.Constants;

public class FileUtil {
	private static final int BUFFER_SIZE = 16 * 1024;

	public static boolean ftpUpload(MultipartFile uploadPicFile, String newFileName, String fileDir) {
		// 上传店铺图片
		FileOutputStream fos = null;
		FileInputStream fis = null;

		FtpUtil fu = new FtpUtil();
		try {
			// 图片存在并且是图片格式
			if (uploadPicFile != null) {
				String finalName = newFileName;
				int uploadFileResult = fu.connect(Constants.FTP_HOSTNAME, Constants.FTP_USERNAME, Constants.FTP_PASSWORD);
				if (uploadFileResult == 1) {
					fu.setTransferType(FTP.BINARY_FILE_TYPE);
					uploadFileResult = fu.uploadFile(uploadPicFile, finalName, fileDir);
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeFtp(fos, fis, fu);
		}
		return true;
	}

	/**
	 * 关闭文件流和ftp连接
	 * 
	 * @param fos
	 * @param fis
	 * @param fu
	 */
	public static void closeFtp(FileOutputStream fos, FileInputStream fis, FtpUtil fu) {
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fu.disconnect();
	}

	/**
	 * 
	 * 上传图片到服务器
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean upload(File src, File dst) {
		boolean suc = true;

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			suc = false;
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				suc = false;
			}
		}
		return suc;
	}

	/**
	 * 复制文件
	 * 
	 * @param inPath
	 * @param outPath
	 */
	public static boolean copy(String inPath, String outPath) {
		boolean suc = false;
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream(inPath)));
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outPath)));
			int i = in.available();
			byte[] b = new byte[i];
			in.readFully(b);
			out.write(b);
			out.flush();
			suc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return suc;
	}

	/**
	 * 删除文件
	 * 
	 * @return
	 */
	public static boolean delete(String filePath) {
		File file = new File(filePath);
		boolean d = false;
		if (file.exists()) {
			if (file.isFile()) { // 判断是否是文件
				d = file.delete();
			}
		}
		return d;
	}

	/**
	 * 删除文件文件夹
	 * 
	 * @return
	 */
	public static boolean deleteDir(String filePath) {
		File file = new File(filePath);
		boolean d = false;
		if (file.exists()) {
			if (file.isFile()) { // 判断是否是文件
				d = file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					delete(files[i].getPath()); // 把每个文件 用这个方法进行迭代
				}
			}
		}
		return d;
	}

	// ---------- 文件常用 方法 ---------- //

	/**
	 * 判断目录是否存在<br>
	 * 存在返回true<br>
	 * 不存在返回false<br>
	 * 
	 * @param fullPath
	 * @return
	 */
	public static boolean existsDir(String fullPath) {
		if (fullPath == null || "".equals(fullPath)) {
			return false;
		}
		File file_dir = new File(fullPath);
		if (!file_dir.exists()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 * @return
	 */
	public static boolean mkDir(String fullPath) {
		if (fullPath == null || "".equals(fullPath)) {
			return false;
		}
		boolean mkdir = false;
		File file_dir = new File(fullPath);
		if (!existsDir(fullPath)) {
			if (file_dir.mkdirs()) {
				mkdir = true;
			}
		}
		return mkdir;
	}

	/**
	 * 获取文件名称带点后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		if (fileName == null || "".equals(fileName)) {
			return "";
		}
		if (fileName.lastIndexOf(".") < 0) {
			return "";
		}
		String extension = null;
		int lastIndexOfDot = fileName.lastIndexOf('.');
		int fileNameLength = fileName.length();
		extension = fileName.substring(lastIndexOfDot, fileNameLength);
		return extension;
	}

	/**
	 * 获取文件名称 带后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFullName(String filePath) {
		if (filePath == null || "".equals(filePath)) {
			return "";
		}
		if (filePath.lastIndexOf("/") < 0) {
			return "";
		}
		String name = null;
		int lastIndexOfLine = filePath.lastIndexOf('/');
		int fileNameLength = filePath.length();
		name = filePath.substring(lastIndexOfLine + 1, fileNameLength);
		return name;
	}

	/**
	 * 获取文件名称 不带后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getName(String filePath) {
		if (filePath == null || "".equals(filePath)) {
			return "";
		}
		if (filePath.lastIndexOf("/") < 0) {
			return "";
		}
		if (filePath.lastIndexOf(".") < 0) {
			return "";
		}
		String name = null;
		int lastIndexOfLine = filePath.lastIndexOf('/');
		int lastIndexOfDot = filePath.lastIndexOf('.');
		name = filePath.substring(lastIndexOfLine + 1, lastIndexOfDot);
		return name;
	}

	/**
	 * 取文件所在文件夹位置
	 * 
	 * @param DicPath
	 * @return
	 */
	public static String getDir(String filePath) {
		if (filePath == null || "".equals(filePath)) {
			return "";
		}
		if (filePath.lastIndexOf("/") < 0) {
			return "";
		}
		int s = filePath.lastIndexOf("/");
		return filePath.substring(0, s);
	}

	public static void main(String[] args) {

	}
}
