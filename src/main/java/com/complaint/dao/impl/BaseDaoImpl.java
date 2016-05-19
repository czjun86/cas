package com.complaint.dao.impl;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.complaint.dao.BaseDao;
import com.complaint.dao.BatchDao;

public class BaseDaoImpl implements BaseDao{
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactoryTemplate;
    private Integer batch = 200;
	
    /**
     * 批量插入,默认为每次200条
     * type为mapper接口类，且必须继承BatchDao接口
     */
    public void batchInsert(Class<?> type,List<?> list,Integer batchNum) throws Exception{
    	if(type == null)
    		return;
    	if(CollectionUtils.isEmpty(list))
    		return;
    	if(batchNum != null && batchNum > 1)
    		this.batch = batchNum;
    	SqlSessionFactory sqlSessionFactory = sqlSessionFactoryTemplate.getObject();
    	SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
    	for(int i=0;i<list.size();i++){
    		BatchDao mapper = (BatchDao)sqlSession.getMapper(type);
    		mapper.insertByBatch(list.get(i));
    		if((i != 0 && i % batch == 0) || i + 1 == list.size())
    		{
    			sqlSession.commit();
    			sqlSession.clearCache();
    		}
    	}
    	sqlSession.close();
    }
}
