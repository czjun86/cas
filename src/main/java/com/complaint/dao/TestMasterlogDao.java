package com.complaint.dao;

import java.util.Map;

import com.complaint.model.TCasCell;
import com.complaint.model.TestMasterlog;
import com.complaint.model.WcdmsTrackLog;

public interface TestMasterlogDao{

    int deleteByPrimaryKey(String flowid);

    int insert(TestMasterlog testMasterlog);
    
    int insertTask(TestMasterlog testMasterlog);
    
    int insertOwn(TestMasterlog testMasterlog);

    int insertSelective(TestMasterlog testMasterlog);

    TestMasterlog selectByPrimaryKey(String flowid);

    int updateByPrimaryKeySelective(TestMasterlog testMasterlog);

    int updateByPrimaryKey(TestMasterlog testMasterlog);
    
    int querySequence();
    
    int queryOrderNum(String serialno);
    
    int queryTaskOrderNum(String serialno);
    
    int queryOwnOrderNum(String serialno);
    
    void delMasterLogByFlowid(String flowid);
    /**
     * 修改总表地址
    
     * @Title: updateAdress
    
     * @Description: TODO
    
     * @param testMasterlog
     * @return
    
     * @return: int
     */
    int updateAdress(TestMasterlog testMasterlog);
    /**
     * PSC反算CID、LAC
    
     * @Title: updateCidLac
    
     * @Description: TODO
    
     * @param map
     * @return
    
     * @return: TWnmsCell
     */
    TCasCell updateCidLac(Map map);
    /**
     * PSC反算CID、LAC根据人
    
     * @Title: updateCidLacBylatlng
    
     * @Description: TODO
    
     * @param map
     * @return
    
     * @return: WcdmsTrackLog
     */
    WcdmsTrackLog updateCidLacBylatlng(Map map);

	
}
