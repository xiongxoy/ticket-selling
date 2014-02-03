package movie.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SeatState extends JPanel {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				try {
					JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Container p = frame.getContentPane();
					p.setLayout(new GridLayout(10,10));
					
					for(int i=0; i<10; i++)
						for(int j=0; j<10; j++)
							if(j%3 == 0)
								p.add(new SeatPanel(false, i, j));
							else 
								p.add(new SeatPanel(true, i, j));
					
				
					System.out.println(p.getComponents().length);
					frame.setSize(300,400);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Deprecated
	public SeatState() {
		// TODO Auto-generated constructor stub
		setLayout(new GridLayout(10,10));
		
		for(int i=0; i<10; i++)
			for(int j=0; j<10; j++)
				if(j%3 == 0)
					add(new SeatPanel(false, i, j));
				else 
					add(new SeatPanel(true, i, j));
		System.out.println(getComponents().length);
	}
	
	public SeatState(Connection con, String S_id)
	{
		setLayout(new GridLayout(10,10));
		boolean [] state = new boolean[100] ;
		for(int i=0; i<100; i++)
			state[i] = false;
		
		
		String query = "select seatid from seat where S_id = \"" +
				        S_id + "\""; 
		
		System.out.println(query);
		
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Vector<Vector<String>> result = null;
		try {
			result = DBOpration.GetResultSet(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(result.size());
		String seatid = null;
		
		for(Vector<String> s : result)
		{
			seatid = s.get(0);
			state[Integer.parseInt(seatid)] = true;
		}
		
		for(int i=0; i<10; i++)
			for(int j=0; j<10; j++)
				if(state[i*10 + j] == false)
					add(new SeatPanel(false,i, j));
				else 
					add(new SeatPanel(true,i, j));
		
		System.out.println(getComponents().length + "!!!");
	}
	
	/**
	 * @return 被选中的座位的数量
	 */
	public int getSelected()
	{
		int count=0;
		for(Component c: getComponents())
		{
			if( ((SeatPanel) c).isSelected())
				count++;
		}
		return count;
	}
	
	
	/**
	 * @return 返回一个Vector<Point2D>,元素是被选中的座位的坐标
	 * 			(不过只能取出double型的坐标,应该自己实现一个存储二维int坐标的类
	 */
	public Vector<Point2D> getSeatVector()
	{
		Vector<Point2D> seatVector = new Vector<Point2D>();
		for(Component c: getComponents())
		{
			if( ((SeatPanel) c).isSelected())
				seatVector.add(((SeatPanel) c).getPoint());
		}
		
		return seatVector;
	}
}

class SeatPanel extends JPanel  {

	private boolean available;
	private boolean selected;
	private JPanel self;
	private Point2D point;

	public Point2D getPoint()
	{
		return point;
	}

	public boolean isSelected() {
		return selected;
	}


	/**
	 * Create the panel.
	 * 
	 * THIS IS A HARD COMPONENT !!!
	 * 
	 * Basically it will need a connection to get access to the cinema DB
	 * It will draw to itself the state of the seats 
	 * 		red is not available 
	 * 		blue is available
	 * It will react to click (if not disabled)
	 * 		clicked component will be selected, click again will release the component
	 */
	public SeatPanel(boolean s, int i, int j) {
		point = new Point(i,j);
		available = s;
		selected = false;	
		self = this;

		this.setLayout(new BorderLayout());
		addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * 响应鼠标点击
			 */
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(available) {
					selected = !selected;
					self.repaint(); //更新
				}
				return;
			}
		});
	}

	/**
	 * 绘制图形的函数
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

		super.paint(g); //使用父类的方法，清空绘图区域
		Graphics2D g2 = (Graphics2D) g;

		//设置颜色
		if(available)
			g2.setColor(Color.BLUE);
		else
			g2.setColor(Color.RED);

		g2.fill(( new Rectangle(5,5,20,20)) );

		if(selected)
		{	
			//绘制边框，数据基本是尝试出来的，也可以试着算一下
			g2.setColor(Color.ORANGE);
			g2.fill(new Rectangle(0,0,5,30));
			g2.fill(new Rectangle(0,0,30,5));
			g2.fill(new Rectangle(0,25,30,5));
			g2.fill(new Rectangle(25,0,5,30));
		}
	}

};

