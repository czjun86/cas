package com.complaint.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * FTP工具类
 * </p>
 * 
 * @author tian.bo
 * @version 1.0
 */
public class FTPUtil {

	private static Logger	logger	= LoggerFactory.getLogger(FTPUtil.class);

	/**
	 * 上传二进制文件到FTP
	 * 
	 * @param ftpDir
	 *            FTP目录
	 * @param fileName
	 *            文件名称
	 * @param content
	 *            要上传的内容
	 * @param ftpBean
	 * @return
	 * @throws Exception
	 */
	public static boolean uploadBinaryFile(String ftpDir, String fileName, InputStream inputStream, FTPBean ftpBean)
			throws Exception {
		FTPClient ftpClient = new FTPClient();
		boolean flag = false;
		try {
			if (!ftpLogin(ftpBean, ftpClient)) {
				logger.info("ftp login fault["+ftpBean.getHost()+","+ftpBean.getPort()+","+ftpBean.getUsername()+","+ftpBean.getPassword()+"]...");
				return false;
			}
			logger.info("ftp login success["+ftpBean.getHost()+","+ftpBean.getPort()+","+ftpBean.getUsername()+","+ftpBean.getPassword()+"]...");
			// 指定本地被动模式
			ftpClient.enterLocalPassiveMode();
			// 指定用户使用的工作目录
			String dirs[] = ftpDir.split("/");
			for (String tempDir : dirs) {
				if (tempDir != null && !"".equals(tempDir)) {
					if (!ftpClient.changeWorkingDirectory(tempDir)) {
						ftpClient.makeDirectory(tempDir);
						ftpClient.changeWorkingDirectory(tempDir);
					}
				}
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 指定上传后文件保存在服务器上的名字
			flag = ftpClient.storeFile(fileName, inputStream);
			if (flag) {
				logger.info("upload file success["+ftpDir+","+fileName+"]...");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception ioe) {
				logger.error(ioe.getMessage());
			}
			ftpLogout(ftpClient);
		}
		return flag;
	}

	/**
	 * 上传普通文件到FTP
	 * 
	 * @param ftpDir
	 * @param fileName
	 * @param file
	 * @param ftpBean
	 * @return
	 * @throws Exception
	 */
	public static boolean uploadFile(String ftpDir, String fileName, File file, FTPBean ftpBean) throws Exception {
		return uploadBinaryFile(ftpDir, fileName, new FileInputStream(file), ftpBean);
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名,当fileName为空的时候,表示现在目录
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downloadFile(FTPBean ftpBean, String remotePath, String fileName, String localPath) {
		// 初始表示下载失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftpClient = new FTPClient();

		try {
			if (!ftpLogin(ftpBean, ftpClient)) {
				logger.info("ftp login fault...");
				return false;
			}
			logger.info("ftp login success...");

			ftpClient.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftpClient.listFiles();
			if (fs != null && fs.length != 0) {
				for (FTPFile ff : fs) {
					if (fileName == null || "".equals(fileName)) {
						// 下载整个目录下面的所有文件和文件夹
						if (ff.isDirectory()) {
							File lo = new File(localPath + File.separator + ff.getName());
							lo.mkdirs();// 本地创建目录

							downloadFile(ftpBean, remotePath + "/" + ff.getName(), fileName, localPath + File.separator
									+ ff.getName());
						} else {
							File localFile = new File(localPath + File.separator + ff.getName());
							FileOutputStream is = new FileOutputStream(localFile);
							ftpClient.retrieveFile(ff.getName(), is);
							is.close();
						}
					}
					if (ff.getName().equals(fileName)) {
						// 下载单个文件
						File localFile = new File(localPath + File.separator + ff.getName());
						FileOutputStream is = new FileOutputStream(localFile);
						ftpClient.retrieveFile(ff.getName(), is);
						is.close();
					}
				}
			}
			// 下载成功
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ftpLogout(ftpClient);
		}
		return success;
	}

	/**
	 * 删除 FTP 上的文件
	 * 
	 * @param fileFtpDir
	 *            文件路径含文件名称
	 * @param ftpBean
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteFile(String fileFtpDir, FTPBean ftpBean) throws Exception {
		boolean flag = false;
		if (null == fileFtpDir || "".equals(fileFtpDir) || null == ftpBean) {
			logger.error("parameter is null or empty.");
			return flag;
		}

		FTPClient ftpClient = new FTPClient();
		// 指定本地被动模式
		ftpClient.enterLocalPassiveMode();
		try {
			if (!ftpLogin(ftpBean, ftpClient)) {
				logger.info("ftp login fail["+ftpBean.getHost()+","+ftpBean.getPort()+","+ftpBean.getUsername()+","+ftpBean.getPassword()+"] ...");
				return false;
			} else {
				logger.info("ftp login success["+ftpBean.getHost()+","+ftpBean.getPort()+","+ftpBean.getUsername()+","+ftpBean.getPassword()+"] ...");
			}

			flag = ftpClient.deleteFile(fileFtpDir);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			ftpLogout(ftpClient);
		}

		return flag;
	}

	/**
	 * 登陆FTP服务器
	 * 
	 * @param ftpBean
	 * @param ftp
	 * @throws SocketException
	 * @throws IOException
	 */
	private static boolean ftpLogin(FTPBean ftpBean, FTPClient ftpClient) throws Exception {
		int loginFlag = 0;
		if(ftpClient==null){
			ftpClient = new FTPClient();
		}
		// 连接FTP服务器
		// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		if (ftpBean != null) {
			ftpClient.connect(ftpBean.getHost(), ftpBean.getPort());
		} else {
			return false;
		}
		// 登录ftp
		ftpClient.login(ftpBean.getUsername(), ftpBean.getPassword());
		// 看返回的值是不是230，如果是，表示登陆成功
		loginFlag = ftpClient.getReplyCode();
		// 以2开头的返回值就会为真
		if (!FTPReply.isPositiveCompletion(loginFlag)) {
			if (ftpClient != null) {
				ftpClient.disconnect();
			}
			return false;
		}
		return true;
	}

	/**
	 * 退出ftp
	 * @param ftpBean
	 * @param ftpClient
	 * @return
	 */
	private static boolean ftpLogout(FTPClient ftpClient) {
		boolean flag = false;
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				logger.info("logout from the ftp server...");
				if (ftpClient.isConnected()) {
					ftpClient.disconnect();
					logger.info("disconnect from the ftp server...");
				}
			}
			flag = true;
		} catch (IOException ioe) {
			logger.error("logout FTP fail!"+ioe.toString());
		}
		return flag;
	}
	/**
	 * 
	 * @param ftpBean  链接ftp配置
	 * @param ftp_type  处理类型，上行为1，下行为2
	 * @param ftp_dir	ftp中路径
	 * @param ftp_name	ftp文件名下载时用，上传时不使用
	 * @return 2ftpbean为空 ，3IP或端口号错误，4用户或密码错误,5表示ftp链接错误 ,6ftp下载路径不存在,7表操作成功,8目录下文件不存在
	 */
	public static Integer FTPUpOrDown(FTPBean ftpBean,Integer ftp_type,String ftp_dir,String ftp_name){
		// 创建FTPClient对象
		FTPClient ftpClient = new FTPClient();
		//用于找到目录后判断是否有这个名字的文件存在
		boolean being = false;
		try {
			//FTP登陆链接
			int login=0;
			int upcount = 0;
			try {
				login = ftpLoginGetError(ftpBean, ftpClient);
			} catch (Exception e) {
				logger.error("login ftp",e);
			}
			if (login!=0) {
				logger.info("ftp login fault...");
				if(login==1){
					//login为1 则ftpbean为空
					return 2;
				}else if(login==2){
					//login为2则IP或端口号错误
					return 3;
				}else if(login==3){
					//login为3则用户或密码错误返回为
					return 4;
				}else if(login==4){
					//login为4则FTP链接超时或发生错误
					return 5;
				}else if(login==5){
					//login为5超时
					return 11;
				}
			}
			logger.info("ftp login success...");
			//下行模式
			//判断目录是否存在
			if(ftp_name.startsWith("/")){
				ftp_name = ftp_name.substring(1);
			}
			String[] file = ftp_name.split("/");
			//下行文件名字
			String fileName = file[file.length-1];
			//下行路径
			String filePath =null;
			if(ftp_name.length()>1&&ftp_name.length()>(fileName.length()+1)){
				filePath= ftp_name.substring(0,ftp_name.length()-fileName.length()-1);
			}
			//转入到指定目录下，转入成功就继续操作，不成功就提示目录不存在
			upcount = file.length-1; 
			boolean bool = ftpClient.changeWorkingDirectory(filePath);
			if(!bool){
				if(filePath.startsWith("/")){
					filePath = filePath.substring(1);
				}
				bool = ftpClient.changeWorkingDirectory(filePath);
			}
			if(bool){
				//获取目录下所有文件
				FTPFile[] fs = ftpClient.listFiles();
				//目录下文件不为空，就循环与传入文件名比对
				if (fs != null && fs.length != 0) {
					for (FTPFile ff : fs) {
						if (fileName != null && !("".equals(fileName))) {
							// 下载整个目录下面的所有文件和文件夹
							if (ff.isDirectory()) {
								continue;
							} else {
								if(fileName.equals(ff.getName())){
									being=true;
								}
							}
						}
					}
				}else{
					//路径下没有文件夹，返回8
					return 8;
				}
			}else{
				//路径不存在就返回
				return 6;
			}
			//判断目录下这个文件时候存在
			if(!being){
				//路径下没有文件夹，返回8
				return 8;
			}
			
			if(upcount > 0){
				for(int i=0; i<upcount; i++){
					ftpClient.cdup();
				}
			}
			//上行模式   根据目录一个文件一个进入，没有就创建
			String dirs[] = ftp_dir.split("/");
			//上传情况下文件不存在就创建
			for (String tempDir : dirs) {
				if (tempDir != null && !"".equals(tempDir)) {
					//ftpClient
					if (!ftpClient.changeWorkingDirectory(tempDir)) {
						//不存在就创建
						ftpClient.makeDirectory(tempDir);
						//创建后转到该目录下
						ftpClient.changeWorkingDirectory(tempDir);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 7;
	}
	
	/**
	 * 登陆FTP验证具体返回错误位置
	 * 
	 * @param ftpBean
	 * @param ftp
	 * @throws SocketException
	 * @throws IOException
	 * @return 成功返回为0，ftpbean 为空返回为1 ，IP或端口号错误返回为 2，用户或密码错误返回为3,FTP链接发生错误为4,5超时
	 */
	private static Integer ftpLoginGetError(FTPBean ftpBean, FTPClient ftpClient) throws Exception {
		int loginFlag = 0;
		if(ftpClient==null){
			ftpClient = new FTPClient();
		}
		// 连接FTP服务器
		// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		if (ftpBean != null) {
			try {
				ftpClient.setConnectTimeout(15000); 
				ftpClient.connect(ftpBean.getHost(), ftpBean.getPort());
			} catch (Exception e) {
				logger.error("ftp connect error",e);
				return 4;
			}
		} else {
			return 1;
		}
		// 登录ftp
		try {
			if(ftpClient.login(ftpBean.getUsername(), ftpBean.getPassword())){
				
			}else{
				return 3;
			}
		} catch (Exception e) {
			logger.error("login ftp",e);
		}
		// 看返回的值是不是230，如果是，表示登陆成功
		loginFlag = ftpClient.getReplyCode();
		// 以2开头的返回值就会为真
		if (!FTPReply.isPositiveCompletion(loginFlag)) {
			if (ftpClient != null) {
				ftpClient.disconnect();
			}
			return 4;
		}
		return 0;
	}
}
