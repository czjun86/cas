package com.complaint.dao;

import java.util.List;
import java.util.Map;

import com.complaint.model.CenterZoom;
import com.complaint.model.GradeBean;
import com.complaint.model.LogSubmanualGsm;
import com.complaint.model.Rate;
import com.complaint.model.RateColor;
import com.complaint.model.TrackCell;
import com.complaint.model.TrackPoint;
import com.complaint.model.WcdmsTrackLog;

/***
 * 等级评测mapper
 * @author Administrator
 *
 */

public interface GradMapper {
	public List<GradeBean> showGradBySingleWcdma(Map<String, Object> map);
	public List<GradeBean> showGradBySingleGsm(Map<String, Object> map);
	public List<Rate> showGradKpi(Map<String, Object> map);
	
	public List<RateColor> showGradColor();
}
