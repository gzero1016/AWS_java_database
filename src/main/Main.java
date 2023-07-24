package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnectionMgr;

public class Main {
	public static void main(String[] args) {
		
		// DB가 연결이 끊어질 수 도 있기 때문에 try, catch 처리해줌
		try {
			Connection con = DBConnectionMgr.getInstance().getConnection();
			String sql = "select * from user_tb";
			PreparedStatement pstmt = con.prepareStatement(sql);	// 연결된 db에 spl 문을 넘겨준다.
			ResultSet rs = pstmt.executeQuery();	// 
			
			// boolean 타입을 리턴하는 친구 rs는 list 형태로 들어가있음
			while(rs.next()) {	// next로 호출하면 row 를 들고옴 한줄씩 내려가다가 값이 없으면 false를 리턴하며 while이 끝이난다.
				System.out.println(rs.getString(2));	// 2번째 열을 들고온다. 1열 = user_id , 2열 = user_name , 3열 = password
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	} 
}
