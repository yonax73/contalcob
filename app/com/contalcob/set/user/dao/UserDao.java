/**
 * 
 */
package com.contalcob.set.user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.sec.login.entity.Login;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.location.phone.entity.Phone;
import com.contalcob.set.picture.entity.Picture;
import com.contalcob.set.user.entity.User;
import com.contalcob.utils.base.dao.Dao;

/**
 * company : yonax73@gmail.com <br/>
 * user : yonatan<br/>
 * update date : May 23, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 23, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class UserDao extends Dao {
	public static boolean create(Business business) {
		boolean created = false;
		CallableStatement cst = null;
		Connection conn = null;
		User user = business.getUser();
		Login login = user.getLogin();
		Location location = user.getLocation();
		Picture picture = user.getPicture();
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_users_CREATE(?,?,?,?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, login == null ? 0 : login.getId());
			cst.setInt(3, location == null ? 0 : location.getId());
			cst.setInt(4, picture == null ? 0 : picture.getId());
			cst.setInt(5, business == null ? 0 : business.getId());
			cst.setString(6, user.getName());
			created = cst.executeUpdate() > 0;
			if (created) {
				user.setId(cst.getInt(1));
				created = business.exists();
			}
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining created the user.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return created;
	}

	public static boolean update(User user) {
		boolean updated = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_users_UPDATE(?,?)");
			cst.setInt(1, user.getId());
			cst.setString(2, user.getName());
			updated = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining update the user.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return updated;
	}

	public static boolean search(Business business) {
		boolean loaded = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_users_SEARCH(?,?)");
			cst.setInt(1, business.getId());
			cst.setInt(2, business.getUser().getLogin().getType().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				business.setUsers(new ArrayList<User>());
				List<User> users = business.getUsers();
				do {
					Location location = new Location(0);
					Phone mobile = new Phone(0);
					Login login = new Login(0);
					User user = new User(rs.getInt(1));
					user.setName(rs.getString(2));
					login.setUser(rs.getString(3));
					mobile.setNumber(rs.getString(4));
					location.setMobilePhone(mobile);
					user.setLogin(login);
					user.setLocation(location);
					users.add(user);
				} while (rs.next());
			}
			loaded = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning search the users.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return loaded;
	}

	public static boolean load(User user) {
		boolean loaded = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_users_LOAD(?)");
			cst.setInt(1, user.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				user.setName(rs.getString(1));
				user.setLocation(new Location(0));
				user.getLocation().setMobilePhone(new Phone(rs.getInt(2)));
				user.getLocation().getMobilePhone().setNumber(rs.getString(3));
				user.setLogin(new Login(rs.getInt(4)));
				user.getLogin().setEmail(rs.getString(5));
				user.getLogin().setUser(rs.getString(6));
			}
			loaded = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the user.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return loaded;
	}

}
