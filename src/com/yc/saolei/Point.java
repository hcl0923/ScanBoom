package com.yc.saolei;

public class Point {
	private int x;
	private int y;
	private int state;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Point(int x,int y,int state){
		super();
		this.x=x;
		this.y=y;
		this.state=state;
	}
}
