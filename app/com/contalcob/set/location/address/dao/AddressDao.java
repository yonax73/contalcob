/**
 * 
 */
package com.contalcob.set.location.address.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import play.Logger;
import play.db.DB;

import com.contalcob.set.location.address.entity.Address;
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
public class AddressDao extends Dao {

	public static boolean create(Address address) {
		boolean created = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_addresses_CREATE(?,?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, address.getAddress());
			cst.setString(3, address.getNeighborhood());
			cst.setInt(4, address.getCity() == null ? 0 : address.getCity().getId());
			created = cst.executeUpdate() > 0;
			if (created) {
				address.setId(cst.getInt(1));
				created = address.exists();
			}
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining created the address.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return created;
	}

	public static boolean update(Address address) {
		boolean updated = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_set_addresses_UPDATE(?,?,?,?)");
			cst.setInt(1, address.getId());
			cst.setString(2, address.getAddress());
			cst.setString(3, address.getNeighborhood());
			cst.setInt(4, address.getCity() == null ? 0 : address.getCity().getId());
			updated = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining update the address.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return updated;
	}
}
