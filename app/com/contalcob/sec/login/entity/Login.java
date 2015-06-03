/**
 * 
 */
package com.contalcob.sec.login.entity;

import com.contalcob.set.masterValue.entity.MasterValue;
import com.contalcob.utils.base.entity.Entity;

/**
 * company : yonax73@gmail.com <br/>
 * user : yonatan<br/>
 * update date : May 16, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 16, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Login extends Entity {

	public static final int LENDER = 39;
	public static final int COLLECTOR = 40;
	private static final long serialVersionUID = 1L;
	private String email;
	private String user;
	private String password;
	private MasterValue type;

	/**
	 * @param id
	 */
	public Login(int id) {
		super(id);
	}

	@Override
	public boolean isEmpty() {
		if (user != null)
			return user.isEmpty();
		else
			return true;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public MasterValue getType() {
		return type;
	}

	public void setType(MasterValue type) {
		this.type = type;
	}

}
