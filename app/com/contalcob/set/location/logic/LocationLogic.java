/**
 * 
 */
package com.contalcob.set.location.logic;

import java.util.ArrayList;
import java.util.List;

import com.contalcob.set.location.address.dao.AddressDao;
import com.contalcob.set.location.address.entity.Address;
import com.contalcob.set.location.city.dao.CityDao;
import com.contalcob.set.location.city.entity.City;
import com.contalcob.set.location.dao.LocationDao;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.location.phone.dao.PhoneDao;
import com.contalcob.set.location.phone.entity.Phone;

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
public class LocationLogic {

	/**
	 * @param location
	 * @return
	 */
	public static boolean load(Location location) {
		return location != null && location.exists() && LocationDao.load(location);
	}

	/**
	 * @param location
	 * @return
	 */
	public static boolean save(Location location) {
		boolean saved = false;
		// ADDRESS
		Address address = location.getAddress();
		if (address != null) {
			// CITY
			City city = address.getCity();
			if (city != null) {
				if (city.exists()) {
					CityDao.update(city);
				} else {
					CityDao.create(city);
				}
			}
			if (address.exists()) {
				AddressDao.update(address);
			} else {
				AddressDao.create(address);
			}
		}
		// PHONE
		List<Phone> phones = new ArrayList<Phone>();
		Phone landline = location.getLandline();
		Phone mobile = location.getMobilePhone();
		if (landline != null)
			phones.add(landline);
		if (mobile != null)
			phones.add(mobile);
		if (!phones.isEmpty()) {
			PhoneDao.save(phones);
		}
		// LOCATION
		if (!location.exists()) {
			LocationDao.create(location);
		}
		saved = true;
		return saved;
	}

}
