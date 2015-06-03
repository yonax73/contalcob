/**
 * 
 */
package com.contalcob.sec.login.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.sec.login.entity.Login;
import com.contalcob.utils.base.dao.Dao;

/**
 * company : yonax73@gmail.com <br/>
 * user : YQ<br/>
 * update date : May 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 19, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class LoginDao extends Dao {

	public static boolean create(Login login) {
		boolean created = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_sec_login_CREATE(?,?,?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, login.getType() == null ? 0 : login.getType().getId());
			cst.setString(3, login.getEmail());
			cst.setString(4, login.getPassword());
			cst.setString(5, login.getUser());
			created = cst.executeUpdate() > 0;
			if (created) {
				login.setId(cst.getInt(1));
				created = login.exists();
			}
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining created the login.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return created;
	}

	public static boolean update(Login login) {
		boolean updated = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_sec_login_UPDATE(?,?,?,?)");
			cst.setInt(1, login.getId());
			cst.setInt(2, login.getType() == null ? 0 : login.getType().getId());
			cst.setString(3, login.getEmail());
			cst.setString(4, login.getUser());
			updated = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining update the login.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return updated;
	}

	/**
	 * @param login
	 * @return
	 */
	public static boolean exists(Login login) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_login_EXISTS(?,?);";
			cst = conn.prepareCall(sql);
			cst.setInt(1, login.getId());
			cst.setString(2, login.getUser());
			rs = cst.executeQuery();
			if (rs.next())
				result = rs.getInt(1) > 0;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning checked if the login user name exists.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean signIn(Business business) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		Login login = business.getUser().getLogin();
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_sec_login_SIGN_IN(?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.setString(1, business.getName());
			cst.setString(2, login.getUser());
			cst.setString(3, login.getPassword());
			cst.setInt(4, login.getType().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1) > 0;
				if (result) {
					business.setId(rs.getInt(2));
					business.getUser().setId(rs.getInt(3));
					business.getUser().setName(rs.getString(4));
					login.setId(rs.getInt(5));
				}
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning sign in.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

}
