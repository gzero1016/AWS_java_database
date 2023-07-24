package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnectionMgr;

public class Main {
	public static void main(String[] args) {
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		// finally로 DB 연결 해제를 하기위해 변수를 위로 빼준다.
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// DB가 연결이 끊어질 수 도 있기 때문에 try, catch 처리해줌
		try {
			// Java와 DB를 연결함 (con 객체 생성)
			con = pool.getConnection();
			
			// 실행할 쿼리문 작성
			String sql = "select * from user_tb";
			
			// 작성한 쿼리문을 가공 (pstmt 객체 생성)
			pstmt = con.prepareStatement(sql);	// 연결된 db에 spl 문을 넘겨준다.
			
			// 가공된 쿼리문을 실행 -> 결과를 ResultSet으로 변환 (rs 객체 생성)
			rs = pstmt.executeQuery();
			
	
			// 결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
			System.out.println("번호\t|\t아이디\t|\t비밀번호");
			while(rs.next()) {	// 하나의 rs 는 행 , next는 다음 행으로 이동
				
				// getInt() -> 정수
				// getString() -> 문자열
				System.out.println(rs.getInt(1) + " \t|\t " + rs.getString(2) + " \t|\t " + rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외가 발생했을 시 소멸이 안될 수 있으니 예외가 되더라도 정상적으로 실행되더라도 무조건 실행할수 있도록 finally로
			
			// 생성된 rs, pstmt, con 객체 소멸 (DB와 연결 해제)
			pool.freeConnection(con, pstmt, rs);
		}
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		try {
			// 데이터 베이스 연결
			con = pool.getConnection();
			
			// 쿼리문 작성
			String sql = "insert into user_tb values(0, ?, ?)";
			
			// 쿼리문 가공 준비
			pstmt = con.prepareStatement(sql);
			
			// 쿼리문 가공
			pstmt.setString(1, "ttt");	// 첫번째 ? 에 ttt를 넣는다.
			pstmt.setString(2, "1234");	// 두번째 ? 에 1234를 넣는다.
			
			// 쿼리문 실행
			int successCount = pstmt.executeUpdate();
			System.out.println("insert 성공 횟수: " + successCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외가 발생했을 시 소멸이 안될 수 있으니 예외가 되더라도 정상적으로 실행되더라도 무조건 실행할수 있도록 finally로
			
			// 생성된 rs, pstmt, con 객체 소멸 (DB와 연결 해제)
			pool.freeConnection(con, pstmt);
		}
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// DB가 연결이 끊어질 수 도 있기 때문에 try, catch 처리해줌
		try {
			// Java와 DB를 연결함 (con 객체 생성)
			con = pool.getConnection();
			
			// 실행할 쿼리문 작성
			String sql = "select * from user_tb";
			
			// 작성한 쿼리문을 가공 (pstmt 객체 생성)
			pstmt = con.prepareStatement(sql);	// 연결된 db에 spl 문을 넘겨준다.
			
			// 가공된 쿼리문을 실행 -> 결과를 ResultSet으로 변환 (rs 객체 생성)
			rs = pstmt.executeQuery();
			
	
			// 결과가 담긴 ResultSet을 반복작업을 통해 데이터 조회
			System.out.println("번호\t|\t아이디\t|\t비밀번호");
			while(rs.next()) {	// 하나의 rs 는 행 , next는 다음 행으로 이동
				
				// getInt() -> 정수
				// getString() -> 문자열
				System.out.println(rs.getInt(1) + " \t|\t " + rs.getString(2) + " \t|\t " + rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 예외가 발생했을 시 소멸이 안될 수 있으니 예외가 되더라도 정상적으로 실행되더라도 무조건 실행할수 있도록 finally로
			
			// 생성된 rs, pstmt, con 객체 소멸 (DB와 연결 해제)
			pool.freeConnection(con, pstmt, rs);
		}
		
		
	}
}
