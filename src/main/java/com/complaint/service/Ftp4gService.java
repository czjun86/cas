package com.complaint.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.Ftp4gMapper;
import com.complaint.model.TFtpConfig;
import com.complaint.page.PageBean;
import com.complaint.utils.Constant;
import com.complaint.utils.ConstantUtil;
import com.complaint.utils.FTPBean;
import com.complaint.utils.FTPUtil;

@Service("ftp4gService")
public class Ftp4gService {
	private static final Logger logger = LoggerFactory.getLogger(Ftp4gService.class);
	@Autowired
	private Ftp4gMapper ftp4gMapper;
	
	//终端请求返回数据
	public String queryFtp(String vision){
		String rtnMsg="";
		StringWriter out = new StringWriter();
		List<TFtpConfig> tf=this.ftp4gMapper.queryFtp();
		Map map =new HashMap();
		if(tf!=null){
			for(int i=0;i<tf.size();i++){
				tf.get(i).setKey(ConstantUtil.KEY);
			}
			map.put("config", tf);
		}
		map.put("vi", vision);
		try {
			JSONValue.writeJSONString(map, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("redefine attribute name",e);
		}
	    rtnMsg = out.toString();
		return rtnMsg;
	}
	
	/*public TFtpConfig queryFtp2(){
		TFtpConfig tf=new TFtpConfig();
		TFtpConfig tfc = this.ftp4gMapper.queryFtp();
			if(tfc!=null){
				tf = tfc;
			}
		return tf;
	}*/
	
	/**
	 * 验证数据是否正确,并存入
	 * @param tf
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateFtp(TFtpConfig tf,Integer type)throws Exception{
			FTPBean fb = new FTPBean();
			if("172.31.0.43".equals(tf.getIp())){
				fb.setHost("192.168.0.43");
			}else if("172.31.0.37".equals(tf.getIp())){
				fb.setHost("192.168.0.37");
			}else if("172.31.0.36".equals(tf.getIp())){
				fb.setHost("192.168.0.36");
			}else{
				fb.setHost(tf.getIp());
			}
				fb.setPort(tf.getPort());
				fb.setUsername(tf.getUsername());
				fb.setPassword(tf.getPwd());
				//验证地址目录是否可用
			int result = FTPUtil.FTPUpOrDown(fb,tf.getFtp_type(), tf.getFile_dir(), tf.getFile_name());
			if(result==7){//验证通过
				if(tf.getServer_num()!=null&&!"".equals(tf.getServer_num().trim())){//本身输入了编号就直接测试是否存在
					if(!isNum(tf.getServer_num())){//判断为10为以内数字
						return 10;
					}
				}else{//没输入编号就获取一个不与数据库重复的编号
					tf.setServer_num(getNum());//获取10为数字编号
				}
				//tf.setFile_size(String.valueOf(Double.parseDouble(tf.getFile_size())));//文件大小数值转化
				//写入数据库
				if(type == 0){//零为新增
					if(this.ftp4gMapper.getQueryFtpByNum(tf.getServer_num()).size()>0){
						return 9;//编号已存在
					}
					if(tf.getStatus()==1){//判断新数据是否是启用状态，是就要先将数据库所有状态都禁用，在插入
						this.ftp4gMapper.staClose();//关掉所有启动项
					}
					this.ftp4gMapper.insertFtp(tf);
				}else if(type == 1){//1为修改
					if(tf.getStatus()==1){
						this.ftp4gMapper.staClose();//关掉所有启动项
					}
					this.ftp4gMapper.updateFtp(tf);
				}else{
					return 11;
				}
				return type;
			}else{
				return result;
			}
	}
	
	/**
	 * 获取页数
	 */
	public PageBean getPageBean(String name){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		int count = this.ftp4gMapper.countPage(param);
		PageBean pb = new PageBean();
		pb.setTotalPage((count%Constant.PAGESIZE==0)?(count/Constant.PAGESIZE):(count/Constant.PAGESIZE+1));
		return pb;
	}
	/**
	 * 查出所有信息
	 */
	public List<TFtpConfig> getTFtpConfig(String name,Integer pageIndex,Integer pageSize){
		int ln =pageIndex*pageSize;
		int rn = (pageIndex+1)*pageSize;
		Map map = new HashMap();
		map.put("name", name);
		map.put("ln", ln);
		map.put("rn", rn);
		List<TFtpConfig> list = this.ftp4gMapper.getTFtpConfig(map);
		return list;
	}
	/**
	 * 判断为10位内纯数字
	 */
	public boolean isNum(String num){
		boolean flag = true;
		String param = "^[0-9]{1,10}$";
			Pattern pattern = Pattern.compile(param);
		    Matcher  matcher = pattern.matcher(num);
		    if(!matcher.matches()){
		    	flag = false;
		    	return flag;
		    }
	return flag;
	}
	/**
	 * 获取10为随机数
	 */
	public String getNum(){
		String str;//
		List<TFtpConfig> list = new ArrayList<TFtpConfig>();
			do{//获取10位随机数，并判断是否重复直到不重复为止
				StringBuffer strbuf = new StringBuffer();
					for(int i= 0;i<10;i++){
						strbuf.append((int)(10*(Math.random())));
					}
				str = strbuf.toString();
				list = this.ftp4gMapper.getQueryFtpByNum(str);
			}while(list.size()>0);
		return str;
	}
	/**
	 * 修改跳转页面，根据ID查出信息
	 */
	public TFtpConfig searchById(Integer id){
		TFtpConfig tf = this.ftp4gMapper.searchById(id);
		return tf;
	}
	public Integer editFtp(TFtpConfig tf){
		return 7;
	}
	/**
	 * 删除FTP
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer delFtp(String ids)throws Exception{
		String[] idStr = ids.split(",");
		for(String id:idStr){
			this.ftp4gMapper.deleteftp(Integer.parseInt(id));
		}
		return 1;
	}
	/**
	 * 判断是否还没有启动项
	 * return 0为无,1为有
	 */
	public Integer statuNull(){
		int flag = 0;
		List<TFtpConfig> tfc= new ArrayList<TFtpConfig>();
		tfc=this.ftp4gMapper.queryFtp();//判断有没有启用像
		if(tfc!=null && tfc.size()>0){
			flag = 1;
		}
		return flag;
	}
	/**
	 * 启用选中项
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer staUse(Integer id)throws Exception{
		//this.ftp4gMapper.staClose();//关掉所有启动项
		this.ftp4gMapper.staUse(id);//启用这一项
		return 1;
	}
	/**
	 * 禁用选中项
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer staOff(Integer id)throws Exception{
		this.ftp4gMapper.staOff(id);//关掉选中项
		return 1;
		
	}
}
