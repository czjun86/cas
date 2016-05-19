package com.complaint.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.BaseDao;
import com.complaint.dao.EpinfoDao;
import com.complaint.model.Epinfo;
import com.complaint.model.EpinfoArea;
import com.complaint.page.PageBean;

@Service("epinfoService")
public class EpinfoService {
	private static final Logger logger = LoggerFactory.getLogger(EpinfoService.class);
	@Autowired
	private EpinfoDao epinfoDao;
	@Autowired
	private BaseDao baseDao;
	public PageBean getEpinfoList(String uuid, int pageIndex, int pageSize) {
		int lbound = (pageIndex - 1) * pageSize;
		int mbound = pageIndex * pageSize;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uuid", uuid);
		param.put("lbound", lbound);
		param.put("mbound", mbound);
		List<Epinfo> list = this.epinfoDao.queryEpinfoList(param);
		PageBean pb = new PageBean();
		pb.setList(list);
		return pb;
	}

	public PageBean countEqinfos(String uuid, int pageIndex, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uuid", uuid);
		int count = this.epinfoDao.countEpinfo(uuid);
		PageBean pb = new PageBean();
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		pb.setTotalPage(count);
		return pb;
	}
	
	@SuppressWarnings("unchecked")
	public String isValid(String uuid,String phoneNum) throws IOException{
		JSONObject json=new JSONObject();
		Epinfo info = this.epinfoDao.queryUuid(uuid);
		
		if(info == null){
			json.put("suc", "f");
			json.put("msg", 1);
		}else{
			if(info.getIslock() == 1){
				if(phoneNum!=null){
					//用手机号查询出有权限的区域
					List<EpinfoArea> ls = this.epinfoDao.getEpinfoArea(phoneNum);
					if(ls!=null && !ls.isEmpty()){
						json.put("area", ls);
					}
				}
				json.put("suc", "t");
			}else{
				json.put("suc", "f");
				json.put("msg", 2);
			}
		}
		StringWriter out = new StringWriter();
		json.writeJSONString(out);
		return out.toString();
	}
	/**
	 * 判断uuid在数据库是否存在
	 * @param uuid
	 * @return
	 */
	public boolean isExsit(String uuid) {
		Epinfo info = this.epinfoDao.queryUuid(uuid);
		if(info == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 判断数组中数据是否重复
	 * @param uuids
	 * @return
	 */
	private Map<String, Object> isRepeatInArray(String[] uuids,Integer[] areaid,String[] functionary,String[] teltphone,Integer[] islock){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Epinfo> epinfos = getEpinfos(uuids,areaid,functionary,teltphone,islock);
		for (int i = 0; i < epinfos.size(); i++) {
			for (int j = 0; j < epinfos.size(); j++) {
				if(i != j && epinfos.get(i).getUuid().equals(epinfos.get(j).getUuid())){			
					map.put("msg", true);
					map.put("uuid", epinfos.get(j).getUuid());
					return map;
				}
			}
		}
		map.put("msg", false);
		return map;
	}
	
	private List<Epinfo> getEpinfos(String[] uuids,Integer[] areaid,String[] functionary,String[] teltphone,Integer[] islock){
		List<Epinfo> epinfos = new ArrayList<Epinfo>();
		Epinfo epinfo = null;
		for (int i = 0; i < uuids.length; i++) {
			if(StringUtils.isNotEmpty(uuids[i])){
				epinfo = new Epinfo();
				epinfo.setUuid(uuids[i]);
				epinfo.setAreaid(areaid[i]);
				epinfo.setFunctionary(functionary[i]);
				epinfo.setTeltphone(teltphone[i]);
				epinfo.setIslock(islock[i]);
				epinfos.add(epinfo);
			}
		}
		return epinfos;
	}
	public Epinfo getEpinfoById(Integer id) {
		return this.epinfoDao.queryById(id);
	}
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> addEpinfo(String[] uuids,Integer[] areaid,String[] functionary,String[] teltphone,Integer[] islock)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Epinfo> epinfos = getEpinfos(uuids,areaid,functionary,teltphone,islock);
		Map<String, Object> mapTemp = isRepeatInArray(uuids,areaid,functionary,teltphone,islock);
		boolean b = (Boolean) mapTemp.get("msg");
		
		if(!b){
			if(isExsitEpinfo(epinfos).isEmpty()){					
				this.baseDao.batchInsert(EpinfoDao.class, epinfos, 200);
				map.put("msg", 1);
			}else{
				map = isExsitEpinfo(epinfos);
			}
		}else{
			map.put("msg", -2);
			map.put("uuid", mapTemp.get("uuid"));
		}	
		return map;
	}
	
	public Map<String, Object> isExsitEpinfo(List<Epinfo> epinfos){
		Map<String, Object> map = new HashMap<String, Object>();
		if(epinfos!=null){
			for (Epinfo epinfo : epinfos) {
				 if(this.isExsit(epinfo.getUuid())){
					map.put("msg", "-1");
					map.put("uuid", epinfo.getUuid());
					return map;
				 }
			}
		}
		return map;
	}
	@Transactional(rollbackFor=Exception.class)
	public int deleteEpinfo(String ids)throws Exception {
		String[] idStrs = ids.split(",");
		for (String id : idStrs) {
			this.epinfoDao.deleteEpInfo(Integer.parseInt(id));
		}
		return 1;
	}
	@Transactional(rollbackFor=Exception.class)
	public int updateEpinfo(Epinfo epinfo)throws Exception {
		this.epinfoDao.updateEpInfo(epinfo);
		return 1;
	}
}
