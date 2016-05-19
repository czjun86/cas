package com.complaint.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import com.complaint.model.ColourCode;
import com.complaint.model.RateColor;

public class CreateImgByColors {
	/**
	 * 评价图例生成图片
	 * @param colors16进制带#号颜色，
	 * @param url images的路径
	 * @param img 图片符号
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param fontsize 字体SIZE
	 * @throws Exception
	 */
	public static void createImage(RateColor[] colors,String url,String img,int width,int height,int fontsize )throws Exception {
		for (int i = 0; i < colors.length; i++) {
			//String s = "●";
			String name = url + colors[i].getRank_code() + ".png";
			String realName = realPathUniform(name);
			File file = new File(realName);
			Font font = new Font("Serif", Font.BOLD, fontsize);
			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			bi = g2.getDeviceConfiguration().createCompatibleImage(width,
					height, Transparency.TRANSLUCENT);
			g2.dispose();
			g2 = bi.createGraphics();
			// 将16进制带#的color转成int类型的rgb生成颜色
			int r = Integer.valueOf(colors[i].getRank_color().substring(1, 3), 16);
			int g = Integer.valueOf(colors[i].getRank_color().substring(3, 5), 16);
			int b = Integer.valueOf(colors[i].getRank_color().substring(5, 7), 16);
			g2.setColor(new Color(r, g, b));
			g2.setStroke(new BasicStroke(1));

			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(img, context);
			double x = (width - bounds.getWidth()) / 2;
			double y = (height - bounds.getHeight()) / 2+1;
			double ascent = -bounds.getY();
			double baseY = y + ascent;
			g2.setFont(font);
			g2.drawString(img, (int) x, (int) baseY);
			g2.dispose();
			ImageIO.write(bi, "png", file);
		}
	}  
	
	
	/**
	 * 指标测试图例生成图片
	 * @param colors16进制带#号颜色，
	 * @param url images的路径
	 * @param img 图片符号
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param fontsize 字体SIZE
	 * @throws Exception
	 */
	public static void createKpiImage(ColourCode[] colors,String url,String img,int width,int height,int fontsize )throws Exception {
		for (int i = 0; i < colors.length; i++) {
			//String s = "●";
			String name = url + colors[i].getColourcode().substring(1,colors[i].getColourcode().length()) + ".png";
			String realName = realPathUniform(name);
			File file = new File(realName);
			Font font = new Font("Serif", Font.BOLD, fontsize);
			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			bi = g2.getDeviceConfiguration().createCompatibleImage(width,
					height, Transparency.TRANSLUCENT);
			g2.dispose();
			g2 = bi.createGraphics();
			// 将16进制带#的color转成int类型的rgb生成颜色
			int r = Integer.valueOf(colors[i].getColourcode().substring(1, 3), 16);
			int g = Integer.valueOf(colors[i].getColourcode().substring(3, 5), 16);
			int b = Integer.valueOf(colors[i].getColourcode().substring(5, 7), 16);
			g2.setColor(new Color(r, g, b));
			g2.setStroke(new BasicStroke(1));

			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(img, context);
			double x = (width - bounds.getWidth()) / 2;
			double y = (height - bounds.getHeight()) / 2+1;
			double ascent = -bounds.getY();
			double baseY = y + ascent;
			g2.setFont(font);
			g2.drawString(img, (int) x, (int) baseY);
			g2.dispose();
			ImageIO.write(bi, "png", file);
		}
	}  
	
	public static void createGsmImage(RateColor[] colors,String url)throws Exception {
		for (int i = 0; i < colors.length; i++) {
			int width = 14;
			int height = 12;
			String s = "▼";
			String name = url + "/images/integration/g_" + colors[i].getRank_code() + ".png";
			String realName = realPathUniform(name);
			File file = new File(realName);
			Font font = new Font("Serif", Font.BOLD, 14);
			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			bi = g2.getDeviceConfiguration().createCompatibleImage(width,
					height, Transparency.TRANSLUCENT);
			g2.dispose();
			g2 = bi.createGraphics();
			// 将16进制带#的color转成int类型的rgb生成颜色
			int r = Integer.valueOf(colors[i].getRank_color().substring(1, 3), 16);
			int g = Integer.valueOf(colors[i].getRank_color().substring(3, 5), 16);
			int b = Integer.valueOf(colors[i].getRank_color().substring(5, 7), 16);
			g2.setColor(new Color(r, g, b));
			g2.setStroke(new BasicStroke(1));

			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(s, context);
			double x = (width - bounds.getWidth()) / 2;
			double y = (height - bounds.getHeight()) / 2+2;
			double ascent = -bounds.getY();
			double baseY = y + ascent;
			g2.setFont(font);
			g2.drawString(s, (int) x, (int) baseY);
			g2.dispose();
			ImageIO.write(bi, "png", file);
		}
	}  
	
	public static void createSpeicalImage(RateColor[] colors,String url)throws Exception {
		for (int i = 0; i < colors.length; i++) {
			int width = 14;
			int height = 14;
			String s = "★";
			String name = url + "/images/integration/s_" + colors[i].getRank_code() + ".png";
			String realName = realPathUniform(name);
			File file = new File(realName);
			Font font = new Font("Serif", Font.BOLD, 14);
			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			bi = g2.getDeviceConfiguration().createCompatibleImage(width,
					height, Transparency.TRANSLUCENT);
			g2.dispose();
			g2 = bi.createGraphics();
			// 将16进制带#的color转成int类型的rgb生成颜色
			int r = Integer.valueOf(colors[i].getRank_color().substring(1, 3), 16);
			int g = Integer.valueOf(colors[i].getRank_color().substring(3, 5), 16);
			int b = Integer.valueOf(colors[i].getRank_color().substring(5, 7), 16);
			g2.setColor(new Color(r, g, b));
			g2.setStroke(new BasicStroke(1));

			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(s, context);
			double x = (width - bounds.getWidth()) / 2;
			double y = (height - bounds.getHeight()) / 2+1;
			double ascent = -bounds.getY();
			double baseY = y + ascent;
			g2.setFont(font);
			g2.drawString(s, (int) x, (int) baseY);
			g2.dispose();
			ImageIO.write(bi, "png", file);
		}
	}  
	
	/**
	 * linux系统路径转换
	 */
	public static String realPathUniform(String path) {
		String uniPath = "";
		if (path == null || "".equals(path)) {
			uniPath = File.separator;
			return uniPath;
		}
		if (path.endsWith("/") || path.endsWith("\\")) {
			uniPath = path;
		} else {
			uniPath = path + File.separator;
		}
		return uniPath;
	}
}
