package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.CenterZoom;
import com.complaint.model.LogSubmanualGsm;
import com.complaint.model.TrackCell;
import com.complaint.model.TrackPoint;
import com.complaint.model.WcdmsTrackLog;

public interface TrackIndependentDao {
	/***
	 * 查询WCDMA日志服务信息
	 * @param map 流水号
	 * @return
	 */
    public List<WcdmsTrackLog> queryWcdma(Map<String, Object> map);
    /***
	 * 查询GSM日志服务信息
	 * @param map 流水号
	 * @return
	 */
    public List<LogSubmanualGsm> queryGsm(Map<String, Object> map);
    
    /**
     * 查询轨迹图小区数据*/
   public List<TrackCell> queryCell(Map<String, Object> map);
   /**
    * 查询轨迹图点数据3g*/
   public List<TrackPoint> queryPointWcdma(Map<String, Object> map);
   
   /**
    * 查询轨迹图点数据2g*/
   public List<TrackPoint> queryPointGsm(Map<String, Object> map);
   /***
    * 查询中心经纬度
    * @param map
    * @return
    */
    public List<CenterZoom>  queryCenter(Map<String, Object> map);
}
