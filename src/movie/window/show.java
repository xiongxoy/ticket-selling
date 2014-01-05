package movie.window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import movie.util.DBOpration;
import movie.util.EasySQLTable;

//import EasySQLTable;
//import movie.window.add_session;



import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class show extends JFrame {

	private JPanel contentPane;
	private EasySQLTable employee_Table;
	private Connection connection;
	private String view_query;
	private Vector<Vector<String>> VectorTemp ;
	private Vector<String> employee_Temp ;
	private show self;

	private JList list;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					show frame = new show();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EasySQLTable getMyTable()
	{
		return employee_Table;
	}
	/**
	 * Create the frame.
	 */
	public show() {
		self = this;
		connection = DBOpration.connectDB();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 805, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(90, 64, 606, 219);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout());
		
		Vector<String> columnHeads = new Vector<String>();
		columnHeads.add("E_id");
		columnHeads.add("E_name");
		columnHeads.add("sex");
		columnHeads.add("birthday");
		columnHeads.add("duty");
		columnHeads.add("state");
		employee_Table = new EasySQLTable(panel,columnHeads);
		employee_Table.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final String getemployee = "SELECT * FROM employee";
		employee_Table.getTable(getemployee, connection);

		JLabel lblNewLabel = new JLabel("员工信息表");
		lblNewLabel.setBounds(339, 10, 85, 20);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		contentPane.add(lblNewLabel);
		
		JButton button_1 = new JButton("删除记录");
		button_1.setBounds(198, 314, 93, 23);
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectData = employee_Table.getSelectData(0); //E_id
				if (selectData == null)
				{
					JOptionPane.showMessageDialog(contentPane, "未选择记录！","......",JOptionPane.ERROR_MESSAGE) ;
					return;
				}
				
				int ret = JOptionPane.showConfirmDialog(contentPane, "确认删除？","......",JOptionPane.YES_NO_OPTION) ;
				if(ret== JOptionPane.OK_OPTION)
				{
					String deleteRow = "delete  from employee where E_id =";
					deleteRow = deleteRow.concat("\"").concat(selectData).concat("\"");
					System.out.println(deleteRow);
					employee_Table.getUpdate(deleteRow, connection);
					employee_Table.getTable(getemployee, connection);
				}
				else
				{
					return;
				}
			}
		});
		contentPane.add(button_1);
		
		JButton button = new JButton("增加员工");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				add_em ae = new add_em(self);
				ae.setVisible(true);
			
			}
		});
		button.setBounds(423, 314, 93, 23);
		contentPane.add(button);
	}
}
