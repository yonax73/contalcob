/**
 * 
 */
package com.contalcob.set.location.phone.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

import play.Logger;
import play.db.DB;

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
public class PhoneDao extends Dao {

	public static boolean save(List<Phone> phones) {
		boolean saved = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			if (phones != null && !phones.isEmpty()) {
				conn = DB.getConnection();
				for (Phone phone : phones) {
					if (phone.exists()) {
						cst = conn.prepareCall("CALL contalcob_schema.sp_set_phones_UPDATE(?,?,?,?)");
						cst.setInt(1, phone.getId());
						cst.setString(2, phone.getNumber());
						cst.setString(3, phone.getExtension());
						cst.setInt(4, phone.getType() == null ? 0 : phone.getType().getId());
						cst.executeUpdate();
					} else {
						cst = conn.prepareCall("CALL contalcob_schema.sp_set_phones_CREATE(?,?,?,?)");
						cst.registerOutParameter(1, Types.INTEGER);
						cst.setString(2, phone.getNumber());
						cst.setString(3, phone.getExtension());
						cst.setInt(4, phone.getType() == null ? 0 : phone.getType().getId());
						if (cst.executeUpdate() > 0) {
							phone.setId(cst.getInt(1));
						}
					}
				}
			}
			saved = true;
		} catch (Exception e) {
			Logger.error("An error has been ocurred tryining saved the phones.\n" + e.getMessage(), e);
		} finally {
			close(cst, conn);
		}
		return saved;
	}
}
