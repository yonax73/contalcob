/**
 * 
 */
package com.contalcob.set.location.city.entity;

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
public class City extends Entity {

	private static final long serialVersionUID = 1L;
	private String city;
	private String country;

	/**
	 * @param id
	 */
	public City(int id) {
		super(id);
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
