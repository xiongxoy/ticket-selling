package movie.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.SpinnerNumberModel;

import movie.util.DBOpration;




/**
 * 增加场次主界面
 * @author 实验室
 *
 */
public class add_session extends JFrame {  

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private add_session self;
    private String price;
    private String clock,minute;
    private String room;
    private JFrame parent;
    private Connection connection;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JFrame no = new JFrame();
					add_session frame = new add_session(no,"XXXX","1");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public add_session( JFrame parent_temp,final String movieName,final String M_id) {
		
		System.out.println(nowTime());
		connection = DBOpration.connectDB();
		
		
		
		parent = parent_temp;
		parent.setEnabled(false);
		self = this;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String getRoom = "SELECT O_id FROM room";
		ResultSet roomRs = DBOpration.getRs(getRoom, connection);
		Vector<String> roomTemp  =new Vector<String>();
		 try {
			  
			 Vector<Vector<String>> VectorTemp = (DBOpration.GetResultSet(roomRs));
			
			  for(Object t:  VectorTemp.toArray())
			  {
				 roomTemp.add(((Vector<String>) t).get(0)); 
			  }
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 roomTemp.add(0, "");
		 Object[] roomS = roomTemp.toArray();
		 
		final JComboBox comboBox_place = new JComboBox(roomS);
		comboBox_place.setBounds(181, 73, 96, 21);
		contentPane.add(comboBox_place);
		
		
		JLabel label = new JLabel("\u653E\u6620\u5385\u53F7");
		label.setBounds(94, 72, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u7535\u5F71\u540D");
		label_1.setBounds(94, 27, 54, 15);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(181, 30, 144, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(movieName);
		textField.setEditable(false);
		
		JLabel label_2 = new JLabel("\u65F6\u95F4\u6BB5");
		label_2.setBounds(94, 117, 54, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u7968\u4EF7");
		label_3.setBounds(94, 162, 54, 15);
		contentPane.add(label_3);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(181, 159, 144, 21);
		contentPane.add(textField_1);
		
		final JSpinner spinner_minute = new JSpinner();
		spinner_minute.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		spinner_minute.setBounds(300, 113, 39, 22);
		contentPane.add(spinner_minute);
		
		final JSpinner spinner_clock = new JSpinner();
		spinner_clock.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		spinner_clock.setBounds(208, 114, 46, 22);
		contentPane.add(spinner_clock);
		
		
		JLabel label_4 = new JLabel("\u65F6");
		label_4.setBounds(181, 117, 17, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u5206");
		label_5.setBounds(273, 117, 17, 18);
		contentPane.add(label_5);
		
		JButton button = new JButton("\u786E\u8BA4");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.setEnabled(true);
				  price = textField_1.getText();
					int int_Clock =  (Integer) spinner_clock.getValue();
					
				
					int int_Minute =  (Integer) spinner_minute.getValue();
					
					room = (String) comboBox_place.getSelectedItem();
				  if (!isPosNumber(price))
					{
						JOptionPane.showMessageDialog(contentPane, "价格没有正确输入!!!","......",JOptionPane.ERROR_MESSAGE) ;
					
					}
				  else if (room == "")
				  {
					  JOptionPane.showMessageDialog(contentPane, "没有选择放映厅!!!","......",JOptionPane.ERROR_MESSAGE) ;
				  }
				  else
				  {//构造增加语句
					 clock = int2String(int_Clock);
					 minute = int2String(int_Minute);
					 
					 String add_query = "insert into session(S_id,M_id,S_time,S_date,O_id,S_price)  values(";
					 String getS_id ="";
					getS_id = getS_id.concat("\"").concat( nowTime()).concat("\"");
					 String getM_id = "";
					 getM_id = getM_id.concat("\"").concat(M_id).concat("\"");		
					 String getS_time ="";
					 getS_time = getS_time.concat("\"").concat(clock).concat(":").concat(minute).concat("\"");
					 
					 String getS_date = "";
					 getS_date = nowTime().substring(0, 8);
					 System.out.println(getS_date);
				String getO_id ="";
				
				getO_id = getO_id.concat("\"").concat(room).concat("\"");
				
				String getPrice = "";
				getPrice = getPrice.concat("\"").concat(price).concat("\"");
				
				add_query = add_query.concat(getS_id).concat(",").concat(getM_id).concat(",").concat(getS_time);
				add_query = add_query.concat(",").concat(getS_date).concat(",").concat(getO_id).concat(",").concat(getPrice).concat(")").concat(";");
				
				
				System.out.println(add_query);
					 DBOpration.getUpdate(add_query, connection);
					 int seatid = 0;
					 
					String add_seat = "insert into seat(S_id,seatid,state) values(";
					//每个场次增加99个座位
					for (seatid=0;seatid <=99;seatid++)
					{
					
					String add_seat_1 = add_seat+getS_id+","+"\""+int2String(seatid)+"\""+","+"\""+"Y"+"\""+");";
					System.out.println(add_seat_1);
					DBOpration.getUpdate(add_seat_1, connection);
			
					}
					//刷新场次管理界面的那个显示对应电影的场次表
					String view_query = "select movie.M_name as 电影名,S_id as 场次,S_date as 日期,O_id as 放映厅号  from session ,movie where movie.M_id = session.M_id and  movie.M_id = ";
					view_query = view_query.concat(getM_id);
					System.out.println(view_query);
					
					((I_TRY)parent).getMyTable().getTable(view_query, connection);
				self.dispose();
				  }
			}
		});
		button.setBounds(41, 206, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.setEnabled(true);
			
				self.dispose();
			}
		});
		button_1.setBounds(311, 206, 93, 23);
		contentPane.add(button_1);
	
	
	}
	//让父面板在关闭时复活
	@Override
	protected  void processWindowEvent(WindowEvent e) 
	{
		if(e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			parent.setEnabled(true);
		}
		
		super.processWindowEvent(e);
		
	}
	 
	
	//是否为整数
	public static boolean isInteger(String value) {
		try {
		int temp = Integer.parseInt(value);
		if (temp <=0)
		{
			return false;
		}
		return true;
		} catch (NumberFormatException e) {
		return false;
		}
		}

        //是否为浮点数
		public static boolean isDouble(String value) {
		try {
		double temp = Double.parseDouble(value);
		if (temp <= 0)
		{
			return false;
		}
		if (value.contains("."))
		return true;
		return false;
		} catch (NumberFormatException e) {
		return false;
		}
		}


		public static boolean isPosNumber(String value) {
		return isInteger(value) || isDouble(value); }
		//小于100的正整数转成2位STRING
		public static String int2String(int num)
		{
			if (num < 10)
			{
				return "0"+String.valueOf(num);
			}
			else
			{
				return String.valueOf(num);
			}
		}
		//取得现在的系统时间，年月日时分秒
		public static String nowTime() {
			  Calendar c = Calendar.getInstance();
			  c.setTimeInMillis(new Date().getTime());
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			  return dateFormat.format(c.getTime());
			 }
		
}
