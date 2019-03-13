package dlnu.sdl.utils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SqlHelper {
	// ������Ҫ�ı���
	private static Connection ct = null;
	// �ڴ��������£�����ʹ�õ���PreparedStatement�����Statement
	// �������Է�ֹsqlע�롣
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;

	// �������ݿ����
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";

	// �������ļ���
	private static Properties pp = null;
	private static InputStream fis = null;
	// ����������ֻ��Ҫһ�Σ��þ�̬�����
	static {
		try {
			// ��dbinfo.properties�ļ��ж�ȡ�����ļ�
			pp = new Properties();
			// fis = new FileInputStream("dbinfo.properties");//=>tomcat����Ŀ¼��
			// ������ʹ��java web ��ʱ�򣬶�ȡ�ļ�Ҫʹ���������[��Ϊ�������ȥ��ȡ��Դ��ʱ��Ĭ�ϵ���Ŀ¼��src]
			fis = SqlHelper.class.getClassLoader().getResourceAsStream(
					"dbinfo.properties");
			pp.load(fis);
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			driver = pp.getProperty("driver");
			password = pp.getProperty("password");

			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fis = null;// ��������վ����ʰ
		}

	}

	// �õ�����
	public static Connection getConnection() {
		try {
			ct = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}

	// ��ҳ����
	public static ResultSet excuteQuery2() {
		return null;
	}

	// ���ô洢����,�޷��ز���
	// sql call ����(?,?,?)
	// *************callPro1�洢���̺���1*************
	public static void callPro1(String sql, String[] parameters) {
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					cs.setObject(i + 1, parameters[i]);
				}
			}
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, cs, ct);
		}

	}

	// ���ô洢���̣��з���Result
	// sql call ����(?,?,?)
	// *******************callpro2�洢����2************************
	public static CallableStatement callPro2(String sql, String[] inparameters,
			Integer[] outparameters) {
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			if (inparameters != null) {
				for (int i = 0; i < inparameters.length; i++) {
					cs.setObject(i + 1, inparameters[i]);
				}
			}
			// cs.registerOutparameter(2,oracle.jdbc.OracleTypes.CURSOR);
			if (outparameters != null) {
				for (int i = 0; i < outparameters.length; i++) {
					cs.registerOutParameter(inparameters.length + 1 + i,
							outparameters[i]);
				}
			}
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return cs;
	}

	// �Բ�ѯ�������
	// �����������㣬�����Ĺ�������ʹ����Դ������ر���Դ
	@SuppressWarnings({"unchecked" })
	public static ArrayList executeQuery3(String sql, String[] parms) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			// ���ʺŸ�ֵ
			if (parms != null && !parms.equals("")) {
				for (int i = 0; i < parms.length; i++) {
					pstmt.setString(i + 1, parms[i]);
				}
			}
			rs = pstmt.executeQuery();
			ArrayList al = new ArrayList();
			ResultSetMetaData rsmd = rs.getMetaData();
			int column = rsmd.getColumnCount();// ������Եõ�����Ĳ�ѯ������ж�����
			while (rs.next()) {
				Object[] ob = new Object[column];
				for (int i = 1; i <= column; i++) {
					ob[i - 1] = rs.getObject(i);
				}
				al.add(ob);
			}
			return al;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("executeSqlResultSet��������"
					+ e.getMessage());
		} finally {
			// try{
			close(rs, pstmt, conn);
			// }catch(Exception e){
			// e.printStackTrace();
			// throw new
			// RuntimeException("executeSqlResultSet��������"+e.getMessage());

			// }
		}

	}

	// ͳһ��select
	// ResultSet->ArrayList
	public static ResultSet executeQuery(String sql, String[] parameters) {
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if (parameters != null && !parameters.equals("")) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return rs;
	}

	// ����ж�� update/ delete/insert[��Ҫ��������]
	public static void executeUpdate2(String[] sql, String[][] parameters) {
		try {
			// ����
			// 1���������
			ct = getConnection();
			//
			// ��Ϊ��ʱ�û�����Ŀ����Ƕ��sql���
			ct.setAutoCommit(false);
			// ....
			for (int i = 0; i < sql.length; i++) {

				if (parameters[i] != null) {
					ps = ct.prepareStatement(sql[i]);
					for (int j = 0; j < parameters[i].length; j++) {
						ps.setString(j + 1, parameters[i][j]);
					}
					ps.executeUpdate();
				}

			}

			ct.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				ct.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}

	}

	// ��дһ��update��delete��insert
	// sql��ʽ��update ���� set �ֶ��� =��where �ֶ�=��
	// parameter��Ӧ���ǣ���abc��,23�������Ƿ�ɹ�
	public static boolean executeUpdate(String sql, String[] parameters) {
		// 1������һ��ps
		boolean bo = false;
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}

			}
			if(ps.executeUpdate() == 1)
				bo = true;
		} catch (Exception e) {
			e.printStackTrace();// �����׶�
			// �׳��쳣
			// ���Դ���Ҳ���Բ�����
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}
		return bo; 
	}

	
	public static void close(ResultSet rs, Statement ps, Connection ct) {
		// �ر���Դ(�ȿ����)
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps = null;
		}
		if (null != ct) {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ct = null;
		}
	}

	public static Connection getCt() {
		return ct;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static CallableStatement getCs() {
		return cs;
	}
}
