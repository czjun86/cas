package com.complaint.service;
/**
 * 用于在启动服务器时初始部分资源
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.IntegrationThresholdColorDao;
import com.complaint.model.ColourCode;
import com.complaint.model.RateColor;
import com.complaint.utils.CreateImgByColors;
@Service("initPartService")
public class InitPartService {
	@Autowired
	private RateColorCodeService rateColorCodeService;
	
	@Autowired
	private ColourCodeService colourCodeService;
	    /**
	     * 初始化综合阈值颜色图片
	     */
	    public void initIntegrationMap(String url){
	    	List<RateColor> itc = this.rateColorCodeService.getColoursWithNoPoundSign();
	    	RateColor [] colors = new RateColor[4];
	    	for(int i =0;i<itc.size();i++){
	    		colors[i] = new RateColor();
	    		colors[i] = itc.get(i);
	    		colors[i].setRank_color("#"+itc.get(i).getRank_color());
	    		
	    	}
//			获取生成图片保存路径
			String completionPath = url;
			try {
//				生成图片
				CreateImgByColors.createImage(colors,completionPath+"/images/integration/w_","●",13,14,13);
				CreateImgByColors.createImage(colors,completionPath+"/images/integration/g_","▼",14,12,14);
				CreateImgByColors.createImage(colors,completionPath+"/images/integration/s_","★",14,14,14);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    /**
	     * 初始化测试图例图片
	     */
	    public void initKpiMap(String url){
	    	List<ColourCode> itc = this.colourCodeService.getColoursWithNoPoundSign();
	    	ColourCode [] colors = new ColourCode[6];
	    	for(int i =0;i<itc.size();i++){
	    		colors[i] = new ColourCode();
	    		colors[i] = itc.get(i);
	    		colors[i].setColourcode("#"+itc.get(i).getColourcode());
	    		
	    	}
//			获取生成图片保存路径
			String completionPath = url;
			try {
//				生成图片
				CreateImgByColors.createKpiImage(colors,completionPath+"/images/integration/w_","●",18,19,18);
				CreateImgByColors.createKpiImage(colors,completionPath+"/images/integration/g_","▼",19,16,19);
				CreateImgByColors.createKpiImage(colors,completionPath+"/images/integration/l_","■",19,16,19);
				//CreateImgByColors.createKpiImage(colors,completionPath+"/images/integration/s_","◆",23,23,23);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
}
