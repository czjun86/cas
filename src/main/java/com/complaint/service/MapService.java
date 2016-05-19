package com.complaint.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.complaint.dao.LLOffsetDao;
import com.complaint.model.LLOffset;
@Service("mapService")
public class MapService {

	@Autowired
	private  LLOffsetDao llOffsetDao;
	
	private  Map<String, LLOffset> llmap = new HashMap<String, LLOffset>();
	public  LLOffset getLLlOffset(BigDecimal lng, BigDecimal lat){
		String ll = String.valueOf(lng).substring(0, 4)+"X"+String.valueOf(lat).substring(0, 4);
		if(CollectionUtils.isEmpty(llmap)){
			initLLMap();
		}
		return llmap.get(ll);
	}
	public  void initLLMap(){
		int count = llOffsetDao.countAll();
		int pageSize = 1000;
		int totalPage = count%pageSize ==0 ? count/pageSize : count/pageSize +1;
		Map<String, Object> map = null;
		List<LLOffset> list = new ArrayList<LLOffset>();
		for (int i = 1; i <= totalPage; i++) {
			map = new HashMap<String, Object>();
			map.put("lbound", (i-1)*pageSize);
			map.put("mbound", i*pageSize);
			list.addAll(llOffsetDao.queryBypage(map));
		}
		for (LLOffset llOffset : list) {
			String ll = String.valueOf(llOffset.getLng().doubleValue())+"X"+String.valueOf(llOffset.getLat().doubleValue());
			if(!llmap.containsKey(ll)){
				llmap.put(ll, llOffset);
			}
		}
	}
}
