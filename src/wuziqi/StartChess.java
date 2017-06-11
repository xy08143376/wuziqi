/**
 * 
 */
package wuziqi;

/**
 * @author G50
 *
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class StartChess extends JFrame{
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	final int WIDTH=screensize.width;
	final int HEIGH=screensize.height;
	
	private ChessBoard chessBoard;
	private JPanel toolBar;
	private JMenuBar menuBar; //菜单栏,用来放置菜单
	private JMenu sysMenu; //系统菜单
	
	private JButton startButton;	//重新开始
	private JButton backButton; //悔棋
	private JButton exitButton; //退出
	
	private JMenuItem startMenuItem; //菜单项
	private JMenuItem backMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem aboutGame;
	
	public StartChess(){
		this.setTitle("单机五子棋");
		
		chessBoard = new ChessBoard();//初始化
		menuBar = new JMenuBar();//初始化菜单栏
		sysMenu = new JMenu("系统");//初始化菜单
		
		startMenuItem = new JMenuItem("重新开始");//初始化菜单项
		backMenuItem = new JMenuItem("悔棋");
		exitMenuItem = new JMenuItem("退出");
		aboutGame = new JMenuItem("关于游戏");
		
		sysMenu.add(startMenuItem);//将菜单项添加到菜单
		sysMenu.add(backMenuItem);
		sysMenu.add(exitMenuItem);
		sysMenu.add(aboutGame);
		
		MyItemListener itemLis = new MyItemListener();//事件监听器内部类
		
		this.startMenuItem.addActionListener(itemLis);//菜单项注册监听器
		this.backMenuItem.addActionListener(itemLis);
		this.exitMenuItem.addActionListener(itemLis);
		this.aboutGame.addActionListener(itemLis);
		
		menuBar.add(sysMenu);//将菜单添加到菜单栏
		
		this.setJMenuBar(menuBar);//添加菜单栏
		
		toolBar = new JPanel();//实例化工具栏
		
		startButton = new JButton("重新开始");//初始化工具按钮
		backButton = new JButton("悔棋");
		exitButton = new JButton("退出");
		
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		toolBar.add(startButton);
		toolBar.add(backButton);
		toolBar.add(exitButton);
		
		startButton.addActionListener(itemLis);
		backButton.addActionListener(itemLis);
		exitButton.addActionListener(itemLis);//注册监听器
		
		this.add(toolBar, BorderLayout.SOUTH);//添加工具栏
		
		this.add(chessBoard);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//setSize(WIDTH,HEIGH);	
		setLocation(WIDTH/3, HEIGH/6);
		
		pack();//自适应大小
		
	}
	
	private class MyItemListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object obj = e.getSource();//获取事件源
			
			if(obj == startMenuItem ||obj == startButton){
				//重新开始
				//JFiveFrame.this内部类引用外部类
				System.out.println("重新开始。。。");
				chessBoard.restartGame();
				
			} 
			else if(obj == exitMenuItem || obj == exitButton){
				System.exit(0);
				//结束应用程序
			}
			else if(obj == backMenuItem || obj == backButton){
				System.out.println("悔棋。。。");
				chessBoard.goback();
			}
			else if(obj == aboutGame){
				String msg = "本游戏为黑棋先手，五子一线为赢。";
				JOptionPane.showMessageDialog(null, msg);
			}
		}
	}
	
	public static void main(String[] args){
		StartChess ss = new StartChess();
		
	}
}
