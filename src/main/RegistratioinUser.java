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
import javax.swing.JOptionPane;
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
	private String deleteName;
	
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
		
		
//		MouseListener addUserButtonListener = new AddUserButtonMouseListener();
//		addUserButton.addMouseListener(addUserButtonListener);
		// 위 코드랑 아래 코드가 똑같음
//		MouseListener 인터페이스 안에 메소드가 여러개 정의되어있어 람다를 쓸수 없음
		addUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!insertUser(usernametextField.getText(), passwordtextField.getText())) {
					JOptionPane.showMessageDialog(contentPane, "사용자 추가 실패!", "insert 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					JOptionPane.showMessageDialog(contentPane, "사용자 추가 완료!", "insert 성공", JOptionPane.PLAIN_MESSAGE);
				}
				updateUserListTable(table);
			}
		});
		
		
		addUserButton.setBounds(12, 70, 410, 26);
		contentPane.add(addUserButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 106, 410, 145);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(getUserTableModel());
		scrollPane.setViewportView(table);
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
	
	// insert, delete 후 테이블 업데이트
	private void updateUserListTable(JTable jTable) {
		jTable.setModel(getUserTableModel());
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
	private boolean insertUser(String username, String password) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			con = pool.getConnection();
			
			String sql = "insert into user_tb (username, password) values(?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			// update가 정상처리되었다면 result에 true를 넣어준다.
			result = pstmt.executeUpdate() != 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}
}

