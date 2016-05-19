package com.complaint.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.complaint.dao.RateColorDao;
import com.complaint.io.ObjectSerializableUtil;
import com.complaint.model.ColourCode;
import com.complaint.model.ConfigColor;
import com.complaint.model.RateColor;
import com.complaint.utils.CreateImgByColors;

@Service("rateColorCodeService")

public class RateColorCodeService {
	@Autowired
	private RateColorDao rateColorDao;
	
	/**
	 * 查出所有颜色去掉#
	 * @return
	 */
	public List<RateColor> getColoursWithNoPoundSign(){
		List<RateColor> colors = this.rateColorDao.queryRateColors();
		if(colors != null && colors.size()>0){
			for(RateColor rateColors:colors){
				rateColors.setRank_color(rateColors.getRank_color().replace("#", ""));
			}
		}else{
			colors = initColors(colors);
		}
		return colors;
	}
	
	/**
	 * 没查到颜色数据，手动初始化颜色数据
	 */
	public List<RateColor> initColors(List<RateColor> colors){
		if(colors == null){
			colors = new ArrayList<RateColor>();
		}
		RateColor rateColor = null;
		for(int i = 0;i < 4;i++){
			rateColor = new RateColor();
			rateColor.setRank_code(i+1);
			rateColor.setRank_color("00CCFF");
			colors.add(rateColor);
		}
		return colors;
	}
	
	
	/**
	 * 保存颜色
	 */
	@Transactional(rollbackFor=Exception.class)
	public int  saveColor(String[] colors)throws Exception{
		if(colors == null || colors.length <4){
			return -2;
		}else{
			for(int i =0;i<colors.length;i++){
				if(StringUtils.isEmpty(colors[i])){
					return -2;
				}
			}
			for(int i =0;i<colors.length;i++){
				if(isExist(colors,colors[i],i)){
					return -1;
				}
			}
			List<RateColor> oldColors = this.getColoursWithNoPoundSign();
			RateColor rateColor = null;
			for(int i=0; i<colors.length; i++){
				rateColor = new RateColor();
				rateColor.setRank_code(i+1);
				rateColor.setRank_color("#"+colors[i]);
				rateColor.setScene(0);
				rateColorDao.update(rateColor);
			}
			//判断颜色是否修改
			boolean flag = false;
			for(int i = 0;i < oldColors.size();i++){
				if(!oldColors.get(i).getRank_color().equals(colors[i])){
					flag = true;
					break;
				}
			}
			if(flag){
				String filePath = this.getClass().getClassLoader().getResource("").getPath()+"/colorvision.txt";
					ConfigColor color = ObjectSerializableUtil.readObject(filePath);
					if(color == null){
						color = new ConfigColor();
						color.setVision("1");
					}else{
						color.setVision((Integer.parseInt(color.getVision())+1)+"");
					}
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("vision", color.getVision());
					StringWriter out = new StringWriter();
					jsonObject.writeJSONString(out);
					ObjectSerializableUtil.write(filePath, out.toString());
					out.close();
			}
			return 1;
		}
	}
	
	/**
	 * 判断颜色值是否重复
	 * @param colors
	 * @param color
	 * @param index
	 * @return
	 */
	private boolean isExist(String[] colors,String color,int index){
		for(int i = 0; i < colors.length; i++) {
			if (index == i) {
				continue;
			}else{
				if(color.equals(colors[i])){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 生成图片
	 */
	public void createImages(String completionPath,String[] colors){
//		生成图片
		RateColor[] rateColor = new RateColor[4];
		for(int i =0;i<colors.length;i++){
			rateColor[i] = new RateColor();
			rateColor[i].setRank_code(i+1);
			rateColor[i].setRank_color("#"+colors[i]);
		}
		try {
			//生成图片
			CreateImgByColors.createImage(rateColor,completionPath+"/images/integration/w_","●",13,14,13);
			CreateImgByColors.createImage(rateColor,completionPath+"/images/integration/g_","▼",14,12,14);
			CreateImgByColors.createImage(rateColor,completionPath+"/images/integration/s_","★",14,14,14);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
