package com.complaint.service;

/***
 * 工单下单次、多次测试轨迹报表服务
 * @author czj
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.TrackMapper;
import com.complaint.model.CenterZoom;
import com.complaint.model.LogSubmanualGsm;
import com.complaint.model.LogSubmanualLte;
import com.complaint.model.TrackCell;
import com.complaint.model.TrackPoint;
import com.complaint.model.WcdmsTrackLog;





@Service("trackService")
public class TrackService {
	@Autowired
	private TrackMapper trackMapper;
	
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
		return this.trackMapper.queryWcdma(map);
    }
    /***
	 * 查询WCDMA日志服务信息
	 * @param map 流水号
	 * @return
	 */
    public List<LogSubmanualLte> queryLte(String id,String areaid)
    {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("areaid",areaid);
		return this.trackMapper.queryLte(map);
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
		return this.trackMapper.queryGsm(map);
    }
	
    /**
     * 查询轨迹图小区数据*/
   public List<TrackCell> queryCell(String flowid,String areaid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowid",flowid);
		map.put("areaid",areaid);
	   return this.trackMapper.queryCell(map);
   }
   /**
    * 查询轨迹图点数据4g*/
   public List<TrackPoint> queryPointLte(String flowid,String areaid){
	Map<String, Object> map = new HashMap<String, Object>();
	String[] flowids = flowid.split(",");
	List<String> idlist = Arrays.asList(flowids);
	map.put("flowid", idlist);
	map.put("areaid",areaid);
	return this.trackMapper.queryPointLte(map);
	   
   }
   /**
    * 查询轨迹图点数据3g*/
   public List<TrackPoint> queryPointWcdma(String flowid,String areaid){
	Map<String, Object> map = new HashMap<String, Object>();
	String[] flowids = flowid.split(",");
	List<String> idlist = Arrays.asList(flowids);
	map.put("flowid", idlist);
	map.put("areaid",areaid);
	return this.trackMapper.queryPointWcdma(map);
	   
   }
   
   /**
    * 查询轨迹图点数据2g*/
   public List<TrackPoint> queryPointGsm(String flowid,String areaid){
	Map<String, Object> map = new HashMap<String, Object>();
	String[] flowids = flowid.split(",");
	List<String> idlist = Arrays.asList(flowids);
	map.put("flowid", idlist);
	map.put("areaid",areaid);
	return this.trackMapper.queryPointGsm(map);
   }
   
   public List<CenterZoom>  queryCenter(String flowid,String areaid){
	   Map<String, Object> map = new HashMap<String, Object>();
	    String[] flowids = flowid.split(",");
		List<String> idlist = Arrays.asList(flowids);
		map.put("flowid", idlist);
		return this.trackMapper.queryCenter(map);
   }
}
