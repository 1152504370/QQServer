package cn.qdu.qq.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cn.qdu.qq.bis.ServerBis;

public class QQServerJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton button1;
	private JButton button2;
	private ServerBis sbis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QQServerJFrame frame = new QQServerJFrame();
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
	public QQServerJFrame() {
		sbis = new ServerBis();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		button1 = new JButton("\u542F\u52A8\u670D\u52A1");
		button1.addActionListener(new ActionListener() {
			/**
			 * 服务启动按钮点击事件，启动ServerSocket服务
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("btnNewButton.actionPerformed" + e);
				new Thread() {
					public void run() {
						button1.setEnabled(false);
						button2.setEnabled(true);
						try {
							sbis.StartService();
						} catch (IOException e1) {
							System.out.println("服务器启动失败！");
							e1.printStackTrace();
						}
					};
				}.start();
			}
		});

		button2 = new JButton("\u505C\u6B62\u670D\u52A1");
		button2.setEnabled(false);
		button2.addActionListener(new ActionListener() {
			/**
			 * 服务停止按钮点击事件,停止ServerSocket服务
			 */
			public void actionPerformed(ActionEvent e) {
				System.out.println("btnNewButton_1.actionPerformed" + e);
				new Thread(){
					public void run(){
						button1.setEnabled(true);
						button2.setEnabled(false);
						try {
							sbis.StopService();
						} catch (IOException e1) {
							System.out.println("服务器停止失败！");
							e1.printStackTrace();
						}
					};
				}.start();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addContainerGap(47, Short.MAX_VALUE)
										.addComponent(button1, GroupLayout.PREFERRED_SIZE, 153,
												GroupLayout.PREFERRED_SIZE)
										.addGap(32).addComponent(button2, GroupLayout.PREFERRED_SIZE, 139,
												GroupLayout.PREFERRED_SIZE)
										.addGap(37)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_contentPane.createSequentialGroup().addContainerGap(83, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(button2, GroupLayout.PREFERRED_SIZE, 42,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(button1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addGap(107)));
		gl_contentPane.linkSize(SwingConstants.VERTICAL, new Component[] { button2, button1 });
		gl_contentPane.linkSize(SwingConstants.HORIZONTAL, new Component[] { button2, button1 });
		contentPane.setLayout(gl_contentPane);
	}
}
