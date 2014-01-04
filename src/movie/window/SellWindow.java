package movie.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import movie.util.DBOpration;
import movie.util.EasySQLTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SellWindow extends JFrame {

	private JPanel contentPane;
	private Connection connection;
	private SellWindow self;
	private JList movieList;
	private EasySQLTable movieTable;
	private String S_id;

	public String getS_id() {
		return S_id;
	}



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SellWindow frame = new SellWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * 出事化售票界面
	 */
	public SellWindow() {
		self = this;
		
		
		connection = DBOpration.connectDB();  //获取连接
		

		
		setTitle("V5\u7535\u5F71\u552E\u7968\u7CFB\u7EDF-\u552E\u7968\u754C\u9762");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 785, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u6587\u4EF6");
		mnNewMenu.setIcon(new ImageIcon(SellWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u6CE8\u9500");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login l = new Login();
				
				self.dispose();
				l.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem menuItem = new JMenuItem("\u9000\u51FA");
		menuItem.addActionListener(new ActionListener() {
			/**
			 * 退出响应
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
		mnNewMenu.add(menuItem);
		
		JMenu mnNewMenu_1 = new JMenu("\u5E2E\u52A9");
		mnNewMenu_1.setIcon(new ImageIcon(SellWindow.class.getResource("/javax/swing/plaf/metal/icons/Question.gif")));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5173\u4E8E");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			/**
			 * 弹出about框
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				About l = new About();
				l.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(201, 106, 548, 435);
		contentPane.add(panel);
		
	
		panel.setLayout(new BorderLayout());
	    EasySQLTable table = new EasySQLTable(panel);
		this.movieTable = table;
		movieTable.setAutoscrolls(true);
		movieTable.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(movieTable);
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setBounds(12, 88, 168, 453);
		contentPane.add(scrollPane);
		
		
		JLabel lblNewLabel = new JLabel("\u7535\u5F71\u540D");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		scrollPane.setColumnHeaderView(lblNewLabel);
	
		JList list = new JList();
		movieList = list;
		list.addMouseListener(new ListClickListener());
		list.setValueIsAdjusting(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(list);	
		setMovieList();
		

		
		JLabel label = new JLabel("\u8BF7\u9009\u62E9\u7535\u5F71");
		label.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label.setBounds(25, 24, 106, 35);
		contentPane.add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(75, 449, 1, 2);
		contentPane.add(separator);
		
		JLabel label_1 = new JLabel("\u53EF\u9009\u573A\u6B21\u5217\u8868");
		label_1.setBounds(428, 72, 84, 18);
		contentPane.add(label_1);
		
		JButton button = new JButton("\u8FDB\u5165\u8D2D\u7968\u754C\u9762");
		button.addActionListener(new ActionListener() {
	
			/**
			 * 进入购票界面按钮的事件处理
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(movieTable.getSelectData() != null )
				{
					S_id = movieTable.getSelectData();

					TicketWindow t = null;
					try
					{
					t = new TicketWindow(self, connection);
					}
					catch (NoSuchFieldError err) {
						System.out.println("OMG");
						JOptionPane.showMessageDialog(null,
								"该场次已无剩余座位");
						return;
					}
					
					if(t != null)
					{
						t.setVisible(true);
					}
					return;
				}
				
				JOptionPane.showMessageDialog(contentPane, "请先选择场次 ！！！","提示",JOptionPane.ERROR_MESSAGE) ;
				return;
			}
		});
		button.setToolTipText("\u8BF7\u4ECE\u573A\u6B21\u5217\u8868\u4E2D\u9009\u62E9\u6307\u5B9A\u7684\u573A\u6B21\u540E\uFF0C\u518D\u8FDB\u5165\u8D2D\u7968\u754C\u9762");
		button.setBounds(188, 23, 112, 44);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u8FD4\u56DE");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Login l = new Login();
				self.dispose();
				l.setVisible(true);
			}
		});
		button_1.setToolTipText("\u6CE8\u9500\u5E76\u8FD4\u56DE\u767B\u5F55\u754C\u9762");
		button_1.setBounds(642, 31, 84, 28);
		contentPane.add(button_1);
		
		
	}
	

	/**
	 * 设置左侧表单的项
	 */
	private void setMovieList()
	{
		String getmovie = "SELECT M_name FROM movie";
		ResultSet movieRs = movieTable.getRs(getmovie, connection);
		Vector<Vector<String>> VectorTemp = new Vector<Vector<String>>();
		final Vector<String> movieTemp = new Vector<String>();
	  try {
		  
		  VectorTemp = (movieTable.GetResultSet(movieRs));
		
		  for(Object t: VectorTemp.toArray())
		  {
			 movieTemp.add(((Vector<String>) t).get(0)); 
		  }
		
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  
		
		movieList.setModel(new AbstractListModel() {
			Object[] values =  movieTemp.toArray();
			@Override
			public int getSize() {
				return values.length;
			}
			@Override
			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}

	
	public String getSlectedData()
	{
		// TODO Consider to improve 
		return movieTable.getSelectData();
	}

	
	class ListClickListener extends MouseAdapter{
		@Override
		/**
		 *处理表单点击的事件，更新右侧的表
		 */
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource() == movieList)
			{
				String name_click = (String) movieList.getSelectedValue();
				
				System.out.println(name_click);
				
				String query = "select  S_id as 场次编号, M_name as 电影名, S_date as 日期, S_time as 开始时间 ,O_id as 放映厅号  " +
								"from session ,movie where movie.M_id = session.M_id and  M_name = ";
								
				query.concat(name_click);
				System.out.println(query);
				query = query.concat("\"").concat(name_click).concat("\"");
				
				System.out.println(query);
				
				try
				{
					movieTable.getTable(query, connection);
				}
				catch (NoSuchFieldError e2) {
					JOptionPane.showMessageDialog(null, "该电影暂无场次", "提示", JOptionPane.ERROR_MESSAGE, null);
				}
				
			}
			
		}
	}
	

}
