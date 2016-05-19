package com.complaint.mina;

import java.io.StringWriter;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.complaint.service.EpinfoService;
import com.complaint.service.Ftp4gService;
import com.complaint.service.FtpService;
import com.complaint.service.KPIColorService;
import com.complaint.service.SceneService;
import com.complaint.service.TestMasterlogService;
import com.complaint.service.WorkOrderService;

public class MinaServerHandler extends IoHandlerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

	@Autowired
	private EpinfoService epinfoService;
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private SceneService sceneService;
	@Autowired
	private TestMasterlogService testMasterlogService;
	@Autowired
	private KPIColorService kPIColorService;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private Ftp4gService ftp4gService;
	//当一个客端端连结进入时
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("client incoming!");
	}
	
	//当一个客户端关闭时
	@Override
	public void sessionClosed(IoSession session) {
		logger.debug("client disconnect");
		session.close(true); 
	}
	
	//当客户端发送的消息到达时:
	@Override
	@SuppressWarnings("unchecked")
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println(session);
		System.out.println(message);
 		boolean isvalid = Boolean.parseBoolean(session.getAttribute("validRequest").toString());
		if(isvalid)
		{
			//解码已经转换为了String
			JSONObject json = (JSONObject)JSONValue.parse(message.toString());
			short commond = Short.parseShort(session.getAttribute("commond").toString());
			logger.debug("received msg:" + message + "     received commond:" + commond);
			switch(commond){
				//登陆验证
				case 1: session.write(this.epinfoService.isValid(json.get("id")!=null?json.get("id").toString():null,json.get("pid")!=null?json.get("pid").toString():null)); break;
				//终端测试数据上报
				case 2: 
					try
					{
						this.testMasterlogService.addTestReport(json); 
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("suc", "t");
						StringWriter out = new StringWriter();
				    	jsonObject.writeJSONString(out);
				    	session.write(out.toString());
					}catch(RuntimeException ex){
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("suc", "f");
						StringWriter out = new StringWriter();
				    	jsonObject.writeJSONString(out);
				    	session.write(out.toString());
						logger.error("终端测试数据上报出错:",ex);
					}
					break;
				//获取工单列表
				case 3: session.write(this.workOrderService.queryWorkOrderForPhoneList(json.get("pid").toString())); break;
				//获取场景信息
				case 4: session.write(this.sceneService.queryAll()); break;
				//获取工单详情
				case 5: session.write(this.workOrderService.queryWorkOrderForDetail(json.get("no").toString(),json.get("id").toString())); break;
				case 6:session.write(this.kPIColorService.queryKpicolor(json));break;
				//版本更新
				case 7:session.write(this.kPIColorService.queryVision(json.get("vi").toString()));break;
				//FTP配置
				case 8:session.write(json.get("nt")!=null && "4".equals(json.get("nt").toString())?
						this.ftp4gService.queryFtp(json.get("vi").toString()):this.ftpService.queryFtp(json.get("vi").toString()));break;
				//获取任务工单
				case 9:session.write(this.workOrderService.queryTaskWorkOrder(json.get("pid").toString()));break;
				//4g工单列表
				case 10: session.write(this.workOrderService.queryLteWorkOrderForPhoneList(json.get("pid").toString())); break;
				//4G版本更新
				case 11:session.write(this.kPIColorService.queryVisionLte(json.get("vi").toString()));break;
			}	
		}else
			session.close(true);
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("exceptionCaught ====>",cause);
    }
	
	@Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.debug("session idle,closed!");
        session.close(true);
    }
}
