package movie.util;



import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * 
 * @author ZX from Victory 5 
 * �ṩ�򵥵ı�񣬲���ȡ���ݺ���ʵ�Ĳ�����װ������
 */
public class EasySQLTable extends JPanel {


	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	// GUI��������
	private JTable table;
	private JPanel panel;
	


	/**
	 * ���ƶ���panel�ϻ���һ�ű��
	 * @param panel
	 */
	public EasySQLTable(JPanel panel) {
		// TODO Auto-generated constructor stub
		
		this.panel = panel;
		Vector<String> columnHeads = new Vector<String>();
		columnHeads.add("��Ӱ��");
		columnHeads.add("��ӳ��");
		columnHeads.add("Ʊ�ۣ�Ԫ��");
		columnHeads.add("��ʼʱ��");
		columnHeads.add("Ƭ�������ӣ�");
		
		
		table = new JTable(null,columnHeads);
		setLayout(new BorderLayout());

		// ��"table"�༭���õ� "CENTER"
		add(table, BorderLayout.CENTER);
		setSize(536,440);

		// ��ʾForm
		this.setVisible(true);
		
	}
	
	
	public Vector<Vector<String>> GetResultSet(ResultSet rs) throws SQLException {
		// ��λ�����һ����¼
		boolean moreRecords = rs.next();
		// ���û�м�¼������ʾһ����Ϣ
		if (!moreRecords) {
			JOptionPane.showMessageDialog(this,
			"��������޼�¼");
		}
		Vector<String> columnHeads = new Vector<String>();
		Vector<Vector<String>> rows = new Vector<Vector<String>>();
		try {

			// ��ȡ�ֶε�����
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); ++i)
			{
				System.out.println(rsmd.getSchemaName(i));
				columnHeads.addElement(rsmd.getSchemaName(i));
			}
                 
			// ��ȡ��¼��
			do {
				rows.addElement(getNextRow(rs, rsmd));
			} while (rs.next());
			
       
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			
		}
		  return rows;
			
	}


	public ResultSet getRs(String query,Connection connection){
		
		try {

			// ִ��SQL���
			resultSet = (connection.createStatement()).executeQuery(query);
			
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return resultSet;
		
	}

	public void getTable(String query, Connection connection) {
		try {

			// ִ��SQL���
		
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			// �ڱ������ʾ��ѯ���
			displayResultSet(resultSet);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}
	
	public String getSelectData(int rowIndex)
	{
		int selectIndex = table.getSelectedRow();
		if (selectIndex != -1) 
		{
			return (String) table.getModel().getValueAt(selectIndex, rowIndex);
		}
		
		return null;
			
	}
	
	public void getUpdate(String query,Connection connection)
	{
		 try {
			(connection.createStatement()).execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(panel, "����ʧ��!!!","......",JOptionPane.ERROR_MESSAGE) ;
		}
	}
	
	
	public EasySQLTable(JPanel panel,Vector<String> columnHeads) {
		// TODO Auto-generated constructor stub
		
		this.panel = panel;
		
		table = new JTable(null,columnHeads);
		
		setLayout(new BorderLayout());


		// ��"table"�༭���õ� "CENTER"
		add(table, BorderLayout.CENTER);

		
		setSize(536,440);
		JScrollPane scroller = new JScrollPane(table);
		Container c = panel;
		c.removeAll();
		c.add(scroller, BorderLayout.CENTER);
		// ��ʾForm
		this.setVisible(true);
		
	}

	private void displayResultSet(ResultSet rs) throws SQLException {

				// ��λ�����һ����¼
				boolean moreRecords = rs.next();
				// ���û�м�¼������ʾһ����Ϣ
				if (!moreRecords) {

				}
				Vector<String> columnHeads = new Vector<String>();
				Vector<Vector<String>> rows = new Vector<Vector<String>>();
				try {

					// ��ȡ�ֶε�����
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); ++i)
						columnHeads.addElement(rsmd.getColumnLabel(i));

					// ��ȡ��¼��
					if (moreRecords)
					{
					do {
						rows.addElement(getNextRow(rs, rsmd));
					} while (rs.next());
					}
					// �ڱ������ʾ��ѯ���
					table = new JTable(rows, columnHeads);
					table.setEnabled(true);
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					JScrollPane scroller = new JScrollPane(table);
					Container c = panel;
				    c.removeAll();
					c.add(scroller, BorderLayout.CENTER);

					// ˢ��Table
					c.validate();
				} catch (SQLException sqlex) {
					sqlex.printStackTrace();
				}
	}
	
	
	
	public String getSelectData()
	{
		int selectIndex = table.getSelectedRow();
		if (selectIndex != -1) 
		{
			return (String) table.getModel().getValueAt(selectIndex, 0);
		}
		
		return null;
			
	}
	

	private Vector<String> getNextRow(ResultSet rs, ResultSetMetaData rsmd)
			throws SQLException

	{
		Vector<String> currentRow = new Vector<String>();
		for (int i = 1; i <= rsmd.getColumnCount(); ++i)
			currentRow.addElement(rs.getString(i));

		// ����һ����¼
		return currentRow;
	}

	public void shutDown() {
		try {

			// �Ͽ����ݿ�����
			connection.close();
		} catch (SQLException sqlex) {
			System.err.println("Unable to disconnect");
			sqlex.printStackTrace();
		}
	}
	
	
	

	public static void main(String args[]) {
		
		String test = "SELECT * FROM film";
		Connection con = DBOpration.connectDB();
		JFrame frame = new JFrame("Table");
		final EasySQLTable app = new EasySQLTable((JPanel)frame.getContentPane());
		frame.setVisible(true);
		
		app.getTable(test, con);
		test = "SELECT * FROM city";
		app.getTable(test, con);
		frame.setSize(app.getSize());

	}

}
