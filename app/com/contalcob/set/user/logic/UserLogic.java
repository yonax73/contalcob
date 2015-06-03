/**
 * 
 */
package com.contalcob.set.user.logic;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.set.user.dao.UserDao;
import com.contalcob.set.user.entity.User;

/**
 * company : yonax73@gmail.com <br/>
 * user : yonatan<br/>
 * update date : May 24, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 24, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class UserLogic {

	/**
	 * @param business
	 * @return
	 */
	public static boolean save(Business business) {
		boolean saved = false;
		User user = business.getUser();
		if (user.exists()) {
			saved = UserDao.update(user);
		} else if (business.exists()) {
			saved = UserDao.create(business);
		}
		return saved;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean search(Business business) {
		return business.exists() && UserDao.search(business);
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean load(User user) {
		return user.exists() && UserDao.load(user);
	}

}
