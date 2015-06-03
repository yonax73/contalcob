/**
 * 
 */
package com.contalcob.bsp.business.entity;

import java.util.List;

import com.contalcob.bsp.partner.entity.Partner;
import com.contalcob.bsp.zone.entity.Zone;
import com.contalcob.set.user.entity.User;
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
public class Business extends Entity {

	private static final long serialVersionUID = 1L;
	private String name;
	private User user;
	private Zone zone;
	private Partner partner;
	private List<Partner> partners;
	private List<User> users;
	private List<Zone> zones;

	private User excludeUser;

	/**
	 * @param id
	 */
	public Business(int id) {
		super(id);
	}

	@Override
	public boolean isEmpty() {
		if (name == null)
			return false;
		else
			return name.isEmpty();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * @return the excludeUser
	 */
	public User getExcludeUser() {
		return excludeUser;
	}

	/**
	 * @param excludeUser
	 *            the excludeUser to set
	 */
	public void setExcludeUser(User excludeUser) {
		this.excludeUser = excludeUser;
	}

	/**
	 * @return the partner
	 */
	public Partner getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	/**
	 * @return the partners
	 */
	public List<Partner> getPartners() {
		return partners;
	}

	/**
	 * @param partners
	 *            the partners to set
	 */
	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

}
