package movie.preview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

import javax.media.ControllerAdapter;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import movie.util.DBOpration;
import movie.util.EasySQLTable;

import com.jgoodies.forms.factories.Forms;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class PreviewWindow extends JFrame {

	private JPanel contentPane;
	private Player player; // 播放器对象
	private Component visualMedia; // 视频显示组件
	private Component mediaControl; // 视频播放控制组件
	private String moviename;
	private JPanel panel_movie;
	private JPanel panel_post;
	private JButton key_play;
	private JButton key_stop;
	private EasySQLTable movieTable;
	private Vector<Vector<String>> VectorTemp ;
	private Vector<String> movieTemp;
	private Connection connection;
	private JScrollBar scrollBar;
	private JScrollPane scrollPane;
	private JList list;
	private JTextPane textmovie;
	private JPanel panel;

	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PreviewWindow frame = new PreviewWindow();
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
	public PreviewWindow() {
		
		connection = DBOpration.connectDB();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 716);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("20px"),
				ColumnSpec.decode("144px:grow"),
				ColumnSpec.decode("38px"),
				ColumnSpec.decode("72px"),
				ColumnSpec.decode("46px"),
				ColumnSpec.decode("348px:grow"),
				ColumnSpec.decode("24px"),
				ColumnSpec.decode("180px"),},
			new RowSpec[] {
//				FormsFacoty.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("53px"),
//				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
//				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("386px:grow"),
//				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("31px"),
				RowSpec.decode("134px:grow"),}));
		
		String getmovie = "SELECT M_id,M_name FROM movie";
		ResultSet movieRs = DBOpration.getRs(getmovie, connection);
		 VectorTemp = new Vector<Vector<String>>();
		 movieTemp = new Vector<String>();
	  try {
		  
		  VectorTemp = (DBOpration.GetResultSet(movieRs));
		
		  for(Object t:  VectorTemp.toArray())
		  {
			 movieTemp.add(((Vector<String>) t).get(1)); 
		  }
		
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  //String[] item = (String[]) movieTemp.toArray();
	  Object[] item =  movieTemp.toArray();
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "2, 6, fill, fill");
		
		list = new JList(item);
		list.setFont(new Font("宋体", Font.PLAIN, 16));
		
		//contentPane.add(list, "2, 6, fill, fill");
		list.setBackground(Color.white);
		list.setForeground(Color.gray);
		scrollPane.setViewportView(list);
		
		panel_movie = new JPanel();
		contentPane.add(panel_movie, "4, 6, 3, 1, fill, fill");
		panel_movie.setLayout(new BorderLayout(0, 0));
		
		JLabel label_menu = new JLabel("\u9009\u62E9\u7535\u5F71");
		label_menu.setFont(new Font("宋体", Font.PLAIN, 22));
		contentPane.add(label_menu, "2, 2, left, fill");
		
		JLabel label_introduce = new JLabel("\u5F71\u7247\u4ECB\u7ECD");
		label_introduce.setFont(new Font("宋体", Font.PLAIN, 20));
		contentPane.add(label_introduce, "8, 2, 1, 3, left, fill");
		
		JLabel label_movie = new JLabel("\u7535\u5F71\u9884\u89C8");
		label_movie.setFont(new Font("宋体", Font.PLAIN, 18));
		contentPane.add(label_movie, "4, 4, 1, 3, left, top");
		
		textmovie = new JTextPane();
		contentPane.add(textmovie, "8, 6, fill, fill");
		
		JLabel label_post = new JLabel("\u7535\u5F71\u6D77\u62A5");
		label_post.setFont(new Font("宋体", Font.PLAIN, 17));
		contentPane.add(label_post, "2, 8, left, fill");
		
		panel_post = new JPanel();
		contentPane.add(panel_post, "2, 9, 4, 1, fill, fill");
		panel_post.setLayout(new GridLayout(1, 3, 0, 0));
		
		JLabel label = new JLabel("\u7535\u5F71\u540D\u79F0");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		contentPane.add(label, "2, 4, left, fill");
		
		panel = new JPanel();
		contentPane.add(panel, "6, 9, 2, 1, fill, fill");
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		key_stop = new JButton("\u505C\u6B62");
		key_stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.stop();
			}
		});
		contentPane.add(key_stop, "6, 2, left, fill");
		
		key_play = new JButton("\u64AD\u653E");
		key_play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				///////////////////////////////////////
				moviename = (String)list.getSelectedValue();				
				panel_post.removeAll();
				panel.removeAll();
				String temp = "SELECT P_path ,M_path, T_path FROM movie,resource where movie.M_id = resource.M_id and resource.M_id in (SELECT distinct M_id FROM movie where M_name = \"";
				String sel_path = temp + moviename +"\");";
				Object[] Ppath = sql(sel_path,0);
				Object[] Mpath = sql(sel_path,1);
				Object[] Tpath = sql(sel_path,2);
				String spath = (String) (Ppath[0]) + "\\1.jpg";
				String spath2 = (String) (Ppath[0]) + "\\2.jpg";
				System.out.println(spath);
				panel_post.add(new ImageViewer(spath));
				panel.add(new ImageViewer(spath2));
				//String pic_path = "C:/Users/GY/Desktop/1.jpg";										
				//panel_post.add(new ImageViewer("C:/Users/GY/Desktop/201112516423.jpg"));
							   
			    //String s = "file:/D:/下载/趣味影片/0381.mpg";
				System.out.println((String) Mpath[0]);
				startPlayer((String) Mpath[0]);
				File myfile = new File((String)Tpath[0]);
				String text = "";
				Scanner input = null;
				try {
					input = new Scanner(myfile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while(input.hasNext()){
					text = text + input.next() ;
				}
				input.close();
				textmovie.setText(text);
				//startPlayer("D:\下载\趣味影片\Bike.mpg"); //开始播放打开的文件
				//////////////////////////////////////
			}
		});
		contentPane.add(key_play, "4, 2, fill, fill");
		
				
	}
	
	public class ImageViewer extends JPanel{
		//显示图片
		//ImageIcon myicon = new ImageIcon("C:/Users/GY/Desktop/201112516423.jpg");
		//Image image1 = myicon.getImage();
		ImageIcon myicon;
		Image image1;
       public ImageViewer(String path){
    	 myicon = new ImageIcon(path);
   		 image1 = myicon.getImage();
      }
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(image1 != null){
			g.drawImage(image1, 0, 0, getWidth(),getHeight(),this);
		}
	}
	}
	
	public Object[] sql(String select,int index){
		//String getmovie = "SELECT M_id,M_name FROM movie";
		ResultSet movieRs = DBOpration.getRs(select, connection);
		 VectorTemp = new Vector<Vector<String>>();
		 movieTemp = new Vector<String>();
	  try {
		  
		  VectorTemp = (DBOpration.GetResultSet(movieRs));
		  //movieTemp.add(e)
		  for(Object t:  VectorTemp.toArray())
		  {
			 movieTemp.add(((Vector<String>) t).get(index)); 
		  }
		
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  //String[] item = (String[]) movieTemp.toArray();
	  Object[] item =  movieTemp.toArray();
	return item;
	}

public void startPlayer(String mediaLocation) {
    if (player != null) //如果播放器非空则移去先前的播放器组件
    {
        if (visualMedia != null) {
        	panel_movie.remove(visualMedia); //如果对象visualMedia非空则移去
        }
    }
    if (mediaControl != null) {
    	panel_movie.remove(mediaControl); //如果对象mediaControl非空则移去
        player.close(); //关闭播放器
    }
    MediaLocator mediaLocator = new MediaLocator(mediaLocation); //媒体定位器
    if (mediaLocator == null) {
        showMessage("打开文件错误"); //显示错误信息
        return;
    }
    try {
        player = Manager.createPlayer(mediaLocator); //得到播放器实例
        player.addControllerListener(new PlayerEventHandler()); //增加播放控制器
        player.realize();
    } catch (Exception ex) {
        ex.printStackTrace();
        showMessage("打开错误"); //显示错误信息
    }

}
//取得媒体组件

public void getMediaComponents() {
    visualMedia = player.getVisualComponent(); //取得视频显示组件

    //如果对象visualMedia非空则加入到窗口内容窗格
    if (visualMedia != null) {
    	panel_movie.add(visualMedia, BorderLayout.CENTER);
        pack();
    }

    mediaControl = player.getControlPanelComponent(); //取得播放控制组件

    //如果对象visualMedia非空则加入到窗口内容窗格
    if (mediaControl != null) {
    	panel_movie.add(mediaControl, BorderLayout.SOUTH);
    }

}

//播放器事件处理
private class PlayerEventHandler extends ControllerAdapter {

    @Override
	public void realizeComplete(RealizeCompleteEvent realizeDoneEvent) {
        player.prefetch(); //预取媒体数据
    }

    //完成预取媒体数据后，开始播放媒体
    @Override
	public void prefetchComplete(PrefetchCompleteEvent prefetchDoneEvent) {
        getMediaComponents();
        validate();
        player.start(); //开始播放媒体
    }

    //如果媒体播放完毕，重新设置媒体时间并停止媒体播放器
    @Override
	public void endOfMedia(EndOfMediaEvent mediaEndEvent) {
        player.setMediaTime(new Time(0)); //重新设置媒体时间
        player.stop(); // 停止媒体播放
    }
}

public void showMessage(String s) {
    JOptionPane.showMessageDialog(this, s);	//显示提示信息
}

}