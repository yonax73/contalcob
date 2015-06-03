/**
 * 
 */
package com.contalcob.bsp.business.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.sec.login.entity.Login;
import com.contalcob.set.user.entity.User;
import com.contalcob.utils.base.dao.Dao;

/**
 * company : yonax73@gmail.com <br/>
 * user : yonatan<br/>
 * update date : May 18, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 18, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class BusinessDao extends Dao {

	/**
	 * @param business
	 * @return
	 */
	public static boolean update(Business business) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_bsp_business_UPDATE(?,?,?,?,?,?,?)");
			User user = business.getUser();
			Login login = user.getLogin();
			cst.setInt(1, business.getId());
			cst.setInt(2, user.getId());
			cst.setInt(3, login.getId());
			cst.setString(4, business.getName());
			cst.setString(5, user.getName());
			cst.setString(6, login.getEmail());
			cst.setString(7, login.getUser());
			cst.execute();
			result = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning updated the business.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean create(Business business) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL contalcob_schema.sp_bsp_business_CREATE(?,?,?,?,?,?,?,?,?);";
			cst = conn.prepareCall(sql);
			User user = business.getUser();
			Login login = user.getLogin();
			cst.registerOutParameter(1, Types.INTEGER);
			cst.registerOutParameter(2, Types.INTEGER);
			cst.registerOutParameter(3, Types.INTEGER);
			cst.setString(4, business.getName());
			cst.setString(5, user.getName());
			cst.setString(6, login.getEmail());
			cst.setString(7, login.getUser());
			cst.setString(8, login.getPassword());
			cst.setInt(9, login.getType().getId());
			cst.execute();
			business.setId(cst.getInt(1));
			user.setId(cst.getInt(2));
			login.setId(cst.getInt(3));
			result = business.exists() && user.exists() && login.exists();
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning saved the business.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean exists(Business business) {
		boolean result = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_business_EXISTS(?,?);";
			cst = conn.prepareCall(sql);
			cst.setInt(1, business.getId());
			cst.setString(2, business.getName());
			rs = cst.executeQuery();
			if (rs.next())
				result = rs.getInt(1) > 0;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning checked if the business name exists.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return result;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean load(Business business) {
		boolean loaded = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_business_LOAD(?)}");
			cst.setInt(1, business.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				User user = new User(0);
				Login login = new Login(0);
				business.setName(rs.getString(1));
				user.setName(rs.getString(2));
				login.setEmail(rs.getString(3));
				login.setUser(rs.getString(4));
				user.setLogin(login);
				business.setUser(user);
			}
			loaded = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the business.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return loaded;
	}

}
