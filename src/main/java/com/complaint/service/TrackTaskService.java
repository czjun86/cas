package com.complaint.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.TrackTaskDao;
import com.complaint.model.CenterZoom;
import com.complaint.model.LogSubmanualGsm;
import com.complaint.model.TrackCell;
import com.complaint.model.TrackPoint;
import com.complaint.model.WcdmsTrackLog;

@Service("trackTaskService")
public class TrackTaskService {
	@Autowired
	private TrackTaskDao trackTaskDao;
	
	/***
	 * 查询WCDMA日志服务信息
	 * @param map 流水号
	 * @return
	 */
    public List<WcdmsTrackLog> queryWcdma(String id,String areaid)
    {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("areaid",areaid);
		return this.trackTaskDao.queryWcdma(map);
    }
    /***
	 * 查询GSM日志服务信息
	 * @param map 流水号
	 * @return
	 */
    public List<LogSubmanualGsm> queryGsm(String id,String areaid)
    {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("areaid",areaid);
		return this.trackTaskDao.queryGsm(map);
    }
	
    /**
     * 查询轨迹图小区数据*/
   public List<TrackCell> queryCell(String flowid,String areaid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowid",flowid);
		map.put("areaid",areaid);
	   return this.trackTaskDao.queryCell(map);
   }
   /**
    * 查询轨迹图点数据3g*/
   public List<TrackPoint> queryPointWcdma(String flowid,String areaid){
	Map<String, Object> map = new HashMap<String, Object>();
	String[] flowids = flowid.split(",");
	List<String> idlist = Arrays.asList(flowids);
	map.put("flowid", idlist);
	map.put("areaid",areaid);
	return this.trackTaskDao.queryPointWcdma(map);
	   
   }
   
   /**
    * 查询轨迹图点数据2g*/
   public List<TrackPoint> queryPointGsm(String flowid,String areaid){
	Map<String, Object> map = new HashMap<String, Object>();
	String[] flowids = flowid.split(",");
	List<String> idlist = Arrays.asList(flowids);
	map.put("flowid", idlist);
	map.put("areaid",areaid);
	return this.trackTaskDao.queryPointGsm(map);
   }
   
   public List<CenterZoom>  queryCenter(String flowid,String areaid){
	   Map<String, Object> map = new HashMap<String, Object>();
	    String[] flowids = flowid.split(",");
		List<String> idlist = Arrays.asList(flowids);
		map.put("flowid", idlist);
		return this.trackTaskDao.queryCenter(map);
   }
}
