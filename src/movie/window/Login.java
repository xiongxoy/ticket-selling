package movie.window;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import movie.util.DBOpration;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField E_id_Field;
	private JPasswordField passwordField;
	private JLabel about_label;
	private About about; 
	private JFrame self;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	

	/**
	 * Create the frame.
	 * 初始化界面组件，设置Listener
	 */
	public Login() {
		self = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 447, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		E_id_Field = new JTextField();
		E_id_Field.setBounds(109, 109, 243, 28);
		contentPane.add(E_id_Field);
		E_id_Field.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(109, 170, 243, 28);
		contentPane.add(passwordField);
		
		JLabel E_nr_Label = new JLabel("员工号");
		E_nr_Label.setBounds(38, 117, 54, 15);
		contentPane.add(E_nr_Label);
		
		JLabel Password_Label = new JLabel("密码");
		Password_Label.setBounds(38, 178, 39, 15);
		contentPane.add(Password_Label);
		
		JButton loginButton = new JButton("登陆");
		loginButton.setBounds(109, 273, 73, 32);
		contentPane.add(loginButton);
		
		
		JButton exitButton = new JButton("退出");
		exitButton.setBounds(267, 273, 73, 32);
		contentPane.add(exitButton);
		
		JLabel titleLable =  new JLabel("员工登陆");
		titleLable.setBounds(179, 34, 80, 28);
		titleLable.setFont(new Font("微软雅黑",  0, 20));
		contentPane.add(titleLable);
		
		about_label = new JLabel("关于影院售票系统 v0.1");
		about_label.setBounds(267, 312, 160, 32);
		
		about_label.addMouseListener(new MouseAdapter() {
			/**
			 * 点击about_label的时候，弹出“关于”框
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getSource() == about_label)
					if(about == null)
					{
						about = new About();
						about.setVisible(true);
					}
					else
					{
						about.setVisible(true);
					}
				
			}
		});
		
		contentPane.add(about_label);
		
		final JRadioButton radioButton = new JRadioButton("售票界面",true);
		buttonGroup.add(radioButton);
		radioButton.setBounds(52, 221, 77, 26);
		
		contentPane.add(radioButton);
		
		final JRadioButton radioButton_1 = new JRadioButton("经理界面");
		buttonGroup.add(radioButton_1);
		radioButton_1.setBounds(177, 221, 77, 26);
		contentPane.add(radioButton_1);
		
		final JRadioButton radioButton_2 = new JRadioButton("账户管理界面");
		buttonGroup.add(radioButton_2);
		radioButton_2.setBounds(300, 221, 123, 26);
		contentPane.add(radioButton_2);
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);
			}
		});
		
		
		loginButton.addActionListener(new ActionListener() {
		
			/**
			 *  处理“登录”被点击的事件，根据密码的正误和选择的登录界面进行反应
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String query = "select psw from login where E_id = " 
								+ "\"" + E_id_Field.getText() + "\"";
				Connection con = DBOpration.connectDB();
				
				ResultSet rs = null;
				try {
					rs = con.createStatement().executeQuery(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String psw = null;
				
				try {
					psw = (DBOpration.getARow(DBOpration.GetResultSet(rs), 0)).get(0);
				}
				catch (SQLException e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null,"输入错误，请检查用户名和密码" ,"错误" , JOptionPane.ERROR_MESSAGE);
					return;
				}
				catch (NoSuchFieldError e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"输入错误，请检查用户名和密码" ,"错误" , JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				boolean flag = false;
				String a = new String(passwordField.getPassword());
				
				if(psw.equals(a))
				{
					flag = true;
					
				}
				
				if(flag)
				{
					if(radioButton.isSelected())
					{
						SellWindow s = new SellWindow();
						s.setVisible(true);
						self.dispose();
					}
					
					if(radioButton_1.isSelected())
					{
						I_TRY i = new I_TRY();
						i.setVisible(true);
						self.dispose();
					}
					
					if(radioButton_2.isSelected())
					{
						show s = new show();
						s.setVisible(true);
						self.dispose();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"密码错误，请重新输入" ,"错误" , JOptionPane.ERROR_MESSAGE);
				}
		
				
			}
		});
	}
}
