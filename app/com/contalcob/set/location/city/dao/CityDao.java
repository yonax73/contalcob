/**
 * 
 */
package com.contalcob.set.location.city.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.contalcob.set.location.city.entity.City;
import com.contalcob.utils.base.dao.Dao;

/**
 * company : yonax73@gmail.com <br/>
 * user : YQ<br/>
 * update date : May 22, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 22, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class CityDao extends Dao {

	public static boolean create(City city) {
		boolean created = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_cities_CREATE(?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, city.getCity());
			cst.setString(3, city.getCountry());
			created = cst.executeUpdate() > 0;
			if (created) {
				city.setId(cst.getInt(1));
				created = city.exists();
			}
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining created the city.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return created;
	}

	public static boolean update(City city) {
		boolean updated = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_cities_UPDATE(?,?,?)");
			cst.setInt(1, city.getId());
			cst.setString(2, city.getCity());
			cst.setString(3, city.getCountry());
			updated = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining update the city.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return updated;
	}
}
