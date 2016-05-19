package com.complaint.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.WnmsCellDao;
import com.complaint.model.LLOffset;
import com.complaint.model.WnmsCell;

@Service("wnmsService")
public class WnmsCellService {
	@Autowired
	private WnmsCellDao wnmsCellDao;
	private  Map<String, WnmsCell> cellmap = new HashMap<String, WnmsCell>();
	
	public  void initLLMap(){
		int count = wnmsCellDao.countAll();
		int pageSize = 1000;
		int totalPage = count%pageSize ==0 ? count/pageSize : count/pageSize +1;
		Map<String, Object> map = null;
		List<WnmsCell> list = new ArrayList<WnmsCell>();
		for (int i = 1; i <= totalPage; i++) {
			map = new HashMap<String, Object>();
			map.put("lbound", (i-1)*pageSize);
			map.put("mbound", i*pageSize);
			list.addAll(wnmsCellDao.queryByPage(map));
		}
		for (WnmsCell wnmsCell : list) {
			String ll = String.valueOf(wnmsCell.getLac())+"X"+String.valueOf(wnmsCell.getCellId());
			if(!cellmap.containsKey(ll)){
				cellmap.put(ll, wnmsCell);
			}
		}
	}
}
