package com.complaint.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/upload")
public class FileUploadController {
	
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest requst){
		ModelAndView mv = new ModelAndView("/upload/index");
		
		return mv;
	}
	
	@RequestMapping(value="/fileupload",method=RequestMethod.POST)
	public ModelAndView fileupload(HttpServletRequest requst){
		ModelAndView mv = new ModelAndView("/upload/index");
		MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)requst;
		List<MultipartFile> files = mreq.getFiles("myfiles");
		try {
			for(MultipartFile file : files){
				if(file.isEmpty()){
					continue;
				}
				double size = file.getSize() / 1024 / 1024;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mv;
	}
	

}
