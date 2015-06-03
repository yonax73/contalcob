/**
 * 
 */
package com.contalcob.bsp.partner.entity;

import com.contalcob.set.location.entity.Location;
import com.contalcob.utils.base.entity.Entity;

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
public class Partner extends Entity {

	private static final long serialVersionUID = 1L;

	private Location location;
	private String name;

	public Partner(int id) {
		super(id);
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}
