/**
 * 
 */
package com.contalcob.bsp.zone.logic;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.zone.dao.ZoneDao;
import com.contalcob.bsp.zone.entity.Zone;
import com.contalcob.set.location.logic.LocationLogic;
import com.contalcob.set.user.entity.User;

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
public class ZoneLogic {

	/**
	 * @param business
	 * @return
	 */
	public static boolean search(Business business) {
		return business.exists() && ZoneDao.search(business);
	}

	/**
	 * @param zone
	 * @return
	 */
	public static boolean load(Zone zone) {
		boolean loaded = false;
		if (zone.exists() && ZoneDao.load(zone)) {
			if (zone.getLocation() != null) {
				loaded = LocationLogic.load(zone.getLocation());
			}
		}
		return loaded;
	}

	/**
	 * @param zone
	 * @return
	 */
	public static boolean save(Business business) {
		boolean saved = false;
		Zone zone = business.getZone();
		if (zone.exists()) {
			saved = ZoneDao.update(zone);
		} else if (business.exists()) {
			saved = ZoneDao.create(business);
		}
		return saved;
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean removeUserZone(User user) {
		boolean removed = false;
		if (user != null && user.exists()) {
			Zone zone = user.getZone();
			if (zone != null && zone.exists()) {
				removed = ZoneDao.removeUserZone(user);
			}
		}
		return removed;
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean addUserZone(User user) {
		boolean added = false;
		if (user != null && user.exists()) {
			Zone zone = user.getZone();
			if (zone != null && zone.exists()) {
				added = ZoneDao.addUserZone(user);
			}
		}
		return added;
	}

}
