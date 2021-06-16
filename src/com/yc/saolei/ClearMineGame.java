package com.yc.saolei;

import java.util.*;

public class ClearMineGame {
	private MineMap map;
	private int pointCount;
	private Point [][]minePoints;
	Scanner sc=new Scanner(System.in);
	public void startGame(){
		System.out.println("****欢迎进入扫雷社区****");
	 	System.out.println("     1.初级");
		System.out.println("     2.中级");
		System.out.println("     3.高级");
		System.out.println("********************");
		System.out.println("  请选择游戏难度：");
		int choice=0;
		do{
			choice=sc.nextInt();
			switch(choice){
			case 1:
				map=new MineMap(9,9,10);
				pointCount=map.getHeight()*map.getWidth();
				break;
			case 2:
				map=new MineMap(16,16,40);
				pointCount=map.getHeight()*map.getWidth(); 
				break;
			case 3:
				map=new MineMap(32,16,99);
				pointCount=map.getHeight()*map.getWidth();
				break;
			default:
				System.out.println("没有此选项，请重新输入：");
				break;
			}
		}while(choice<1||choice>3);
		minePoints=map.getMineMap();
		System.out.println("==============================");
		playGame();
	}
	public void playGame(){
		boolean isOver=false;
		int choice=0;
		showMinePoint();
		System.out.println("请输入棋子的坐标（x，y）：");
		int x=sc.nextInt();
		int y=sc.nextInt();
		Point point=minePoints[x-1][y-1];//点的操作
		System.out.println("请输入操作：1.打开 2.插旗 3.拔旗");
		do{
			choice=sc.nextInt();
			switch(choice){
			case 1:
				isOver=openPoint(point);//isOver=openPoint(minePoints[x-1][y-1]);
				break;
			case 2:
				insertFlag(point);
				break;
			case 3:
				delFlag(point);
				break;
			default:
				System.out.println("没有此选项，请重新选择：");
				break;
			}
		}while(choice>3||choice<1);
		if(isOver==true){
			System.out.println("游戏结束。。。。");
		}else{
			playGame();
		}
	}
	public void delFlag(Point p){
		int state=p.getState();
		if((state&0b0000110)==0b0000100){//写p.setState
			minePoints[p.getX()][p.getY()].setState(state&0b1111001);
			return;//打开 没棋都不行
		}
		System.out.println("此坐标没有插旗，不能拔旗。。。。");
	}
	public void insertFlag(Point p){
		int state=p.getState();
		if((state&0b0000110)==0b0000010){
			System.out.println("已经打开，不能插旗。。。。");
			return;
		}
		if((state&0b0000110)==0b0000100){
			System.out.println("已经插旗，不能继续插旗。。。。。");
			return;
		}
		minePoints[p.getX()][p.getY()].setState(state+0b0000100);
	}
	public boolean openPoint(Point p){
		int state=p.getState();
		if((state&0b0000001)==0b0000001){
			for(int i=0;i<minePoints.length;i++){
				for(int j=0;j<minePoints[i].length;j++){
					minePoints[i][j].setState((minePoints[i][j].getState()&0b1111001)|0b0000010);//全部棋子打开
				}
			}
			showMinePoint();       //????
			return true;
		}else{
			openWhite(p);
			if(pointCount==map.getMineCount()){
				System.out.println("恭喜通关.....");
				return true;
			}
		}
		return false;
	}
	private void openWhite(Point p){
		int state=p.getState();
		if((state&0b1)==0b1||(state&0b110)==0b010||(state&0b110)==0b100){
			return;//有雷 打开 插旗 都返回
		}
		minePoints[p.getX()][p.getY()].setState(p.getState()+0b010);//0b000
		pointCount--;
		int x=p.getX();
		int y=p.getY();
		if((state>>3)==0){
			if(x-1>=0){
				openWhite(minePoints[x-1][y]);
				if(y-1>=0){
					openWhite(minePoints[x-1][y-1]);
				}
				if(y+1<minePoints[0].length){
					openWhite(minePoints[x-1][y+1]);
				}
			}
			if(x+1<minePoints.length){
				openWhite(minePoints[x+1][y]);
				if(y-1>=0){
					openWhite(minePoints[x+1][y-1]);
				}
				if(y+1<minePoints[0].length){
					openWhite(minePoints[x+1][y+1]);
				}
			}
			if(y-1>=0){
				openWhite(minePoints[x][y-1]);
			}
			if(y+1<minePoints[x].length){
				openWhite(minePoints[x][y+1]);
			}
		}
	}
	public void showMinePoint(){      //????
		int rows=minePoints.length;
		int cols=minePoints[0].length;
		System.out.print("\t");
		for(int i=0;i<cols;i++){
			System.out.print((i+1)+"\t");
		}
		System.out.println();
		for(int i=0;i<rows;i++){
			System.out.print((i+1)+"\t");
			for(int j=0;j<cols;j++){
				if((minePoints[i][j].getState()&0b0000110)==0){
					System.out.print("N\t");
				}else if((minePoints[i][j].getState()&0b0000110)==0b0000100){
					System.out.print("F\t");
				}else{
					if((minePoints[i][j].getState()&0b0000001)==0b0000001){
						System.out.print("M\t");
					}else if((minePoints[i][j].getState()>>3)==0){
						System.out.print("\t");
					}else{
						System.out.print((minePoints[i][j].getState()>>3)+"\t");
					}
				}
			}
			System.out.println();
		}
	}
}
