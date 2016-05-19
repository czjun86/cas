package com.complaint.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.BaseDao;
import com.complaint.dao.TolerantDao;
import com.complaint.model.Tolerante;
import com.complaint.model.WorkOrder;
import com.complaint.page.PageBean;

@Service("tolerantService")
public class TolerantService {
	
	@Autowired
	private TolerantDao dao;
	@Autowired
	private BaseDao baseDao;
	
	public PageBean getTolerantPage(String startTime ,String endTime ,String serialno ,String isTolerant ,String areaids ,Integer pageSize){
		//组装查询条件
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("serialno", serialno);
		map.put("isTolerant", isTolerant);
		//区域有值进行处理
		if(areaids!=null&&!("".equals(areaids))){
			if(areaids.indexOf("-1")<0){
				//选择全部区域就不赋值，没有areaid默认是全部
				//没有选择全部区域，就拆分成list，sql中循环
				List<Integer> areas = new ArrayList<Integer>();
				String[] str = areaids.split(",");
				for(String areaid:str){
					areas.add(Integer.parseInt(areaid));
				}
				map.put("areas", areas);
			}
		}
		//更具条件查询行数
		int count = dao.getTolerantPage(map);
		
		PageBean pb = new PageBean();
		//统计页数，每页个数
		pb.setPageSize(pageSize);
		pb.setPageIndex(1);
		pb.setTotalPage(count);
		return pb;
	}
	
	public List<WorkOrder> getList(String startTime ,String endTime ,String serialno ,String isTolerant ,String areaids ,Integer pageIndex ,Integer pageSize){
		List<WorkOrder> list = new ArrayList<WorkOrder>();
		//组装查询条件
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("serialno", serialno);
		map.put("isTolerant", isTolerant);
		//区域有值进行处理
		if(areaids!=null&&!("".equals(areaids))){
			if(areaids.indexOf("-1")<0){
				//选择全部区域就不赋值，没有areaid默认是全部
				//没有选择全部区域，就拆分成list，sql中循环
				List<Integer> areas = new ArrayList<Integer>();
				String[] str = areaids.split(",");
				for(String areaid:str){
					areas.add(Integer.parseInt(areaid));
				}
				map.put("areas", areas);
			}
		}
		//组装查询页码
		map.put("mbound", (pageIndex+1)*pageSize);
		map.put("lbound", pageIndex*pageSize);
		//查询当页内容
		list = dao.getList(map);
		return list;
	}
	
	/**
	 * 查询容差信息
	 * @param serialno
	 * @param areaId
	 * @return
	 */
	public List<Integer> getTolerantInfo(String serialno , Integer areaId){
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("serialno", serialno);
		map.put("areaId", areaId);
		List<Integer> list = new ArrayList<Integer>();
		//根据工单和区域查询该工单容差信息
		list = dao.getInfo(map);
		
		return list;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Integer getUpdateInfo(String serialno , Integer areaId ,String ids ,Integer userId) throws Exception{
		//删除对应工单原有容差信息
		Map<String ,Object> delmap = new HashMap<String ,Object>();
		delmap.put("serialno", serialno);
		delmap.put("areaId", areaId);
		dao.deleteInfo(delmap);
		
		//添加现在的容差信息
		if(ids!=null&&!("".equals(ids))){//
			String[] str = ids.split(",");
			List<Integer> list = new ArrayList<Integer>();
			if(str.length>0){
				for(String id:str){
					list.add(Integer.parseInt(id));
				}
			}
			//有数据才进行，批量插入
			//组装批量插入内容
			List<Tolerante> tolerantes = new ArrayList<Tolerante>();
			Tolerante tolerante = null;
			for(Integer id : list){
				tolerante = new Tolerante();
				tolerante.setTolerid(id);
				tolerante.setSerialno(serialno);
				tolerante.setAreaId(areaId);
				tolerante.setUserId(userId);
				tolerantes.add(tolerante);
			}
			//进行批量插入
			this.baseDao.batchInsert(TolerantDao.class, tolerantes, 200);
		}
		return 1;
	}
	
}
