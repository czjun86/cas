package com.complaint.dao;

import com.complaint.model.ColourCode;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ColourCodeDao {


    void insert(ColourCode colourCode);
    
    void update(ColourCode colourCode);
    
    List<ColourCode> queryColourCodes();
}
