import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.text.Caret;

public class Contactbook extends JFrame{
	//整數
	private boolean islike;
	private boolean isnew=false;
	private int likecount=0;
    private String temp;
	//Panel
	private JPanel buttonPanel1;
	private JPanel buttonPanel2;
	private JPanel bottom;
	private JPanel top;
	private JPanel weaPanel;
	//Label
	private static JLabel title;
	private static JLabel weath;
	private static JLabel editTime;
    //按鈕
	private JButton edit;
	private JButton newPost;
	private JButton likebutton;
	private JButton store;
	private JButton saveAs;
	private JButton imPort;
	private JButton cancel;
    //顏色
	private static Color color1= new Color(30,70,36);
	private Color color2= new Color(30,80,36);
	//ComboBox
	private JComboBox<String> weatherComboBox;
	//物件
	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	private ObjectInputStream Importinput;
	//文字區塊
	private static JTextArea TextContent;
	//Icon
	private ImageIcon like =new ImageIcon(getClass().getResource("like.png"));
	private ImageIcon unlike =new ImageIcon(getClass().getResource("unlike.png"));

	//天氣icon
	private static final String[] names = {"sunny.png","rainy.png","cloudy.png"};
	private static final String[] CHnames = {"晴天","雨天","陰天"};
	private final Icon[] icons = { 
			      new ImageIcon(getClass().getResource(names[0])),
			      new ImageIcon(getClass().getResource(names[1])), 
			      new ImageIcon(getClass().getResource(names[2]))};
	private FileWriter fileWriter;

	
	public Contactbook(int Mode){
		super("聯絡簿");

		//加入主要Panel
		TextContent = new JTextArea();
		TextContent.setBackground(color1);
		TextContent.setForeground(Color.yellow);
		TextContent.setEditable(false);
		TextContent.setFont(new Font("Dialog", 1, 16));
        add(TextContent,BorderLayout.CENTER);
		
		//加入上層Panel
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		title = new JLabel("聯絡簿");
		weath = new JLabel("天氣",icons[0],SwingConstants.LEFT);
		editTime= new JLabel();
		
		title.setFont(new Font("Dialog", 1, 50));
		title.setForeground(Color.WHITE);
		title.setOpaque(true);
		title.setBackground(color1);
		
		weath.setFont(new Font("Dialog", 1, 35));
		weath.setForeground(Color.WHITE);
		
		editTime.setFont(new Font("Dialog", 1, 14));
		editTime.setForeground(Color.WHITE);
		
		top.setBackground(color1);
		top.add(title);
		top.add(weath);
		top.add(editTime);
		add(top,BorderLayout.NORTH);

        //建立按鈕物件,編輯按鈕的Panel
		buttonPanel1 =new JPanel();
		buttonPanel1.setBackground(color2);
		edit = new JButton("編輯");
		newPost = new JButton("全新貼文");
		likebutton = new JButton();  

		likebutton.setBackground(color2);		
		likebutton.setBorder(null);
		likebutton.setContentAreaFilled(false);
		likebutton.setEnabled(false);
		//僅檢視模式下隱藏按鈕
		if (Mode == 1) {
			edit.setVisible(false);
			newPost.setVisible(false);
			likebutton.setEnabled(true);
		}
		
		//建立儲存按鈕的Panel
		buttonPanel2 =new JPanel();
		buttonPanel2.setBackground(color2);
		store=new JButton("儲存");
		saveAs=new JButton("另存新檔");
		imPort=new JButton("匯入檔案");
		cancel=new JButton("取消");
		
		//建立選擇天氣的combobox
		weatherComboBox = new JComboBox<String>(CHnames);
		weatherComboBox.addItemListener(         
				new ItemListener()
         {
            public void itemStateChanged(ItemEvent event)
            {
               if (event.getStateChange() == ItemEvent.SELECTED)
            	   weath.setIcon(icons[
                     weatherComboBox.getSelectedIndex()]);
            } 
         });
		weaPanel = new JPanel();
		weaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		weaPanel.add(weatherComboBox);
		weaPanel.setBackground(color1);
		
		
		//加入actionlistener
		edit.addActionListener(e -> {    bottom.removeAll();
		                                 bottom.add(buttonPanel2);
		                                 bottom.validate();
		                                 bottom.repaint();
		                                 
		                                 top.remove(weath);
		                                 top.add(weaPanel);
		                                 top.validate();
		                                 top.repaint();
		                                 
		                                 TextContent.setEditable(true);
		                                 TextContent.setBackground(Color.WHITE);
		                                 TextContent.setForeground(Color.BLACK);
		});
		likebutton.addActionListener(e -> { likecount ++;
			                                if (likecount %2 == 0)
			                                	{likebutton.setIcon(unlike);
			                                    islike = false;}
			                                else if (likecount %2 == 1)
			                                	{likebutton.setIcon(like);
			                                	islike = true;}
			                                	});
			                                	
		newPost.addActionListener(e ->{isnew=true;
		                               if (isnew =true)
		                            	   likebutton.setIcon(unlike);
			                           bottom.removeAll();
                                       bottom.add(buttonPanel2);
                                       bottom.validate();
                                       bottom.repaint();
                                       
                                       top.remove(weath);
		                               top.add(weaPanel);
		                               top.validate();
		                               top.repaint();
			
			                           TextContent.setEditable(true);
		                               TextContent.setBackground(Color.WHITE);
                                       TextContent.setForeground(Color.BLACK);
                                       TextContent.setText("");});
		
		
		
		store.addActionListener(e -> {   bottom.removeAll();
		                                 bottom.add(buttonPanel1);
		                                 bottom.validate();
		                                 bottom.repaint();
		                                 
		                                 top.remove(weaPanel);
		                                 top.add(weath);
		                                 top.validate();
		                                 top.repaint();
		                                 
		                                 TextContent.setEditable(false);
		                                 TextContent.setBackground(color1);
		                                 TextContent.setForeground(Color.yellow);		                                 
		                                 
		                                 storePost();
		                                 temp = TextContent.getText();
		});
		
		saveAs.addActionListener(e -> {saveasFile();});
		
		imPort.addActionListener(e -> {importFile();});
		
		cancel.addActionListener(e -> {bottom.removeAll();
                                       bottom.add(buttonPanel1);
                                       bottom.validate();
                                       bottom.repaint();
        
                                       top.remove(weaPanel);
                                       top.add(weath);
                                       top.validate();
                                       top.repaint();
                                       TextContent.setEditable(false);
                                       TextContent.setBackground(color1);
                                       TextContent.setForeground(Color.yellow);
                                       
                                       TextContent.setText(temp);
		});
		
		

		//把按鈕放入Panel
		buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.X_AXIS));
		buttonPanel1.add(likebutton);
		buttonPanel1.add(Box.createHorizontalGlue());
		buttonPanel1.add(edit);
		buttonPanel1.add(newPost);
		
		buttonPanel2.setLayout(new BoxLayout(buttonPanel2, BoxLayout.X_AXIS));
		buttonPanel2.add(store);
		buttonPanel2.add(saveAs);
		buttonPanel2.add(imPort);
		buttonPanel2.add(cancel);
		buttonPanel2.add(Box.createHorizontalGlue());
		
		
		//把buttonPanel加入視窗
		bottom =new JPanel();
		bottom.setLayout(new GridLayout(1,1));
		bottom.add(buttonPanel1);
		add(bottom,BorderLayout.SOUTH);
				
		InputopenFile();
		readPost();
		closeFile();
		
	}
	
	public static void InputopenFile() {
		try
		{
			input = new ObjectInputStream(Files.newInputStream(Paths.get("src/post")));
		}
		catch(IOException ioException)
		{
			System.err.println("Error opening file.Terminating.");
			System.exit(1);
		}
	}
	
	public void readPost() {
		try
		{
			PostSerializable Post = (PostSerializable) input.readObject();
			temp = Post.getContent();
			
			if (Post.getIsLike() == false)
		    	likebutton.setIcon(unlike);
		    else if (Post.getIsLike() == true)
		    	likebutton.setIcon(like);
            
			isnew=false;
			TextContent.setText(Post.getContent());
			weath.setIcon(icons[Post.getWeather()]);
			SimpleDateFormat sdf = new SimpleDateFormat();
			String dateString = sdf.format(Post.getEditTime());
			editTime.setText(dateString);
			
		}
		catch(EOFException endOfFileException)
		{
			System.out.printf("No more records");
		}
		catch(ClassNotFoundException classNotFoundException)
		{
			System.err.println("Invalid object type. Terminating.");
		}
		catch(IOException ioException)
		{
			System.err.println("Error reading from file. Terminating.");
		}
	}
	
	public void storePost() {
		try
		{
			Date nowdate = new Date();
			output = new ObjectOutputStream(Files.newOutputStream(Paths.get("src/post")));
			PostSerializable Store = new PostSerializable(TextContent. getText(), islike, nowdate);
			Store.setWeather(weatherComboBox.getSelectedIndex());
			output.writeObject(Store);
			
			SimpleDateFormat sdf = new SimpleDateFormat();
			String dateString = sdf.format(nowdate);
			editTime.setText(dateString);
			
			if (output != null)
				output.close();
			
		}
		catch(EOFException endOfFileException)
		{
			System.out.printf("No more records");
		}
		catch(IOException ioException)
		{
			System.err.println("Error reading from file. Terminating.");
		}
	}
	
	public void saveasFile() {
		JFileChooser safileChooser = new JFileChooser();
		safileChooser.setDialogTitle("Save");
		safileChooser.showSaveDialog(null);
		safileChooser.setVisible(true);
		try 
		{
			fileWriter = new FileWriter(safileChooser.getSelectedFile().getAbsolutePath());
			fileWriter.write(TextContent.getText());
		}
		catch(Exception e2) 
		{
			e2.printStackTrace();
		}
		finally {
			try
			{				
				fileWriter.close();
			}
			catch (Exception e3) {
				e3.printStackTrace();
		}
		}
	}
	
	public void importFile(){
		
		try {
		JFileChooser imfileChooser = new JFileChooser();				
		
		imfileChooser.setDialogTitle("Import");
		imfileChooser.setVisible(true);
		
		int flag = imfileChooser.showOpenDialog(this);
		
		if (flag == imfileChooser.APPROVE_OPTION) {
	    File selectedFile =imfileChooser.getSelectedFile();
	    Scanner s = new Scanner(selectedFile);
        StringBuilder builder = new StringBuilder();
	    while(s.hasNext()) {
        	builder.append(String.format("%s%n", s.nextLine()));
        }
	    TextContent.setText(builder.toString());
        s.close();
		}
		else if(flag == 1) {
			setDefaultCloseOperation(imfileChooser.CANCEL_OPTION);
		}
		}
		catch (Exception e1) 
		{
		e1.printStackTrace();
		}
		
		
	}
	
	
	
		public void closeFile() {
		try
		{
			if (input != null)
				input.close();
		}
		catch(IOException ioException)
		{
			System.err.println("Error closing file. Terminating");
			System.exit(1);
		}
	}

}
