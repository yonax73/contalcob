/**
 * 
 */
package com.contalcob.set.location.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.contalcob.set.location.address.entity.Address;
import com.contalcob.set.location.city.entity.City;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.location.phone.entity.Phone;
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
public class LocationDao extends Dao {

	public static boolean create(Location location) {
		boolean created = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_locations_CREATE(?,?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, location.getAddress() == null ? 0 : location.getAddress().getId());
			cst.setInt(3, location.getLandline() == null ? 0 : location.getLandline().getId());
			cst.setInt(4, location.getMobilePhone() == null ? 0 : location.getMobilePhone().getId());
			created = cst.executeUpdate() > 0;
			if (created) {
				location.setId(cst.getInt(1));
				created = location.exists();
			}
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining created the location.\n" + e.getMessage());
		} finally {
			close(cst, conn);
		}
		return created;
	}

	public static boolean load(Location location) {
		boolean loaded = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_locations_LOAD(?)");
			cst.setInt(1, location.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				Address address = new Address(rs.getInt(1));
				address.setAddress(rs.getString(2));
				address.setNeighborhood(rs.getString(3));
				City city = new City(rs.getInt(4));
				city.setCity(rs.getString(5));
				city.setCountry(rs.getString(6));
				Phone landline = new Phone(rs.getInt(7));
				landline.setNumber(rs.getString(8));
				landline.setExtension(rs.getString(9));
				Phone mobile = new Phone(rs.getInt(10));
				mobile.setNumber(rs.getString(11));
				address.setCity(city);
				location.setAddress(address);
				location.setLandline(landline);
				location.setMobilePhone(mobile);
			}
			loaded = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the location.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return loaded;
	}

}
