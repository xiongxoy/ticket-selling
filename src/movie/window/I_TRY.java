package movie.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

import movie.util.DBOpration;
import movie.util.EasySQLTable;


/**
 * 场次管理的主界面，里面分别为增加场次，删除场次和查看销售情况
 * @author 实验室
 *
 */
public class I_TRY extends JFrame implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private JPanel contentPane;               // 存放电影场次表的面板
	private Connection connection;           //和数据库的连接
	private JList list;                      //电影名列表
	private EasySQLTable movieTable;          //场次列表
	private I_TRY self;                          //一个指向自己的指针
	private Vector<Vector<String>> VectorTemp ;//临时保存从数据库取出的movie表的内容，有电影名和电影ID
	private Vector<String> movieTemp ;         //和上面的一样，只是保存的只有电影名这一列
	private String view_query;                //数据库取出电影名和电影ID所用到的数据库查询的语句
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					I_TRY frame = new I_TRY();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 返回框架里的属性table
	 * @return
	 */
	public EasySQLTable getMyTable() 
	{
		return movieTable;
	}
	/**
	 * Create the frame.
	 */
	public I_TRY() {
		
		
		self = this;
		connection = DBOpration.connectDB();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("注销");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Login l = new Login();
				l.setVisible(true);
				
				self.dispose();
				
			}
		});
		btnNewButton.setBounds(642, 24, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("增加场次");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String movieName =(String) list.getSelectedValue();
				//int listSelectIndex = list.getSelectedIndex();
				
				if (movieName == null)
				{
					JOptionPane.showMessageDialog(contentPane, "未选择电影!!!","......",JOptionPane.ERROR_MESSAGE) ;
					return;
				}
				String M_id = VectorTemp.get(list.getSelectedIndex()).get(0);
				add_session as = new add_session(self,movieName,M_id);
				
				as.setVisible(true);
				
				
			}
		});
		btnNewButton_1.setBounds(196, 337, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton button = new JButton("删除场次");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane p = new JOptionPane();
			
				String selectData = movieTable.getSelectData(1); //S_id
				if (selectData == null)
				{
					JOptionPane.showMessageDialog(contentPane, "未选择场次!!!","......",JOptionPane.ERROR_MESSAGE) ;
					return;
				}
				
				int ret = JOptionPane.showConfirmDialog(contentPane, "确认删除？","......",JOptionPane.YES_NO_OPTION) ;
				if(ret== JOptionPane.OK_OPTION)
				{
					String deleteRow = "delete  from session where S_id =";
					deleteRow = deleteRow.concat("\"").concat(selectData).concat("\"");
					System.out.println(deleteRow);
				    movieTable.getUpdate(deleteRow, connection);
				    movieTable.getTable(view_query, connection);
				
				}
				else
				{
					return;
				}
			}
		});
		button.setBounds(377, 337, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("查看销售情况");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				query_xsqk Looking = new query_xsqk();
				Looking.setVisible(true);
				
				
			}
		});
		button_1.setBounds(539, 337, 179, 23);
		contentPane.add(button_1);
		
		JLabel label = new JLabel("请选择电影");
		label.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label.setBounds(25, 12, 106, 35);
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 59, 130, 301);
		contentPane.add(scrollPane);
		
		JLabel label_1 = new JLabel("电影名");
		label_1.setVerticalAlignment(SwingConstants.BOTTOM);
		scrollPane.setColumnHeaderView(label_1);
		
		list = new JList();
		
	
		list.setValueIsAdjusting(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		list.addMouseListener(this);
		
		JPanel panel = new JPanel() ;

		panel.setBounds(152, 92, 582, 219);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout());
		
		Vector<String> columnHeads = new Vector<String>();
		columnHeads.add("电影名");
		columnHeads.add("场次号");
		columnHeads.add("日期");
		columnHeads.add("放映厅号");
		
		movieTable = new EasySQLTable(panel,columnHeads);
		movieTable.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		//panel.add(movieTable);
		
		//取出电影名和电影ID，并存入VectorTemp和movieTemp
		String getmovie = "SELECT M_id,M_name FROM movie";
		ResultSet movieRs = movieTable.getRs(getmovie, connection);
		 VectorTemp = new Vector<Vector<String>>();
		 movieTemp = new Vector<String>();
	  try {
		  
		  VectorTemp = (movieTable.GetResultSet(movieRs));
		
		  for(Object t:  VectorTemp.toArray())
		  {
			 movieTemp.add(((Vector<String>) t).get(1)); 
		  }
		
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  
		
		list.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
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
		
		
		//movieTable.getTable(getmovie, connection);
		
		JLabel label_2 = new JLabel("可选场次列表");
		label_2.setBounds(417, 60, 84, 18);
		contentPane.add(label_2);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == list)
		{
			String name_click = (String) list.getSelectedValue();    //取出用户在电影列表中选中的电影名
			
			System.out.println(name_click);
			
			view_query = "select movie.M_name as 电影名,S_id as 场次,S_date as 日期,O_id as 放映厅号  from session ,movie where movie.M_id = session.M_id and  M_name = ";
			//view_query.concat(name_click);
			System.out.println(view_query);
			view_query = view_query.concat("\"").concat(name_click).concat("\"");
			
			System.out.println(view_query);
			
			
			movieTable.getTable(view_query, connection);  //显示提交请求并显示 场次表
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}


	

