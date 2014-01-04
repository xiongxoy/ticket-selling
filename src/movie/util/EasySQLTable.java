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
 * 提供简单的表格，并将取数据和现实的操作封装了起来
 */
public class EasySQLTable extends JPanel {


	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	// GUI变量定义
	private JTable table;
	private JPanel panel;
	


	/**
	 * 在制定的panel上绘制一张表格
	 * @param panel
	 */
	public EasySQLTable(JPanel panel) {
		// TODO Auto-generated constructor stub
		
		this.panel = panel;
		Vector<String> columnHeads = new Vector<String>();
		columnHeads.add("电影名");
		columnHeads.add("放映厅");
		columnHeads.add("票价（元）");
		columnHeads.add("开始时间");
		columnHeads.add("片长（分钟）");
		
		
		table = new JTable(null,columnHeads);
		setLayout(new BorderLayout());

		// 将"table"编辑框布置到 "CENTER"
		add(table, BorderLayout.CENTER);
		setSize(536,440);

		// 显示Form
		this.setVisible(true);
		
	}
	
	
	public Vector<Vector<String>> GetResultSet(ResultSet rs) throws SQLException {
		// 定位到达第一条记录
		boolean moreRecords = rs.next();
		// 如果没有记录，则提示一条消息
		if (!moreRecords) {
			JOptionPane.showMessageDialog(this,
			"结果集中无记录");
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


	public ResultSet getRs(String query,Connection connection){
		
		try {

			// 执行SQL语句
			resultSet = (connection.createStatement()).executeQuery(query);
			
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return resultSet;
		
	}

	public void getTable(String query, Connection connection) {
		try {

			// 执行SQL语句
		
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			// 在表格中显示查询结果
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
			JOptionPane.showMessageDialog(panel, "更新失败!!!","......",JOptionPane.ERROR_MESSAGE) ;
		}
	}
	
	
	public EasySQLTable(JPanel panel,Vector<String> columnHeads) {
		// TODO Auto-generated constructor stub
		
		this.panel = panel;
		
		table = new JTable(null,columnHeads);
		
		setLayout(new BorderLayout());


		// 将"table"编辑框布置到 "CENTER"
		add(table, BorderLayout.CENTER);

		
		setSize(536,440);
		JScrollPane scroller = new JScrollPane(table);
		Container c = panel;
		c.removeAll();
		c.add(scroller, BorderLayout.CENTER);
		// 显示Form
		this.setVisible(true);
		
	}

	private void displayResultSet(ResultSet rs) throws SQLException {

				// 定位到达第一条记录
				boolean moreRecords = rs.next();
				// 如果没有记录，则提示一条消息
				if (!moreRecords) {

				}
				Vector<String> columnHeads = new Vector<String>();
				Vector<Vector<String>> rows = new Vector<Vector<String>>();
				try {

					// 获取字段的名称
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); ++i)
						columnHeads.addElement(rsmd.getColumnLabel(i));

					// 获取记录集
					if (moreRecords)
					{
					do {
						rows.addElement(getNextRow(rs, rsmd));
					} while (rs.next());
					}
					// 在表格中显示查询结果
					table = new JTable(rows, columnHeads);
					table.setEnabled(true);
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					JScrollPane scroller = new JScrollPane(table);
					Container c = panel;
				    c.removeAll();
					c.add(scroller, BorderLayout.CENTER);

					// 刷新Table
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

		// 返回一条记录
		return currentRow;
	}

	public void shutDown() {
		try {

			// 断开数据库连接
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
