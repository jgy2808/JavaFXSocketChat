package application.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnect {
	Connection conn;

	public void connect() {
		String url = "jdbc:mysql://monfj0054yp8v8db:l9bbbpdrplbort86@ohunm00fjsjs1uzy.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/u1ugaz603cqfne9g";
		String user = "	monfj0054yp8v8db";
		String password = "l9bbbpdrplbort86";
		String driver = "com.mysql.cj.jdbc.Driver";

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 회원 로그인을 검사하는 메서드
	public int login(String email, String pw) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where email=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				if (pw.equals(rs.getString(3))) {
					// 이메일, 비밀번호 일치
					System.out.println("로그인 성공");
					return 1;
				} else {
					// 이메일 일치, 비밀번호 틀림
					System.out.println("로그인 실패");
					return 2;
				}

			} else {
				// 없는 계정
				System.out.println("없는 이메일");
				return 3;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			if (pstmt != null) {
				pstmt.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	// 회원 정보를 등록하는 메서드
	public void register(String email, String nick, String pw) {
		PreparedStatement pstmt = null;
		String sql = "insert into user values(?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, nick);
			pstmt.setString(3, pw);
			

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public String getNick(String email) {
		String nick = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select nick from user where email=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				nick = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			if (pstmt != null) {
				pstmt.close();
			}

			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nick;
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
