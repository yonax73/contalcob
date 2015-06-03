/**
 * 
 */
package com.contalcob.set.user.entity;

import java.util.List;

import com.contalcob.bsp.zone.entity.Zone;
import com.contalcob.sec.login.entity.Login;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.picture.entity.Picture;
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
public class User extends Entity {

	private static final long serialVersionUID = 1L;
	private Login login;
	private Location location;
	private Picture picture;
	private Zone zone;
	private List<Zone> zones;
	private String name;

	/**
	 * @param id
	 */
	public User(int id) {
		super(id);
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
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

	/**
	 * @return the picture
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	/**
	 * @return the zone
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * @return the zones
	 */
	public List<Zone> getZones() {
		return zones;
	}

	/**
	 * @param zones
	 *            the zones to set
	 */
	public void setZones(List<Zone> zones) {
		this.zones = zones;
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

}
