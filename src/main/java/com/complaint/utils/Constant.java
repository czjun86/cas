package com.complaint.utils;

public class Constant {

	//新增用户默认密码
	public static String defaultPwd = "123456";
	public static int PAGESIZE = 10;
	//读取消息头偏移量
	public static final int offset = 7;
	//读取消息体长度(含在消息头中的)
	public final static int len = 4;
	//读取消息头长度
	public final static int headLen = 11;
	//数据库int占位符
	public final static int INTPLACEHOLDER = -9999;
	
	public final static int TOKEN_RESUBMIT = -100;
	//GIS导出每次读取行数
	public final static int READ_ROW = 500;
	//GIS报表分页行数
	public final static int SHEET_ROW = 300000;
	//终端管理错误文件存放路径
	public final static String CAS_SYSTEM_TERMINAL_ERROR_PATH = "/template/terminal/error/";
	//gis模块数据导出存放路径
	public final static String CAS_GIS_TEMPLATE_EXPORT_PATH = "/template/gis/export/";
	//工单模块数据导出存放路径
	public final static String CAS_WORKORDER_TEMPLATE_EXPORT_PATH = "/template/workorder/export/";
	//所有模板存放路径
	public final static String CAS_TEMPLATE_PATH = "/template/";
	//报告模块数据导出文件存放路径
	public final static String CAS_REPORT_TEMPLATE_EXPORT_PATH = "/template/report/export/";
	//GIS角度配置
	public final static String CELL_ANGLE_CONFIG="cell_angle_config";
	//中心内部考核报表起始时间设置
	public final static String RP_CENTER_CUMULATIVE_START_TIME ="rp_center_cumulative_start_time";
	//质量报表累计起始时间
	public final static String RP_QUALITY_CUMULATIVE_START_TIME = "rp_quality_cumulative_start_time";
	//及时率间隔时间
	public final static String TIMELYRATE = "timelyrate";
	//累计起始时间
	public final static String REPORTDATE = "reportdate";
	//引用地图类型修改
	public final static String MAPTYPE ="t_map_type";
}
