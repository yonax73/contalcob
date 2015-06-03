/**
 * 
 */
package com.contalcob.bsp.partner.control;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.partner.entity.Partner;
import com.contalcob.bsp.partner.logic.PartnerLogic;
import com.contalcob.sec.login.control.LoginControl;
import com.contalcob.set.location.address.entity.Address;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.location.logic.LocationLogic;
import com.contalcob.set.location.phone.entity.Phone;
import com.contalcob.set.masterValue.entity.MasterValue;
import com.contalcob.utils.Constant;

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
public class PartnerControl extends Controller {

	public static Result partners() {
		if (session(Constant.SESSION_LOGIN_ID) == null) {
			return LoginControl.signOut();
		}
		return ok(views.html.bsp.partner.partners.render());
	}

	public static Result partner() {
		if (session(Constant.SESSION_LOGIN_ID) == null) {
			return LoginControl.signOut();
		}
		return ok(views.html.bsp.partner.partner.render());
	}

	public static Result search() {
		try {
			String result = "No hay clientes";
			Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
			if (PartnerLogic.search(business)) {
				result = Json.stringify(Json.toJson(business));
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando buscar los clientes \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando buscar los clientes ( " + e.getMessage() + " )");
		}
	}

	public static Result load(int id) {
		try {
			String result = "No se ha podido completar la solicitud";
			Partner partner = new Partner(id);
			if (PartnerLogic.load(partner)) {
				result = Json.stringify(Json.toJson(partner));
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar el cliente \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando cargar el cliente ( " + e.getMessage() + " )");
		}
	}

	public static Result save() {
		try {
			String result = "No se ha podido completar la solicitud. \n";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
				Partner partner = new Partner(Integer.parseInt(data.get("partnerId")[0]));
				partner.setName(data.get("partnerName")[0]);
				Location location = new Location(Integer.parseInt(data.get("locationId")[0]));
				Address address = new Address(Integer.parseInt(data.get("addressId")[0]));
				address.setAddress(data.get("address")[0]);
				address.setNeighborhood(data.get("neighborhood")[0]);
				Phone mobile = new Phone(Integer.parseInt(data.get("mobilePhoneId")[0]));
				mobile.setNumber(data.get("mobilePhone")[0]);
				mobile.setType(new MasterValue(Phone.MOBILE));
				Phone landline = new Phone(Integer.parseInt(data.get("landlineId")[0]));
				landline.setNumber(data.get("landline")[0]);
				landline.setType(new MasterValue(Phone.LANDLINE));
				location.setMobilePhone(mobile);
				location.setLandline(landline);
				location.setAddress(address);
				partner.setLocation(location);
				business.setPartner(partner);
				// LOCATION
				if (!LocationLogic.save(location)) {
					result += "Error al guardar los datos de ubicaci&oacute;n \n";
				}
				// ZONE
				if (PartnerLogic.save(business)) {
					result = Json.stringify(Json.toJson(partner));
				} else {
					result += "Error al guardar los datos del cliente \n";
				}
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando guardar el cliente \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando guardar el cliente ( " + e.getMessage() + " )");
		}
	}

}
