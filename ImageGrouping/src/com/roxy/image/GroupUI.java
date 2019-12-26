package com.roxy.image;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class GroupUI extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	private ImageGrouping imageGrouping;
	
	// Text Field, Button, CheckBox
	private TextField srcText = new TextField(40);
	private TextField targetText = new TextField(40);
	public static TextArea logArea = new TextArea(5, 40);
	private Button startButton = new Button("Start");
	private Button exitButton = new Button("Exit");
	private Button submitButton = new Button("OK");
	private Dialog dialog = null;
	private GridBagLayout gBag = null;
	private GridBagConstraints gbc = null;
	
	class AcListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// 제어 버튼 누를 때 발생하는 이벤트
			if (event.getSource() == exitButton) {
				System.exit(0);
			} else if (event.getSource() == submitButton) {
				// Dialog 확인 버튼
				dialog.setVisible(false);
			} else if (event.getSource() == startButton) {
				// 게임을 시작하는 부분
				String source = srcText.getText().trim();
				String target = targetText.getText().trim();
				if(source.length() == 0 || target.length() == 0) {
					openDialog("Error", "you must input value!");
					return;
				}
				
				try {
					imageGrouping.startGrouping(source, target);
					logArea.append("grouping complete!");
				} catch (Exception e) {
					openDialog("Error", "grouping fail! : " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	public GroupUI(ImageGrouping imageGrouping) {
		this.gBag =  new GridBagLayout();
		this.gbc = new GridBagConstraints();
		this.imageGrouping = imageGrouping;
		
		this.setTitle("Image Grouping");
		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(gBag);

		gbc.fill= GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        gbAdd(new Label("Source: ", Label.RIGHT), 0, 0, 1, 1);
        gbAdd(srcText, 1, 0, 3, 1);
        gbAdd(new Label("Target: ", Label.RIGHT), 0, 1, 1, 1);
        gbAdd(targetText, 1, 1, 3, 1);
        gbAdd(logArea, 0, 3, 4, 2);
        gbAdd(exitButton, 0, 5, 2, 1);
        gbAdd(startButton, 2, 5, 2, 1);
		
//        logArea.setEnabled(false);
        logArea.setEditable(false);
		startButton.addActionListener( new AcListener() );
		exitButton.addActionListener( new AcListener() );
	}
	
	//GridBag insert
	private void gbAdd(Component c, int x, int y, int w, int h) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gBag.setConstraints(c,gbc);
        this.add(c);
    }
	
	// 메시지 박스
	private void openDialog( String title, String msg ) {
        if( "".equals( msg ) == false ) {
        	dialog = new Dialog(new Frame(), title);
        	
        	// 중앙에 띄우기
        	dialog.setBounds(this.getX()+(this.getSize().width/2-100),
        				this.getY() + (this.getSize().height/2-50), 200, 150);
        	dialog.setPreferredSize(new Dimension(200,120));
        	
            Panel p = new Panel( new GridLayout(2, 1) );
            p.add( new Label(msg, Label.CENTER) );
            p.add( submitButton );
            submitButton.addActionListener( new AcListener() );
            submitButton.setSize(100, 20);
            
            dialog.add( p );
            dialog.pack();
            dialog.setVisible( true );
        }
    }
	
	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
}
