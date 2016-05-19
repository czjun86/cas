package com.complaint.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.complaint.schedual.context.Context;
import com.complaint.utils.Constant;

/**
 * 终端管理批量导入错误文件删除任务类
 * @author tian.bo
 */
@Service("fileDeleteTaskService")
public class FileDeleteTaskService implements TaskService {
	
	private static Logger log = LoggerFactory.getLogger(FileDeleteTaskService.class);
	
	public Object runTask(Context context) {
		String terminal_error_path = String.format("%s%s", context.getServletContext().getRealPath(""),Constant.CAS_SYSTEM_TERMINAL_ERROR_PATH);
		String gis_template_export_path = String.format("%s%s", context.getServletContext().getRealPath(""),Constant.CAS_GIS_TEMPLATE_EXPORT_PATH);
		String gis_report_export_path = String.format("%s%s", context.getServletContext().getRealPath(""),Constant.CAS_REPORT_TEMPLATE_EXPORT_PATH);
		new Thread(new FileDeleteThread(terminal_error_path)).start();
		new Thread(new FileDeleteThread(gis_template_export_path)).start();
		new Thread(new FileDeleteThread(gis_report_export_path)).start();
		return null;
	}
	
	class FileDeleteThread implements Runnable {
		String path;
		DeleteFileTask task;
		public FileDeleteThread(String path){
			this.path = path;
			task = new DeleteFileTask(path);
		}

		public void run() {
			try {
				if(task != null){
					task.run();
					log.info("Mission success !!");
				}
			} catch (Exception e) {
				log.error(String.format("delete file error.path=%s", path),e);
			}
		}
		
	}

}
