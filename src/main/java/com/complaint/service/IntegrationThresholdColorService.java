package com.complaint.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.dao.IntegrationThresholdColorDao;
import com.complaint.io.ObjectSerializableUtil;
import com.complaint.model.ConfigColor;
import com.complaint.model.RateColor;
import com.complaint.utils.CreateImgByColors;

/**
 * 综合阈值颜色事务处理
 * @author peng
 *
 */
@Service("integrationThresholdColorService")
public class IntegrationThresholdColorService {

	@Autowired
	private IntegrationThresholdColorDao integrationThresholdColorDao;
	/**
	 * 查询所有颜色
	 */
	public List<RateColor> getIntegrationColorsWithNoPoundSign(){
		List<RateColor> itc = integrationThresholdColorDao.queryColorCodes();
//		判断是否查出数据，没有查出就自动初始化数据
		if(itc != null && itc.size()>0){
			for(RateColor colorCode : itc){
//				将颜色替换成没有#号的16进制
				colorCode.setRank_color(colorCode.getRank_color().replace("#", ""));
			}
		}else{
//			没有查出数据时的情况
			itc = initColors(itc);
		}
		
		return itc;
	}
	
	/**
	 * 没查出颜色数据时用来初始化数据
	 */
	public List<RateColor> initColors(List<RateColor> colorCodes){
		if(colorCodes == null){
			colorCodes = new ArrayList<RateColor>();
		}
		
//		没有数据是手动初始化数据
		for(int i = 0;i<4;i++){
			RateColor color = new RateColor();			
			color.setRank_code(i+1);
			color.setRank_color("00CCFF");
			color.setScene(0);
			colorCodes.add(color);
		}
		return colorCodes;
	}
	
	/**
	 * 修改颜色
	 */
	public int updateColor(String[] colors){
//		判断colors不为空，并有4为
		if(colors == null || colors.length<4){
			return -2;
		}else{
//			判断colors数组没为都不为空
			for(int i = 0;i<colors.length;i++){
				if(colors[i].isEmpty()){
					return -2;
				}
			}
//			判断colors数组每位的颜色不与其他为相同
			for(int i = 0;i<colors.length;i++){
				if(isExist(colors,colors[i],i)){
					return -1;
				}
			}
//			获取原有颜色用于比较判断是否修改
			List<RateColor> oldColors = this.getIntegrationColorsWithNoPoundSign();
			
			RateColor newColor = null;
			for(int i =0;i<colors.length;i++){
				newColor = new RateColor();
				newColor.setRank_code(i+1);
				newColor.setRank_color("#"+colors[i]);
//				由于还没有分室内外，暂时保存默认为室外
				newColor.setScene(0);
//				进行保存
				try {
					integrationThresholdColorDao.update(newColor);
				} catch (Exception e) {
					return 0;
				}
				
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
	 * 验证colors当前为与其他位置的颜色都不同
	 */
	public boolean isExist(String[] colors,String color,int index){
		for(int i = 0;i<colors.length;i++){
			if(i == index){
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
			CreateImgByColors.createImage(rateColor,completionPath+"/images/integration/w_","●",13,14,13);
			CreateImgByColors.createImage(rateColor,completionPath+"/images/integration/g_","●",14,12,14);
			CreateImgByColors.createImage(rateColor,completionPath+"/images/integration/s_","●",14,14,14);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
