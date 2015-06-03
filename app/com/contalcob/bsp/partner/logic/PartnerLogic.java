/**
 * 
 */
package com.contalcob.bsp.partner.logic;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.partner.dao.PartnerDao;
import com.contalcob.bsp.partner.entity.Partner;
import com.contalcob.set.location.logic.LocationLogic;

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
public class PartnerLogic {

	/**
	 * @param business
	 * @return
	 */
	public static boolean search(Business business) {
		return business.exists() && PartnerDao.search(business);
	}

	/**
	 * @param partner
	 * @return
	 */
	public static boolean load(Partner partner) {
		boolean loaded = false;
		if (partner.exists() && PartnerDao.load(partner)) {
			if (partner.getLocation() != null) {
				loaded = LocationLogic.load(partner.getLocation());
			}
		}
		return loaded;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean save(Business business) {
		boolean saved = false;
		Partner partner = business.getPartner();
		if (partner.exists()) {
			saved = PartnerDao.update(partner);
		} else if (business.exists()) {
			saved = PartnerDao.create(business);
		}
		return saved;
	}

}
