/**
 * 
 */
package com.contalcob.bsp.zone.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.zone.entity.Zone;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.user.entity.User;
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
public class ZoneDao extends Dao {

	public static boolean create(Business business) {
		boolean created = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			Zone zone = business.getZone();
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_zones_CREATE(?,?,?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, zone.getLocation() == null ? 0 : zone.getLocation().getId());
			cst.setString(3, zone.getName());
			cst.setString(4, zone.getDescription());
			cst.setInt(5, business.getId());
			created = cst.executeUpdate() > 0;
			if (created) {
				zone.setId(cst.getInt(1));
				created = zone.exists();
			}
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining created the zone.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return created;
	}

	public static boolean update(Zone zone) {
		boolean updated = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_zones_UPDATE(?,?,?,?)");
			cst.setInt(1, zone.getId());
			cst.setInt(2, zone.getLocation() == null ? 0 : zone.getLocation().getId());
			cst.setString(3, zone.getName());
			cst.setString(4, zone.getDescription());
			updated = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining update the zone.\n" + e.getMessage(), e);
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
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_zones_SEARCH(?,?,?)");
			cst.setInt(1, business.getId());
			cst.setInt(2, business.getUser() == null ? 0 : business.getUser().getId());
			cst.setInt(3, business.getExcludeUser() == null ? 0 : business.getExcludeUser().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				business.setZones(new ArrayList<Zone>());
				List<Zone> zones = business.getZones();
				do {
					Zone zone = new Zone(rs.getInt(1));
					zone.setLocation(new Location(rs.getInt(2)));
					zone.setName(rs.getString(3));
					zone.setDescription(rs.getString(4));
					zones.add(zone);
				} while (rs.next());
			}
			loaded = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the zones.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return loaded;
	}

	public static boolean load(Zone zone) {
		boolean loaded = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_zones_LOAD(?)");
			cst.setInt(1, zone.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				zone.setLocation(new Location(rs.getInt(1)));
				zone.setName(rs.getString(2));
				zone.setDescription(rs.getString(3));
			}
			loaded = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the zone.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return loaded;
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean removeUserZone(User user) {
		boolean removed = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_user_zones_REMOVE(?,?)");
			cst.setInt(1, user.getZone().getId());
			cst.setInt(2, user.getId());
			removed = cst.executeUpdate() > 0;
		} catch (Exception e) {
		} finally {
			close(cst, conn);
		}
		return removed;
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean addUserZone(User user) {
		boolean added = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_user_zones_ADD(?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, user.getId());
			cst.setInt(3, user.getZone().getId());
			added = cst.executeUpdate() > 0 && cst.getInt(1) > 0;

		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining add the zone.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return added;
	}

}
