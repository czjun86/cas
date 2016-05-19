package com.complaint.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.complaint.utils.Constant;
@Controller
@RequestMapping("/faq")
public class FaqController {
	/**
	 * 下载帮助文档
	 * @param Name
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/faq", method = RequestMethod.GET)
	public  ModelAndView faqdown(HttpServletRequest request ,HttpServletResponse response){
		ModelAndView mv = null;
		String path = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+Constant.CAS_TEMPLATE_PATH+"FAQ.doc";
		ServletOutputStream out = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
		String fileName = "FAQ.doc";//路径加文件名
		fileName = URLEncoder.encode(fileName, "GB2312");
		fileName = URLDecoder.decode(fileName, "ISO8859_1");
		File file = new File(path);
		byte[] buf = new byte[1024];
		int len = 0;
		response.reset(); // 非常重要
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ fileName + "\"");
		out = response.getOutputStream();
		bis = new BufferedInputStream(new FileInputStream(file));
		bos = new BufferedOutputStream(out);

		while (-1 != (len = bis.read(buf, 0, buf.length))) {
			bos.write(buf, 0, len);
		}
		} catch (Exception e) {
		}finally{
			if (bis != null){
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (bos != null){
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
		}
		return mv;
	}
}
