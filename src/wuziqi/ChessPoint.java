/**
 * 
 */
package wuziqi;

/**
 * @author G50
 *
 */

import java.awt.Color;

public class ChessPoint {
	private int x;
	private int y;//用于棋盘中位置索引
	
	private Color color;//棋子颜色
	public static final int Diameter=30;//棋子直径
	
	public ChessPoint(int x,int y,Color color){
		this.x=x;
		this.y=y;
		this.color=color;
		
	}
	
	public int getX(){		//获取x坐标
		return x;
	}
	
	public int getY(){		//获取y坐标
		return y;
	}
	
	public Color getColor(){	//获取颜色
		return color;
	}
}
