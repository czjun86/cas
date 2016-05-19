package com.complaint.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.SysConfigDao;
import com.complaint.model.Sysconfig;
import com.complaint.utils.Constant;

@Service("sysConfigService")
public class SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;
	private static final Logger logger = LoggerFactory
			.getLogger(EpinfoExcelService.class);

	/**
	 * 查询值
	 * 
	 * @return
	 */
	public String getValue(String configvalue) {
		return sysConfigDao.queryData(configvalue);
	}
	/**
	 * 查询累计网络投诉工单量
	 * @param systime
	 * @param endtime
	 * @return
	 */
    public List<Map> getTotalSer(Map map){
		return sysConfigDao.getTotalSer(map);
    }
	/**
	 * 修改值
	 * 
	 * @return
	 */
	private void updateValue(String configkey, String configvalue)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("configvalue", configvalue);
		param.put("configkey", configkey);
		sysConfigDao.updateData(param);
	}

	/**
	 * 测试报告默认起始时间、及时率时间配置
	 * 
	 * @param reportdate
	 * @param timelyrate
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateTest(String reportdate, String timelyrate)
			throws Exception {
		updateValue(Constant.REPORTDATE, reportdate);
		updateValue(Constant.TIMELYRATE, timelyrate);

	}
	/**
	 * 质量报表默认起始时间、及时率时间配置
	 * 
	 * @param reportdate
	 * @param timelyrate
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateQual(String qualdate, String timelyrate)
			throws Exception {
		updateValue(Constant.RP_QUALITY_CUMULATIVE_START_TIME, qualdate);
		updateValue(Constant.TIMELYRATE, timelyrate);

	}
	public String getAngleType() {
		Sysconfig sc = new Sysconfig();
		try {
			sc = sysConfigDao.getAngleconfig("cell_angle_config");
		} catch (Exception e) {
			logger.error("cell_angle_config", e);
		}
		String[] str = sc.getConfigvalue().split("=");
		String type = str[1].substring(0, 1);
		return type;
	}
}
