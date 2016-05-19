package com.complaint.dao;

import com.complaint.model.Scene;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SceneDao {

    int insert(Scene scene);

    int insertSelective(Scene scene);

    List<Scene> queryAll();
}
