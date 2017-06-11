/**
 * 
 */
package wuziqi;

/**
 * @author G50
 *
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class ChessBoard extends JPanel implements MouseListener{
	public static final int BIANJU =30;//边距
	public static final int JIANJU =35;//网格间距
	public static final int ROWS=10;//棋盘行数
	public static final int COLS =10;//棋盘列数
	
	ChessPoint[] chessList =new ChessPoint[(ROWS+1)*(COLS+1)];//落棋点
	
	boolean isBlack=true;//默认黑棋先手
	boolean gameOver=false;//游戏死否结束
	
	int chessCount;//棋盘棋子个数
	
	int xIndex,yIndex;//刚下棋子的索引
	
	
	public ChessBoard(){
		this.setBackground(Color.orange);
		this.addMouseListener(this);
		this.addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e){
				
			}
			public void mouseMoved(MouseEvent e){
				int x1=(e.getX()-BIANJU+JIANJU/2)/JIANJU ;//将鼠标点击的位置转换成网格的横索引(格数)
				int y1=(e.getY()-BIANJU+JIANJU/2)/JIANJU ;//                          纵索引
				
				if(x1<0 || x1>ROWS || y1<0 || y1>COLS ||gameOver ||findChess(x1,y1))
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//将光标设置成默认形状
				else
					setCursor(new Cursor(Cursor.HAND_CURSOR));//设置成手型
			}
		});
	}
	
	
	public void paint(Graphics g){
		super.paint(g);//画棋盘
		
		for(int i=0;i<=ROWS;i++){ 														//画棋子
			g.drawLine(BIANJU, BIANJU+i*JIANJU, BIANJU+COLS*JIANJU, BIANJU+i*JIANJU);//画棋盘横线
			
		}
		for(int i=0;i<=COLS;i++){
			g.drawLine(BIANJU+i*JIANJU, BIANJU, BIANJU+i*JIANJU, BIANJU+ROWS*JIANJU);//画棋盘竖线
		}
		
		for(int i=0;i<chessCount;i++){
			int xPos=chessList[i].getX()*JIANJU+BIANJU;//棋盘交叉点的x坐标
			int yPos=chessList[i].getY()*JIANJU+BIANJU;//           y
			
			g.setColor(chessList[i].getColor());//设置颜色
			g.fillOval(xPos-ChessPoint.Diameter/2, yPos-ChessPoint.Diameter/2,
					ChessPoint.Diameter, ChessPoint.Diameter);
			//为棋子填充颜色
			
			if(i==chessCount-1){			//标记最后一个棋子
				g.setColor(Color.red);
				g.drawRect(xPos-ChessPoint.Diameter/2, yPos-ChessPoint.Diameter/2, 
						ChessPoint.Diameter, ChessPoint.Diameter);
				
			}
		}
	}
	
	
	public void mousePressed(MouseEvent e){
		if(gameOver)			//游戏结束
			return;
		
		String colorName = isBlack ? "黑棋" :"白棋";
		
		xIndex=(e.getX()-BIANJU+JIANJU/2)/JIANJU;	//鼠标点击的位置的横索引
		yIndex=(e.getY()-BIANJU+JIANJU/2)/JIANJU;   //				 竖
		
		if(xIndex<0 ||xIndex>ROWS ||yIndex<0 ||yIndex>COLS){	//落在棋盘外
			return;
		}
		
		if(findChess(xIndex,yIndex)){		//x,y位置已经有棋子
			return;
		}
		
		ChessPoint ch=new ChessPoint(xIndex,yIndex,isBlack?Color.black:Color.white);
		chessList[chessCount++]=ch;
		repaint();		//重新绘制
		
		if(isWin()){
			String msg=String.format("恭喜，%s赢了！", colorName);//String.format查API
			JOptionPane.showMessageDialog(this, msg);		//查API
			gameOver=true;
		}
		
		isBlack=!isBlack;
	}
	
	
	public void mouseClicked(MouseEvent e){
		
	}
	public void mouseEntered(MouseEvent e){
		
	}
	public void mouseExited(MouseEvent e){
		
	}
	public void mouseReleased(MouseEvent e){
		
	}
	
	private boolean findChess(int x,int y){			//在棋子数组中查找是否有索引为x,y的存在，
		for(ChessPoint c : chessList){				//for(s:v)表示遍历所有V然后赋给变量S
			if(c!=null && c.getX()==x && c.getY()==y){
				return true;
			}
		}
		return false;
	}
	
	
	private boolean isWin(){
		int continueCount = 1;		//连续棋子的个数
		
		for(int x=xIndex-1;x>=0;x--){		//横向向西寻找
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(x,yIndex,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		for(int x=xIndex+1;x<=ROWS;x++){		//横向向东寻找
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(x,yIndex,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		if(continueCount >= 5){
			return true;
		}
		else{			//横向没有成功，搜索其他方向
			continueCount =1;
		}
		
		for(int y=yIndex-1;y>=0;y--){		//纵向向北搜索
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(xIndex,y,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		for(int y=yIndex+1;y<=COLS;y++){		//纵向向南搜索
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(xIndex,y,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		if(continueCount >= 5){
			return true;
		}
		else{			//纵向没成功，搜索其他方向
			continueCount = 1;
		}
		
		for(int x=xIndex-1,y=yIndex-1;x >= 0&&y >= 0;x--,y--){	//西北方向搜索
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(x,y,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		for(int x=xIndex+1,y=yIndex+1;x <= ROWS && y <= COLS;x++,y++){	//东南方向搜索
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(x,y,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		if(continueCount >=5){
			return true;
		}
		else{						//135'搜索没成功，搜其他方向
			continueCount = 1;
		}
		
		for(int x=xIndex+1,y=yIndex-1;x <=COLS && y >=0;x++,y--){	//东北方向搜索
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(x,y,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		for(int x=xIndex-1,y=yIndex+1;x >=0 && y <= COLS;x--,y++){
			Color c = isBlack ? Color.black :Color.white;
			if(getChess(x,y,c)!= null){
				continueCount++;
			}
			else
				break;
		}
		
		if(continueCount >=5){
			return true;
		}
		else{		//45'搜索没成功
			continueCount = 1;
		}
		
		return false;	//所有方向都搜索完毕
	}
	
	
	private ChessPoint getChess(int xIndex,int yIndex,Color color){
		for(ChessPoint c : chessList){
			if(c != null && c.getX()==xIndex && c.getY()==yIndex && c.getColor()==color){
				return c;
			}
		}
		return null;
	}
	
	
	public void restartGame(){			//重新开始
		for(int i=0;i< chessList.length;i++){
			chessList[i] = null;			//清空棋子
		}
		
		isBlack = true;				//恢复初始值
		gameOver = false;
		chessCount =0;
		repaint();
	}
	
	public void goback(){
		if(chessCount ==0)		//没子，无法悔棋
			return;
		
		chessList[chessCount-1] =null;
		chessCount--;
		if(chessCount >0){
			xIndex = chessList[chessCount-1].getX();
			yIndex = chessList[chessCount-1].getY();
		}
		isBlack = !isBlack;
		repaint();
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(BIANJU *2 +JIANJU * COLS,BIANJU *2 +JIANJU * ROWS);
	}
}
