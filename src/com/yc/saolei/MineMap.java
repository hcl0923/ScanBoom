package com.yc.saolei;
import java.util.*;

public class MineMap {
	private int height;
	private int width;
	private int mineCount;
	public MineMap(int height,int width,int mineCount){
		super();
		this.height=height;
		this.width=width;
		this.mineCount=mineCount;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getMineCount() {
		return mineCount;
	}
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}
	public Point[][] getMineMap(){
		return burnMine();
	}
	public Point[][] burnMine(){
		Point [][]points=new Point[height][width];
		for(int i=0;i<points.length;i++){
			for(int j=0;j<points[i].length;j++){
				points[i][j]=new Point(i,j,0);
			}
		}
		Random rd=new Random();
		for(int i=0;i<mineCount;){
			int q=rd.nextInt(height);
			int t=rd.nextInt(width);
			if((points[q][t].getState()&0b0000001)==0){
				points[q][t].setState(points[q][t].getState()|0b0000001);
				i++;
			}
			if(q-1>=0){
				if(t-1>=0){
					points[q-1][t-1].setState(points[q-1][t-1].getState()+0b0001000);
				}
				if(t+1<width){
					points[q-1][t+1].setState(points[q-1][t+1].getState()+0b0001000);
				}
				points[q-1][t].setState(points[q-1][t].getState()+0b0001000);
			}
			if(q+1<height){
				if(t-1>=0){
					points[q+1][t-1].setState(points[q+1][t-1].getState()+0b0001000);
				}
				if(t+1<width){
					points[q+1][t+1].setState(points[q+1][t+1].getState()+0b0001000);
				}
				points[q+1][t].setState(points[q+1][t].getState()+0b0001000);
			}
			if(t-1>=0){
				points[q][t-1].setState(points[q][t-1].getState()+0b0001000);
			}
			if(t+1<width){
				points[q][t+1].setState(points[q][t+1].getState()+0b0001000);
			}
		}
		return points;
	}
}
