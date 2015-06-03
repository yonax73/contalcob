/**
 * 
 */
package com.contalcob.set.location.entity;

import com.contalcob.set.location.address.entity.Address;
import com.contalcob.set.location.phone.entity.Phone;

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
public class Location extends Phone {

	private static final long serialVersionUID = 1L;
	private Address address;
	private Phone landline;
	private Phone mobilePhone;

	/**
	 * @param id
	 */
	public Location(int id) {
		super(id);

	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the landline
	 */
	public Phone getLandline() {
		return landline;
	}

	/**
	 * @param landline
	 *            the landline to set
	 */
	public void setLandline(Phone landline) {
		this.landline = landline;
	}

	/**
	 * @return the mobilePhone
	 */
	public Phone getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @param mobilePhone
	 *            the mobilePhone to set
	 */
	public void setMobilePhone(Phone mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}
