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

import com.complaint.dao.ColourCodeDao;
import com.complaint.io.ObjectSerializableUtil;
import com.complaint.model.ColourCode;
import com.complaint.model.ConfigColor;
import com.complaint.model.RateColor;
import com.complaint.utils.CreateImgByColors;

@Service("colourCodeService")
public class ColourCodeService {
	@Autowired
	private ColourCodeDao colourCodeDao;
	/**
	 * 查询所用的颜色值
	 * @return
	 */
	public List<ColourCode> getColourCodes(){
		return colourCodeDao.queryColourCodes();
	}
	/**
	 * 查询不带#的颜色值
	 * @return
	 */
	public List<ColourCode> getColoursWithNoPoundSign(){
		List<ColourCode> codes = colourCodeDao.queryColourCodes();
		if (codes != null && codes.size() > 0) {
			for (ColourCode colourCode : codes) {
				colourCode.setColourcode(colourCode.getColourcode().replace("#", ""));
			}
		}else{
			codes = initColors(codes);
		}
		return codes;
	}
	/**
	 * 初始化codes
	 * @param codes
	 * @return
	 */
	private List<ColourCode> initColors(List<ColourCode> codes){
		if(codes == null){
			codes = new ArrayList<ColourCode>();
		}
		ColourCode cc = null;
		for (int i = 0; i < 6; i++) {
			cc = new ColourCode();
			cc.setSerialNum((short)(i+1));
			codes.add(cc);
		}
		return codes;
	}
	/**
	 * 
	 * @param colors
	 * @return -2:未填完整   -1：有重复的color 0:系统错误 1:修改成功
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateColour(String[] colors)throws Exception{
		if(colors == null || colors.length <6){
			return -2;
		}else{
			for(int i = 0; i < colors.length; i++) {
				if(StringUtils.isEmpty(colors[i])){
					return -2;
				}
			}
			for (int i = 0; i < colors.length; i++) {
				if(isExist(colors,colors[i],i)){
					return -1;
				}
			}
			List<ColourCode> oldColors = this.getColoursWithNoPoundSign();
			ColourCode colourCode = null;
			for(int i=0; i<colors.length; i++){
				colourCode = new ColourCode();
				colourCode.setSerialNum((short)(i+1));
				colourCode.setColourcode("#"+colors[i]);
				colourCodeDao.update(colourCode);
			}
			//判断颜色是否修改
			boolean flag = false;
			for(int i = 0;i < oldColors.size();i++){
				if(!oldColors.get(i).getColourcode().equals(colors[i])){
					flag = true;
					break;
				}
			}
			if(flag){
				String filePath = this.getClass().getClassLoader().getResource("").getPath()+"/colorvision.txt";
				try {
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
					
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		ColourCode[] cc = new ColourCode[6];
		for(int i =0;i<colors.length;i++){
			cc[i] = new ColourCode();
			cc[i].setSerialNum(i+1);
			cc[i].setColourcode("#"+colors[i]);
		}
		try {
			CreateImgByColors.createKpiImage(cc,completionPath+"/images/integration/w_","●",18,19,18);
			CreateImgByColors.createKpiImage(cc,completionPath+"/images/integration/g_","▼",19,16,19);
			CreateImgByColors.createKpiImage(cc,completionPath+"/images/integration/l_","■",19,16,19);
			//CreateImgByColors.createKpiImage(cc,completionPath+"/images/integration/s_","◆",23,23,23);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
