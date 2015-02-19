package model;

import java.io.Serializable;

public class Punto implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private int radio;
	private float x,y;
	private int color;
	private boolean fin;
	
	public Punto(int radio, float x, float y,int color,boolean fin) {
		this.radio = radio;
		this.x = x;
		this.y = y;
		this.color = color;
		this.fin = fin;
	}
	
	public int getColor() {
		return color;
	}
	public int getRadio() {
		return radio;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public boolean isFin() {
		return fin;
	}
}