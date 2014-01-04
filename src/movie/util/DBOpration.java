package movie.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * �������õ����ݿ����
 * @author ʵ����
 *
 */
public class DBOpration {
	
	/**
	 * ���ӵ����ݿ�
	 * @return ��õ�����
	 */
	public static Connection connectDB()
	{
		Connection con = null;
		
		String url = "jdbc:mysql://127.0.0.1:3306/cinema";
		String username = "kkk";
		String password = "123456";
		// ���������������������ݿ�

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		}

		// ����������������쳣
		catch (ClassNotFoundException cnfex) {
			System.err.println("װ�� JDBC/ODBC ��������ʧ�ܡ�");
			cnfex.printStackTrace();
			System.exit(1); // terminate program
		}

		// �����������ݿ��쳣
		catch (SQLException sqlex) {
			System.err.println("�޷��������ݿ�");
			sqlex.printStackTrace();
			System.exit(1); // terminate program
		}
		
		return con;
		
	}
	
	//ִ�в�ѯ��䣬���ز�ѯ���
	public  static ResultSet getRs(String query,Connection connection){
		
		
		ResultSet resultSet = null;
		try {
			// ִ��SQL���
			
			System.out.println(query);
		
		resultSet = (connection.createStatement()).executeQuery(query);

			// �ڱ������ʾ��ѯ���
			
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return resultSet;
		
	}
	//����һ����ʽ���ز�ѯ�Ľ��
	public static Vector<Vector<String>> GetResultSet(ResultSet rs) throws SQLException {
		// ��λ�����һ����¼
		boolean moreRecords = rs.next();
		// ���û�м�¼������ʾһ����Ϣ
		if (!moreRecords) {
			throw new NoSuchFieldError();
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

	private static  Vector<String> getNextRow(ResultSet rs, ResultSetMetaData rsmd)
			throws SQLException

	{
		Vector<String> currentRow = new Vector<String>();
		for (int i = 1; i <= rsmd.getColumnCount(); ++i)
			currentRow.addElement(rs.getString(i));

		// ����һ����¼
		return currentRow;
	}
	
	/**
	 * ִ������ڲ�ѯ�������ݿ���ɾ�����
	 * @param query
	 * @param connection
	 */
	public static void getUpdate(String query,Connection connection)
	{
		 try {
			(connection.createStatement()).execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "����ʧ��!!!","......",JOptionPane.ERROR_MESSAGE) ;
		}
	}
	/**
	 * ȡ�ñ���һ�У��±���㿪ʼ
	 * @param VectorTemp
	 * @param index
	 * @return
	 */
	public static Vector<String> getARow(Vector<Vector<String>> VectorTemp,int index)
	{
		//Vector<Vector<String>> VectorTemp = new ();
		Vector<String> movieTemp = new Vector<String>();
		if (VectorTemp == null)
		{
			return null;
		}
		for(Object t:  VectorTemp.toArray())
		{
			movieTemp.add(((Vector<String>) t).get(index)); 
		}
  		return movieTemp;
  
	}



}
