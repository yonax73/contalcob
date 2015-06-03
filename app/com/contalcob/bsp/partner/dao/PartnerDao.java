/**
 * 
 */
package com.contalcob.bsp.partner.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.partner.entity.Partner;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.location.phone.entity.Phone;
import com.contalcob.utils.base.dao.Dao;

/**
 * company : yonax73@gmail.com <br/>
 * user : YQ<br/>
 * update date : Jun 2, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Jun 2, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class PartnerDao extends Dao {

	public static boolean create(Business business) {
		boolean created = false;
		CallableStatement cst = null;
		Connection conn = null;
		Partner partner = business.getPartner();
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_partners_CREATE(?,?,?,?)");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, partner.getLocation() == null ? 0 : partner.getLocation().getId());
			cst.setInt(3, business == null ? 0 : business.getId());
			cst.setString(4, partner.getName());
			created = cst.executeUpdate() > 0;
			if (created) {
				partner.setId(cst.getInt(1));
				created = partner.exists();
			}
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining created the partner.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return created;
	}

	public static boolean update(Partner partner) {
		boolean updated = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_partners_UPDATE(?,?,?)");
			cst.setInt(1, partner.getId());
			cst.setInt(2, partner.getLocation() == null ? 0 : partner.getLocation().getId());
			cst.setString(3, partner.getName());
			updated = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining update the partner.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return updated;
	}

	public static boolean load(Partner partner) {
		boolean loaded = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_partners_LOAD(?)");
			cst.setInt(1, partner.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				partner.setLocation(new Location(rs.getInt(1)));
				partner.setName(rs.getString(2));
			}
			loaded = true;
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the partner.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return loaded;
	}

	public static boolean search(Business business) {
		boolean loaded = false;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL contalcob_schema.sp_bsp_partners_SEARCH(?)");
			cst.setInt(1, business.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				business.setPartners(new ArrayList<Partner>());
				List<Partner> partners = business.getPartners();
				do {
					Partner partner = new Partner(rs.getInt(1));
					Location location = new Location(rs.getInt(2));
					partner.setName(rs.getString(3));
					Phone mobile = new Phone(rs.getString(4));
					location.setMobilePhone(mobile);
					partner.setLocation(location);
					partners.add(partner);
				} while (rs.next());
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
