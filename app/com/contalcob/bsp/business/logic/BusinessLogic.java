/**
 * 
 */
package com.contalcob.bsp.business.logic;

import com.contalcob.bsp.business.dao.BusinessDao;
import com.contalcob.bsp.business.entity.Business;

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
public class BusinessLogic {

	/**
	 * @param business
	 * @return
	 */
	public static boolean save(Business business) {
		boolean saved = false;
		if (business != null) {
			if (business.exists()) {
				saved = BusinessDao.update(business);
			} else {
				saved = BusinessDao.create(business);
			}
		}
		return saved;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean exists(Business business) {
		boolean exists = false;
		if (business != null && !business.isEmpty()) {
			exists = BusinessDao.exists(business);
		}
		return exists;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean load(Business business) {
		boolean loaded = false;
		if (business.exists()) {
			loaded = BusinessDao.load(business);
		}
		return loaded;
	}

}
