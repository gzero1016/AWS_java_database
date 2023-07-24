package main;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import config.DBConnectionMgr;

public class RegistratioinUser extends JFrame {

	private JPanel contentPane;
	private JTextField usernametextField;
	private JTextField passwordtextField;
	private JTable table;
	
	private static List<Object> dtlList = new ArrayList<>();
	private static List<List<Object>> mstList = new ArrayList<>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistratioinUser frame = new RegistratioinUser();
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
	public RegistratioinUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		usernametextField = new JTextField();
		usernametextField.setBounds(12, 34, 201, 26);
		contentPane.add(usernametextField);
		usernametextField.setColumns(10);
		
		passwordtextField = new JTextField();
		passwordtextField.setBounds(221, 34, 201, 26);
		contentPane.add(passwordtextField);
		passwordtextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("아이디");
		lblNewLabel.setBounds(12, 10, 57, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("비밀번호");
		lblNewLabel_1.setBounds(225, 10, 57, 15);
		contentPane.add(lblNewLabel_1);
		
		JButton addUserButton = new JButton("추가");
		addUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				insertUser(usernametextField.getText(), passwordtextField.getText());
				table.setModel(getUserTableModel());
			}
		});
		addUserButton.setBounds(12, 70, 315, 26);
		contentPane.add(addUserButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 106, 410, 145);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(getUserTableModel());
		scrollPane.setViewportView(table);
		
		JButton deleteButton = new JButton("삭제");
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteUser(usernametextField.getText());
				table.setModel(getUserTableModel());
			}
		});
		deleteButton.setBounds(339, 70, 83, 26);
		contentPane.add(deleteButton);

	}
	
	// 배열로 바꾸는작업
	public DefaultTableModel getUserTableModel() {
		String[] header = new String[] {"user_id", "username", "password"};
		
		List<List<Object>> userListAll = getUserListAll();
		
		Object[][] userModelArray = new Object[userListAll.size()][userListAll.get(0).size()];
		for(int i = 0; i < userListAll.size(); i++) {
			for(int j = 0; j < userListAll.get(i).size(); j++) {
				 userModelArray[i][j] = userListAll.get(i).get(j);
			}
		}
		
		return new DefaultTableModel(userModelArray, header);
	}
	
	// select
	public List<List<Object>> getUserListAll() {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<List<Object>> mstList = new ArrayList<>();
		
		try {
			con = pool.getConnection();
			
			String sql = "select * from user_tb";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				List<Object> dtlList = new ArrayList<>();
				dtlList.add(rs.getInt(1));
				dtlList.add(rs.getString(2));
				dtlList.add(rs.getString(3));
				mstList.add(dtlList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return mstList;
	}
	
	// insert
	public void insertUser(String username, String password) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = pool.getConnection();
			
			String sql = "insert into user_tb (username, password) values(?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			pstmt.executeUpdate();
			
			System.out.println("insert Successfully!!");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
	
	// delete
	public void deleteUser(String username) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = pool.getConnection();
			
			String sql = "delete from user_tb where username = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			
			pstmt.executeUpdate();
			
			System.out.println("delete Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
}

