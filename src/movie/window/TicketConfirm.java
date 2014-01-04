package movie.window;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import movie.util.DBOpration;
import movie.util.SeatState;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.sql.Connection;

public class TicketConfirm extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtRoom;
	private JTextField txtTime;
	private JTextField txtType;
	private JTextField txtNr;
	private JTextField txtTotal;
	private SeatState seat;
	private TicketWindow par;
	private JFrame self;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TicketConfirm frame = new TicketConfirm(null,null);
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
	public TicketConfirm(TicketWindow par_t, SeatState seat_par) {
		
		seat = seat_par;
		self = this;
		this.par = par_t;
		
		par.setEnabled(false);
		par.setVisible(false);

		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("\u786E\u8BA4");
		btnNewButton.addActionListener(new ActionListener() {
			/**
			 * 处理确认购买，实现对数据库的操作
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Connection con = DBOpration.connectDB();
				String S_id = par.getS_id();
				String delete_sql = "Delete From seat where S_id = " + "\"" + S_id +"\"" + "and seatid = "; 
				
				
				String R_data = add_session.nowTime().substring(0, 14);
				String sellprice = Double.toString(par.getSinglePrice());
				String R_id_no_seat = R_data.substring(0, 8) + par.getRoomNr() + S_id.substring(10, 13);
				String R_id = null;
				
				String add_sql = null;
						         
				
				
				for(Point2D p:seat.getSeatVector())
				{
					DBOpration.getUpdate(delete_sql + 
										 "\""  +
										 add_session.int2String(((int)p.getX()) * 10 + ((int)p.getY())) +
										 "\""
										 , con);
					
					 R_id  = R_id_no_seat + 
							 add_session.int2String(((int)p.getX()) * 10 + ((int)p.getY()));
						     
					add_sql  = "insert into record values(" +
					           "\"" + R_id + "\"," + 
					           "\"" + S_id + "\"," +
					           sellprice + "," + "\"" + R_data.substring(0,12) + "\"" + ", \"00\")";
					
					DBOpration.getUpdate(add_sql, con);
					
					System.out.println(add_sql);
					           
		
				}
				
				par.paintNewSeat();
				
				self.dispose();
			}
		});
		btnNewButton.setBounds(69, 254, 98, 28);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u53D6\u6D88");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
		btnNewButton_1.setBounds(236, 254, 98, 28);
		contentPane.add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(34, 58, 327, 171);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\u7535\u5F71\u540D:");
		label.setBounds(23, 19, 49, 18);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u653E\u6620\u5385:");
		label_1.setBounds(23, 56, 49, 18);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u65F6\u6BB5:");
		label_2.setBounds(33, 93, 29, 18);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("\u7C7B\u578B:");
		label_3.setBounds(33, 130, 29, 18);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("\u6570\u91CF:");
		label_4.setBounds(184, 19, 37, 18);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("\u603B\u4EF7:");
		label_5.setBounds(184, 93, 37, 18);
		panel.add(label_5);
		
		txtName = new JTextField();
		txtName.setText("name");
		txtName.setEditable(false);
		txtName.setColumns(10);
		txtName.setBounds(76, 17, 86, 22);
		panel.add(txtName);
		
		txtRoom = new JTextField();
		txtRoom.setText("room");
		txtRoom.setEditable(false);
		txtRoom.setColumns(10);
		txtRoom.setBounds(76, 54, 86, 22);
		panel.add(txtRoom);
		
		txtTime = new JTextField();
		txtTime.setText("time");
		txtTime.setEditable(false);
		txtTime.setColumns(10);
		txtTime.setBounds(76, 91, 86, 22);
		panel.add(txtTime);
		
		txtType = new JTextField();
		txtType.setText("type");
		txtType.setEditable(false);
		txtType.setColumns(10);
		txtType.setBounds(76, 128, 86, 22);
		panel.add(txtType);
		
		txtNr = new JTextField();
		txtNr.setText("nr");
		txtNr.setEditable(false);
		txtNr.setColumns(10);
		txtNr.setBounds(224, 19, 86, 22);
		panel.add(txtNr);
		
		txtTotal = new JTextField();
		txtTotal.setText("total");
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBounds(224, 91, 86, 22);
		panel.add(txtTotal);
		
		JLabel label_6 = new JLabel("\u8D2D\u7968\u4FE1\u606F\u786E\u8BA4");
		label_6.setBounds(150, 28, 78, 18);
		contentPane.add(label_6);
		
		setValue();
		this.setVisible(true);
	}
	
	/**
	 * 设置文本取值
	 */
	private void setValue()
	{
		txtName.setText(par.getMoiveName());
		txtRoom.setText(par.getRoomNr()+" 号");
		txtTime.setText(par.getSessionTime());
		txtTotal.setText(Double.toString(par.getTotalPrice()) +" 元");
		txtType.setText(par.getType());
		txtNr.setText(Integer.toString(par.getTicketNr())+" 张");
	}
	
	/**
	 * 重载窗口的processWindowEvent函数，在窗口被关闭后，唤醒父窗口
	 */
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		//super(e);
		if(e.getID() == WindowEvent.WINDOW_CLOSED)
		{
			if(par != null)
			{
				par.setVisible(true);
				par.setEnabled(true);
			}
		}
		super.processWindowEvent(e);
		
	}
}
