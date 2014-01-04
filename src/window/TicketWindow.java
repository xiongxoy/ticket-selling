package movie.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;

import movie.util.DBOpration;
import movie.util.SeatState;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TicketWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tick_nr;
	private JComboBox typeBox;
	private JTextField timeBox;
	private SeatState seat;
	private JPanel seatPanel;
	private JLabel textField;
	private JLabel textField_1;
	private JLabel textField_2;
	private JLabel textField_3;
	private JLabel textField_4;
	private JLabel textField_5;
	private JFrame parent;
	
	
	private JFrame self;
	private Connection connection;

	
	
	public String getS_id()
	{
		return ((SellWindow)parent).getS_id();
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TicketWindow frame = new TicketWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 绘制座位分布
	 */
	public void paintNewSeat()
	{
		
		SellWindow par = (SellWindow) parent;
		String S_id = par.getSlectedData();
		
		try
		{
		seat = new SeatState(connection,S_id);
		}
		catch(NoSuchFieldError e)
		{
			
			this.dispose();
			throw new NoSuchFieldError(); 
		}
		
		seatPanel.removeAll();
		seatPanel.add(seat);
	}
	
	/**
	 * 初始化界面
	 */
	private void initiate()
	{
		//this.getParent().setEnabled(false);
		self = this;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 726, 679);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(138, 330, 505, 303);
		panel.setLayout(new BorderLayout());
		
		SellWindow par = (SellWindow) parent;
		String S_id = par.getSlectedData();
		
		try
		{
		seat = new SeatState(connection,S_id);
		}
		catch(NoSuchFieldError e)
		{
			
			this.dispose();
			throw new NoSuchFieldError(); 
		}
	
		panel.add(seat);
		contentPane.add(panel);
		
		seatPanel = panel;
		
		timeBox = new JTextField();
		timeBox.setText("18:00");
		timeBox.setEditable(false);
		timeBox.setBounds(386, 122, 68, 25);
		contentPane.add(timeBox);
		
		JLabel lblNewLabel = new JLabel("\u65F6\u6BB5");
		lblNewLabel.setBounds(342, 125, 26, 18);
		contentPane.add(lblNewLabel);
		
		typeBox = new JComboBox();
		typeBox.setModel(new DefaultComboBoxModel(new String[] {"\u666E\u901A\u7968", "\u5B66\u751F\u7968"}));
		typeBox.setBounds(386, 82, 108, 25);
		contentPane.add(typeBox);
		
		JLabel label = new JLabel("\u7C7B\u578B");
		label.setBounds(342, 85, 26, 18);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u6570\u91CF");
		label_1.setBounds(342, 203, 26, 18);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u5F20");
		label_2.setBounds(461, 203, 13, 18);
		contentPane.add(label_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(20, 67, 280, 194);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_5 = new JLabel("\u7535\u5F71\u540D:");
		label_5.setBounds(25, 12, 42, 18);
		panel_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u4E0A\u6620\u65F6\u95F4:");
		label_6.setBounds(12, 72, 55, 18);
		panel_1.add(label_6);
		
		JLabel label_7 = new JLabel("\u56FD\u5BB6/\u5730\u533A:");
		label_7.setBounds(9, 100, 58, 18);
		panel_1.add(label_7);
		
		JLabel label_8 = new JLabel("\u8BED\u8A00:");
		label_8.setBounds(38, 132, 29, 18);
		panel_1.add(label_8);
		
		JLabel label_9 = new JLabel("\u5BFC\u6F14:");
		label_9.setBounds(38, 42, 29, 18);
		panel_1.add(label_9);
		
		JLabel label_10 = new JLabel("\u7968\u4EF7:");
		label_10.setBounds(38, 162, 29, 18);
		panel_1.add(label_10);
		
		textField = new JLabel();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setText("\u7535\u5F71\u540D");
		textField.setBorder(null);
		textField.setBounds(79, 12, 189, 18);
		panel_1.add(textField);
		
		textField_1 = new JLabel();
		textField_1.setText("\u5BFC\u6F14");
		textField_1.setBorder(null);
		textField_1.setBounds(79, 42, 189, 18);
		panel_1.add(textField_1);
		
		textField_2 = new JLabel();
		textField_2.setText("\u65F6\u95F4");
		textField_2.setBorder(null);
		textField_2.setBounds(79, 72, 71, 18);
		panel_1.add(textField_2);
		
		textField_3 = new JLabel();
		textField_3.setText("\u56FD\u5BB6");
		textField_3.setBorder(null);
		textField_3.setBounds(79, 100, 168, 18);
		panel_1.add(textField_3);
		
		textField_4 = new JLabel();
		textField_4.setText("\u8BED\u8A00");
		textField_4.setBorder(null);
		textField_4.setBounds(79, 132, 168, 18);
		panel_1.add(textField_4);
		
		textField_5 = new JLabel();
		textField_5.setText("\u7968\u4EF7");
		textField_5.setBorder(null);
		textField_5.setBounds(79, 162, 55, 18);
		panel_1.add(textField_5);
		
		JLabel label_3 = new JLabel("\u7535\u5F71\u57FA\u672C\u4FE1\u606F");
		label_3.setBounds(30, 37, 78, 18);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u5EA7\u4F4D\u9884\u89C8");
		label_4.setBounds(22, 279, 52, 18);
		contentPane.add(label_4);
		
		tick_nr = new JTextField();
		tick_nr.setText("0");
		tick_nr.setEditable(false);
		tick_nr.setBounds(386, 199, 68, 25);
		contentPane.add(tick_nr);
		
		JButton buyButton = new JButton("\u786E\u8BA4\u8D2D\u4E70");
		buyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TicketConfirm tc = new TicketConfirm((TicketWindow)self, seat);
			}
		});
		buyButton.setBounds(575, 80, 98, 28);
		contentPane.add(buyButton);
		
		JButton cancelButton = new JButton("\u53D6\u6D88");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
		cancelButton.setBounds(575, 140, 98, 28);
		contentPane.add(cancelButton);
		
		JLabel label_11 = new JLabel("1");
		label_11.setBounds(153, 301, 7, 18);
		contentPane.add(label_11);
		
		JLabel label_12 = new JLabel("2");
		label_12.setBounds(201, 301, 7, 18);
		contentPane.add(label_12);
		
		JLabel label_13 = new JLabel("3");
		label_13.setBounds(250, 301, 7, 18);
		contentPane.add(label_13);
		
		JLabel label_14 = new JLabel("4");
		label_14.setBounds(297, 301, 7, 18);
		contentPane.add(label_14);
		
		JLabel label_15 = new JLabel("5");
		label_15.setBounds(347, 301, 16, 18);
		contentPane.add(label_15);
		
		JLabel label_16 = new JLabel("6");
		label_16.setBounds(396, 300, 26, 18);
		contentPane.add(label_16);
		
		JLabel label_17 = new JLabel("7");
		label_17.setBounds(447, 301, 7, 18);
		contentPane.add(label_17);
		
		JLabel label_18 = new JLabel("8");
		label_18.setBounds(495, 301, 7, 18);
		contentPane.add(label_18);
		
		JLabel label_19 = new JLabel("9");
		label_19.setBounds(544, 301, 7, 18);
		contentPane.add(label_19);
		
		JLabel label_20 = new JLabel("10");
		label_20.setBounds(588, 301, 14, 18);
		contentPane.add(label_20);
		
		JLabel label_21 = new JLabel("\u5EA7\u4F4D\u53F7");
		label_21.setBounds(363, 279, 39, 18);
		contentPane.add(label_21);
		
		JLabel label_22 = new JLabel("\u6392\u53F7");
		label_22.setBounds(82, 468, 26, 18);
		contentPane.add(label_22);
		
		JLabel label_23 = new JLabel("1");
		label_23.setBounds(119, 340, 7, 18);
		contentPane.add(label_23);
		
		JLabel label_24 = new JLabel("2");
		label_24.setBounds(119, 370, 7, 18);
		contentPane.add(label_24);
		
		JLabel label_25 = new JLabel("3");
		label_25.setBounds(119, 394, 7, 18);
		contentPane.add(label_25);
		
		JLabel label_26 = new JLabel("4");
		label_26.setBounds(120, 425, 13, 18);
		contentPane.add(label_26);
		
		JLabel label_27 = new JLabel("5");
		label_27.setBounds(120, 455, 13, 18);
		contentPane.add(label_27);
		
		JLabel label_28 = new JLabel("6");
		label_28.setBounds(119, 485, 26, 18);
		contentPane.add(label_28);
		
		JLabel label_29 = new JLabel("7");
		label_29.setBounds(119, 511, 14, 18);
		contentPane.add(label_29);
		
		JLabel label_30 = new JLabel("8");
		label_30.setBounds(119, 541, 14, 18);
		contentPane.add(label_30);
		
		JLabel label_31 = new JLabel("9");
		label_31.setBounds(119, 571, 14, 18);
		contentPane.add(label_31);
		
		JLabel label_32 = new JLabel("10");
		label_32.setBounds(112, 601, 14, 18);
		contentPane.add(label_32);
		
		JLabel label_33 = new JLabel("\u653E\u6620\u5385");
		label_33.setBounds(329, 166, 39, 11);
		contentPane.add(label_33);
		
		txtXx = new JTextField();
		txtXx.setText("XX");
		txtXx.setEditable(false);
		txtXx.setBounds(386, 160, 68, 22);
		contentPane.add(txtXx);
		
		JLabel label_34 = new JLabel("\u53F7");
		label_34.setBounds(461, 164, 13, 18);
		contentPane.add(label_34);
		
	
		// 使用一个线程不停地统计被选择的座位数，然后设置票的数量
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
				{
					tick_nr.setText(Integer.toString(seat.getSelected()));
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		t.start();
	}
	
	/**
	 * 设置各文本的取值
	 */
	private void setValue()
	{
		//(SellWindow)parent;
		SellWindow t = (SellWindow) parent;
		String S_id = t.getSlectedData();
	
		String query = "SELECT M_name, S_time, M_time, M_date, director, language, region, S_price, O_id FROM cinema.movie,cinema.session " 
						+ "where movie.M_id = session.M_id and session.S_id = \"" + S_id + "\"";
		
		ResultSet rs = DBOpration.getRs(query, connection);
		
		Vector<String> row = null;
		try {
			row = DBOpration.GetResultSet(rs).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(row.size());
		System.out.println(row.get(0));
		
		textField.setText(row.get(0));
		textField_1.setText(row.get(4));
		textField_2.setText(row.get(3));
		textField_3.setText(row.get(6));
		textField_4.setText(row.get(5));
		textField_5.setText(row.get(7));
		txtXx.setText(row.get(8));
		
		timeBox.setText(row.get(1));
		
	}
	
	public int getTicketNr()
	{
		return Integer.parseInt(tick_nr.getText());
	}
	
	public String getType()
	{
		return (String)typeBox.getSelectedItem();
	}
	
	public double getSinglePrice()
	{
		double price;
		double discount = 0;
		
		if( typeBox.getSelectedItem() == "普通票" )
		{
			discount = 1;
		}
		
		if( typeBox.getSelectedItem() == "学生票")
		{
			discount = 0.5;
		}
		
		price = Double.parseDouble(textField_5.getText()) * discount;
		
		return price;
	}
	
	public String getMoiveName()
	{
		return textField.getText();
	}
	
	public String getSessionTime()
	{
		return timeBox.getText();
	}
	
	
	private JTextField txtXx;
	
	
	public String getRoomNr()
	{
		return txtXx.getText();
	}
	public double getTotalPrice()
	{
		double price;
		double discount = 0;
		
		if( typeBox.getSelectedItem() == "普通票" )
		{
			discount = 1;
		}
		
		if( typeBox.getSelectedItem() == "学生票")
		{
			discount = 0.5;
		}
		
		price = Double.parseDouble(textField_5.getText());
		price = discount * price * Integer.parseInt(tick_nr.getText());
		
		return price;
			
	}


	@Deprecated
	public TicketWindow() {
		initiate();
		//setValue();
	}
	

	/**
	 * 初始化座位选择界面
	 * @param par 父窗口
	 * @param con 数据库连接
	 */
	public TicketWindow(SellWindow par, Connection con) {
		connection = con;
		parent = par;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		try
		{
		initiate();
		}
		catch (NoSuchFieldError e) {
			// TODO: handle exception
			
			throw new NoSuchFieldError();
		}
		parent.setEnabled(false);
		setValue();
		
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 重载窗口的processWindowEvent函数，在窗口被关闭后，唤醒父窗口
	 */
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		if(e.getID() == WindowEvent.WINDOW_CLOSED)
		{
			if(parent != null)
			{
				parent.setEnabled(true);
				parent.setVisible(true);
			}
		}
		super.processWindowEvent(e);
		
	}
}
