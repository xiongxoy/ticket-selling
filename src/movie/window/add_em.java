package movie.window;


import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.WindowConstants;

import movie.util.DBOpration;

//import movie.window.DBOpration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;


public class add_em extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JFrame parent;
	private add_em self;
	private String E_name,sex,birthday,duty,state,E_id,tmp;
    private Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JFrame yy = new JFrame();
					add_em frame = new add_em(yy);
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
	public add_em(JFrame parent_temp) {
		
		connection = DBOpration.connectDB();
		
		parent = parent_temp;
		parent.setEnabled(false);
		self = this;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 358, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//JLabel lblNewLabel = new JLabel("职工号\r\n");
		//lblNewLabel.setBounds(41, 41, 54, 15);
		//contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("增加员工信息");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(97, 31, 120, 24);
		contentPane.add(label);
		
		JLabel lblNewLabel_1 = new JLabel("姓名");
		lblNewLabel_1.setBounds(41, 97, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("性别");
		lblNewLabel_2.setBounds(41, 153, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("出生年月");
		lblNewLabel_3.setBounds(41, 209, 54, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("职责");
		lblNewLabel_4.setBounds(41, 265, 54, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("状态");
		lblNewLabel_5.setBounds(41, 321, 54, 15);
		contentPane.add(lblNewLabel_5);
		
		
		
		
		//textField = new JTextField();

		//textField.setText("\r\n");
		//textField.setBounds(178, 36, 111, 21);
		//contentPane.add(textField);
		//textField.setColumns(10);
		
		
		
		
		textField_1 = new JTextField();
		textField_1.setBounds(178, 94, 66, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
		
		
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女"}));
		comboBox.setBounds(178, 150, 79, 21);
		contentPane.add(comboBox);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"开发", "维护", "经理"}));
		comboBox_1.setBounds(178, 264, 79, 21);
		contentPane.add(comboBox_1);
		
		final JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"在岗", "退休 ", "离职", "病假"}));
		comboBox_2.setBounds(178, 321, 79, 21);
		contentPane.add(comboBox_2);
		
		
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.setEnabled(true);
			
				E_name = textField_1.getText();
		
		
				birthday = textField_2.getText();
				sex = (String) comboBox.getSelectedItem();
				duty = (String) comboBox_1.getSelectedItem();
				state = (String) comboBox_2.getSelectedItem();
				tmp = "SELECT count(*)+1 FROM cinema.employee";
				ResultSet roomRs = DBOpration.getRs(tmp, connection);
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
				tmp = (String) ((roomTemp.toArray())[0]);
				
				tmp = add_session.int2String(Integer.parseInt(tmp));
				System.out.println("kkk");
				//System.out.println(E_name.length());
				 if (E_name.length() == 0 )
					{
						JOptionPane.showMessageDialog(contentPane, "未输入姓名!","......",JOptionPane.ERROR_MESSAGE) ;
					
					}
				  else if (birthday == "")
				  {
					  JOptionPane.showMessageDialog(contentPane, "未输入出生年月!","......",JOptionPane.ERROR_MESSAGE) ;
				  }
				 else if (!checkDate(birthday))
				  {
					  JOptionPane.showMessageDialog(contentPane, "出生年月不正确!","......",JOptionPane.ERROR_MESSAGE) ;
				  }
				
				  else
				  {	 
					  	 E_id = "1" + birthday.substring(0, 6) + tmp;
						 String add_query = "insert into employee(E_id,E_name,sex,birthday,duty,state)  values(";
						
						 String getE_id = "";
						 //getE_id = getE_id.concat("\"").concat(E_id).concat(birthday.substring(0, 6)).concat(tmp).concat("\"");
						 getE_id = getE_id.concat("\"").concat(E_id).concat("\"");
						 
						 
						 String getE_name = "";
						 getE_name = getE_name.concat("\"").concat(E_name).concat("\"");
						 
						 String getsex = "";
						 getsex = getsex.concat("\"").concat(sex).concat("\"");
						 
						 String getbirthday = "";
						 getbirthday = getbirthday.concat("\"").concat(birthday).concat("\"");
						 
						 String getduty = "";
						 getduty = getduty.concat("\"").concat(duty).concat("\"");
						 
						 String getstate = "";
						 getstate = getstate.concat("\"").concat(state).concat("\"");
					
					add_query = add_query.concat(getE_id).concat(",").concat(getE_name).concat(",").concat(getsex);
					add_query = add_query.concat(",").concat(getbirthday).concat(",").concat(getduty).concat(",").concat(getstate).concat(")").concat(";");
					  
					System.out.println(add_query);
					DBOpration.getUpdate(add_query, connection);
					/*String view_query = "select movie.M_name as 电影名,S_id as 场次,S_date as 日期,O_id as 放映厅号  from session ,movie where movie.M_id = session.M_id and  movie.M_id = ";
					view_query = view_query.concat(getM_id);
					System.out.println(view_query);*/
					String getemployee = "SELECT * FROM employee";
					((show)parent).getMyTable().getTable(getemployee, connection);	  
					self.dispose();
				  }
			}
		});
		btnNewButton.setBounds(49, 398, 93, 23);
		contentPane.add(btnNewButton);
		
			
		
		JButton btnNewButton_1 = new JButton("取消");
		btnNewButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//parent.setEnabled(true);
					self.dispose();
			}
		});
		btnNewButton_1.setBounds(178, 398, 93, 23);
		contentPane.add(btnNewButton_1);
		
		
		
		
		
		
		textField_2 = new JTextField();
		textField_2.setBounds(178, 206, 111, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
	}
	
	
	
	
    public static boolean checkDate(String sourceDate){
        if(sourceDate==null){
            return false;
        }
        try {
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
               dateFormat.setLenient(false);
               dateFormat.parse(sourceDate);
               return true;
        } catch (Exception e) {
        }
         return false;
    }
    
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		//super(e);
		if(e.getID() == WindowEvent.WINDOW_CLOSED)
		{
			if(parent != null)
			{
				parent.setVisible(true);
				parent.setEnabled(true);
			}
		}
		super.processWindowEvent(e);
	}
    
}
