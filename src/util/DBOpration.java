package movie.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * 包含公用的数据库操作
 * @author 实验室
 *
 */
public class DBOpration {
	
	/**
	 * 连接到数据库
	 * @return 获得的连接
	 */
	public static Connection connectDB()
	{
		Connection con = null;
		
		String url = "jdbc:mysql://127.0.0.1:3306/cinema";
		String username = "kkk";
		String password = "123456";
		// 加载驱动程序以连接数据库

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		}

		// 捕获加载驱动程序异常
		catch (ClassNotFoundException cnfex) {
			System.err.println("装载 JDBC/ODBC 驱动程序失败。");
			cnfex.printStackTrace();
			System.exit(1); // terminate program
		}

		// 捕获连接数据库异常
		catch (SQLException sqlex) {
			System.err.println("无法连接数据库");
			sqlex.printStackTrace();
			System.exit(1); // terminate program
		}
		
		return con;
		
	}
	
	//执行查询语句，返回查询结果
	public  static ResultSet getRs(String query,Connection connection){
		
		
		ResultSet resultSet = null;
		try {
			// 执行SQL语句
			
			System.out.println(query);
		
		resultSet = (connection.createStatement()).executeQuery(query);

			// 在表格中显示查询结果
			
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return resultSet;
		
	}
	//以另一种形式返回查询的结果
	public static Vector<Vector<String>> GetResultSet(ResultSet rs) throws SQLException {
		// 定位到达第一条记录
		boolean moreRecords = rs.next();
		// 如果没有记录，则提示一条消息
		if (!moreRecords) {
			throw new NoSuchFieldError();
		}
		Vector<String> columnHeads = new Vector<String>();
		Vector<Vector<String>> rows = new Vector<Vector<String>>();
		try {

			// 获取字段的名称
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); ++i)
			{
				System.out.println(rsmd.getSchemaName(i));
				columnHeads.addElement(rsmd.getSchemaName(i));
			}
                 
			// 获取记录集
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

		// 返回一条记录
		return currentRow;
	}
	
	/**
	 * 执行相对于查询语句的数据库增删改语句
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
			JOptionPane.showMessageDialog(null, "更新失败!!!","......",JOptionPane.ERROR_MESSAGE) ;
		}
	}
	/**
	 * 取得表中一列，下标从零开始
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
