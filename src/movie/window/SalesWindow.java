package movie.window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import movie.util.DBOpration;
import movie.util.EasySQLTable;

/**
 * ������������������
 * @author ʵ����
 *
 */
public class SalesWindow extends JFrame {

	private JPanel contentPane;
	private String movieName ;  //ѡ�е�Ӱ��
	private String M_id;      	// ѡ�е�ӰID
	private Connection connection;
	private SalesWindow self;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SalesWindow frame = new SalesWindow();
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
	public SalesWindow() {
		
		//��ʼ��
		movieName = "" ;
		M_id = "";
		self = this;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		String movie_query = "select movie.M_name,region,class,language,M_id from movie";
		String room_query = "select O_id from room";
		String session_query = "select S_id from session";
		connection = DBOpration.connectDB();
		
		Vector<Vector<String>> movie_vs = null;
		try {
			movie_vs = DBOpration.GetResultSet(DBOpration.getRs(movie_query,connection));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<Vector<String>> room_vs = null;
		try {
			room_vs = DBOpration.GetResultSet(DBOpration.getRs(room_query,connection));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<Vector<String>> session_vs = null;
		try {
			session_vs = DBOpration.GetResultSet(DBOpration.getRs(session_query,connection));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Vector<String> movie_name_vector = DBOpration.getARow(movie_vs, 0);
		
		final Vector<String> M_id_vector = DBOpration.getARow(movie_vs, 4);
		
		movie_name_vector.add(0, "");
		movie_name_vector.add(1,"ȫ����Ӱ");
		Object[] movie_name_choose = movie_name_vector.toArray();
		final JComboBox comboBox_movieName = new JComboBox(movie_name_choose);
		comboBox_movieName.setBounds(10, 65, 155, 25);
		contentPane.add(comboBox_movieName);
		
		
		
		Vector<String> region_vector =  DBOpration.getARow(movie_vs, 1);
		region_vector.add(0,"");
		
		Object[] region_choose = region_vector.toArray();
		
		Vector<String> language_vector =  DBOpration.getARow(movie_vs, 3);
				language_vector.add(0,"");
		Object[] language_choose = language_vector.toArray();
		
		Vector<String> room_vector =  DBOpration.getARow(room_vs, 0);
		room_vector.add(0,"");
		Object[] room_choose = room_vector.toArray();
	
		Vector<String> session_id_vector =  DBOpration.getARow(session_vs, 0);
		session_id_vector.add(0,"");
		Object[] session_id__choose = session_id_vector.toArray();
		
		Vector<String> class_vector =  DBOpration.getARow(movie_vs, 2);
		class_vector.add(0,"");
		Object[] class_choose = class_vector.toArray();
		
		
		JLabel label = new JLabel("��Ӱ��");
		label.setBounds(27, 26, 54, 15);
		contentPane.add(label);
		
		JPanel panel = new JPanel();
		panel.setBounds(192, 79, 582, 219);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout());
		
		Vector<String> columnHeads = new Vector<String>();
		columnHeads.add("��Ӱ��");
		columnHeads.add("���κ�");
		columnHeads.add("������������");
		columnHeads.add("������");
		columnHeads.add("Ʊ��");
		
		//columnHeads.add("Ƭ�������ӣ�");
		
		final EasySQLTable xsqkTable = new EasySQLTable(panel,columnHeads);
		//panel.add(xsqkTable, BorderLayout.CENTER);
		
		JButton button = new JButton("�鿴���");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//ȡ����ѡ��Ӱ�ĵ�Ӱ��
				movieName =(String) comboBox_movieName.getSelectedItem();
				
		      // ��һ��Ϊ�գ��ڶ���Ϊȫ����Ӱ����������
				if (comboBox_movieName.getSelectedIndex() >=2)
				{
					M_id = M_id_vector.get(comboBox_movieName.getSelectedIndex()-2);
				}
				System.out.println(M_id);
				
				System.out.println(movieName);
				if (M_id != "" )    //��ѡ����ĳһ����Ӱʱ
				{
					String query1 = "SELECT M_name as ��Ӱ��, S_id as ���κ�,count(*) as ������������ ,count(*)/100 as ������ , sum(record.sellprice) as Ʊ��  from  record,movie where movie.M_id = " ;
							
					String getM_id= "";
					getM_id = getM_id.concat("\"").concat(M_id).concat("\"");
					System.out.println(M_id);
					
					query1 = query1.concat(getM_id).concat(" and record.S_id in (select distinct S_id from session where session.M_id =movie.M_id) group by S_id;");
					System.out.println(getM_id);
					//System.out.println(query1);
					xsqkTable.getTable(query1, connection);
					
					M_id = "";
				}
				else if (movieName == "ȫ����Ӱ")  //��ѡ����ǵ���ȫ����Ӱ��һѡ��ʱ 
				{
					String query2_head = "select M_name as ��Ӱ�� ,sum(����) as ���������� ,sum(����Ʊ��) as Ʊ�� ,avg(����������) as ��������  from ";
					String query2_body = "(select M_id,M_name,count(*) as ����,sum(record.sellprice) as ����Ʊ��,count(*)/100 as ���������� from record,movie ";
					String query2_tail = "where  record.S_id in  (select distinct S_id from session where session.M_id =movie.M_id) group by S_id)  as aaa group by M_id";
    
                    query2_head = query2_head + query2_body + query2_tail;
       
                     xsqkTable.getTable(query2_head, connection);

					
				}
				
				
			}
		});
		button.setBounds(189, 304, 101, 25);
		contentPane.add(button);
		
		JButton button_1 = new JButton("ȡ��");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				self.dispose();
			}
		});
		button_1.setBounds(608, 304, 101, 25);
		contentPane.add(button_1);
		
		JLabel lblNewLabel = new JLabel("�������");
		lblNewLabel.setBounds(430, 26, 137, 15);
		contentPane.add(lblNewLabel);
		
		
		
		
	}
}
