package us.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

public class FtpUtil {
	/**
	 * 二进制流传送方式
	 */
	public static int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	/**
	 * 字符传送方式
	 */
	public static int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

	private static FtpUtil ftpUtil = null;
	private FTPClient ftpClient = new FTPClient();;

	private String rootPath = "";
	private int transferType = FTP.BINARY_FILE_TYPE;

	/**
	 * 构造器
	 */
	public FtpUtil() {
	}

	/**
	 * 创建ftp连接
	 * 
	 * @param hostName
	 *            ftp服务器主机地址
	 * @param user
	 *            ftp登录用户名
	 * @param password
	 *            ftp登录口令
	 */
	public int connect(String hostName, String user, String password) {
		try {
			ftpClient.connect(hostName);
		} catch (IOException e) {
			System.out.println("ftp服务器->" + hostName + "  无法连接");
			ftpClient = null;
			return -1;
		}

		try {
			if (!ftpClient.login(user, password)) {
				System.out.println("连接ftp->" + hostName + "  用户或密码不正确");
				return -2;
			}
			rootPath = ftpClient.printWorkingDirectory();
			System.out.println("连接ftp->" + hostName + "  成功,rootPath-->" + rootPath);
			return 1;
		} catch (IOException e) {
			System.out.println("ftp服务器->" + hostName + " 无法登陆");
			return -3;
		}
	}

	public boolean createDirectory(String directory) {

		try {
			boolean changeFlag = ftpClient.changeWorkingDirectory(rootPath + directory);
			if (!changeFlag) {
				System.out.println("目录不存在，创建目录：" + rootPath + directory);
				return ftpClient.makeDirectory(rootPath + directory);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int uploadFile(MultipartFile localFile, String remoteFileName, String fileDir) {
		InputStream in = null;
		try {
			createDirectory(fileDir);
			boolean changeFlag = ftpClient.changeWorkingDirectory(rootPath + fileDir);
			if (!changeFlag) {
				System.out.println("远程根目录无法进入");
				return -11;
			}
			if (localFile == null)
				return -12;
			in = localFile.getInputStream();
			ftpClient.enterLocalPassiveMode();
			if (!ftpClient.storeFile(remoteFileName, in)) {
				System.out.println("error-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + fileDir + "上传文件："
						+ remoteFileName + "  失败");
				return -1;
			} else {
				System.out.println("success-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + fileDir + "上传文件："
						+ remoteFileName + "  成功");
			}
			return 1;
		} catch (Exception e) {
			System.out.println("Exception-->error-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + "上传文件："
					+ remoteFileName + "  失败");
			e.printStackTrace();
			return -13;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ioe) {
				System.out.println("关闭本地文件错误");
			}
		}
	}

	public int uploadFileNoPassive(MultipartFile localFile, String remoteFileName, String fileDir) {
		InputStream in = null;
		try {
			createDirectory(fileDir);
			boolean changeFlag = ftpClient.changeWorkingDirectory(rootPath + fileDir);
			if (!changeFlag) {
				System.out.println("远程根目录无法进入");
				return -11;
			}
			if (localFile == null)
				return -12;
			in = localFile.getInputStream();
			if (!ftpClient.storeFile(remoteFileName, in)) {
				System.out.println("error-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + fileDir + "上传文件："
						+ remoteFileName + "  失败");
				return -1;
			} else {
				System.out.println("success-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + fileDir + "上传文件："
						+ remoteFileName + "  成功");
			}
			return 1;
		} catch (Exception e) {
			System.out.println("Exception-->error-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + "上传文件："
					+ remoteFileName + "  失败");
			e.printStackTrace();
			return -13;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ioe) {
				System.out.println("关闭本地文件错误");
			}
		}
	}

	/**
	 * 将本地机器指定的文件以指定的文件名传送到远程机器上，本方法只用于传送一个文件
	 * 
	 * @param localFileName
	 *            本地机器文件名
	 * @param remoteFileName
	 *            远程主机文件名,相对于用户登录路径
	 */
	public int uploadFile(File localFile, String remoteFileName) {
		FileInputStream in = null;
		try {
			boolean changeFlag = ftpClient.changeWorkingDirectory(rootPath);
			if (rootPath != null && (!changeFlag)) {
				System.out.println("远程根目录无法进入");
				return -11;
			}
			in = new FileInputStream(localFile.getAbsolutePath());
			ftpClient.enterLocalPassiveMode();
			if (!ftpClient.storeFile(remoteFileName, in)) {
				System.out.println("error-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + "上传文件："
						+ remoteFileName + "  失败");
			} else {
				System.out.println("success-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + "上传文件："
						+ remoteFileName + "  成功");
			}
			return 1;
		} catch (Exception e) {
			System.out.println("Exception-->error-->向服务器" + ftpClient.getRemoteAddress().getHostName() + rootPath + "上传文件："
					+ remoteFileName + "  失败");
			e.printStackTrace();
			return -13;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ioe) {
				System.out.println("关闭本地文件错误");
			}
		}
	}

	/**
	 * 关闭ftp连接
	 * 
	 * @throws ClassException
	 */
	public void disconnect() {
		try {
			if (ftpClient != null)
				ftpClient.disconnect();
		} catch (Exception e) {
			System.out.println("关闭ftp连接失败");
		}
	}

	/**
	 * 设置ftp文件传输方式
	 * 
	 * @param type
	 *            传输方式，参照FtpClient.BINARY_FILE_TYPE,FtpClient.ASCII_FILE_TYPE
	 */
	public void setTransferType(int type) {
		transferType = type;
		try {
			if (ftpClient != null)
				ftpClient.setFileType(type);
		} catch (IOException ioe) {
			System.out.println("设置传输方式失败");
		}
	}

	/**
	 * 设置ftp文件传输的根路径,当登录用户的默认路径与文件存放的路径不同时调用此方法
	 * 
	 * @param newRootPath
	 *            根路径
	 */
	public void setRootPath(String newRootPath) {
		rootPath = newRootPath;
	}

}
