/**
 * 
 */
package com.contalcob.sec.login.logic;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.sec.login.dao.LoginDao;
import com.contalcob.sec.login.entity.Login;

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
public class LoginLogic {

	/**
	 * @param login
	 * @return
	 */
	public static boolean exists(Login login) {
		boolean exists = false;
		if (login != null && !login.isEmpty()) {
			exists = LoginDao.exists(login);
		}
		return exists;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean signIn(Business business) {
		boolean result = false;
		if (business != null) {
			result = LoginDao.signIn(business);
		}
		return result;
	}

	/**
	 * @param login
	 * @return
	 */
	public static boolean save(Login login) {
		boolean saved = false;
		if (login.exists()) {
			saved = LoginDao.update(login);
		} else {
			saved = LoginDao.create(login);
		}
		return saved;
	}

}
