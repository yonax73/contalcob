/**
 * 
 */
package com.contalcob.bsp.zone.control;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.zone.entity.Zone;
import com.contalcob.bsp.zone.logic.ZoneLogic;
import com.contalcob.sec.login.control.LoginControl;
import com.contalcob.set.location.address.entity.Address;
import com.contalcob.set.location.city.entity.City;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.location.logic.LocationLogic;
import com.contalcob.set.user.entity.User;
import com.contalcob.utils.Constant;

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
public class ZoneControl extends Controller {

	public static Result zones() {
		if (session(Constant.SESSION_LOGIN_ID) == null) {
			return LoginControl.signOut();
		}
		return ok(views.html.bsp.zone.zones.render());
	}

	public static Result zone() {
		if (session(Constant.SESSION_LOGIN_ID) == null) {
			return LoginControl.signOut();
		}
		return ok(views.html.bsp.zone.zone.render());
	}

	public static Result search() {
		try {
			String result = "No hay zonas de trabajo";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
				User user = new User(Integer.parseInt(data.get("userId")[0]));
				User excludeUser = new User(Integer.parseInt(data.get("excludeUserId")[0]));
				business.setUser(user);
				business.setExcludeUser(excludeUser);
				if (ZoneLogic.search(business)) {
					result = Json.stringify(Json.toJson(business));
				}
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando buscar las zonas de trabajo \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando buscar las zonas de trabajo ( " + e.getMessage() + " )");
		}
	}

	public static Result load(int id) {
		try {
			String result = "No se ha podido completar la solicitud";
			Zone zone = new Zone(id);
			if (ZoneLogic.load(zone)) {
				result = Json.stringify(Json.toJson(zone));
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar la zona de trabajo \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando cargar la zona de trabajo ( " + e.getMessage() + " )");
		}
	}

	public static Result save() {
		try {
			String result = "No se ha podido completar la solicitud. \n";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
				Zone zone = new Zone(Integer.parseInt(data.get("zoneId")[0]));
				zone.setName(data.get("zoneName")[0]);
				zone.setDescription(data.get("zoneDescription")[0]);
				Location location = new Location(Integer.parseInt(data.get("locationId")[0]));
				City city = new City(Integer.parseInt(data.get("cityId")[0]));
				city.setCity(data.get("city")[0]);
				city.setCountry(data.get("country")[0]);
				Address address = new Address(Integer.parseInt(data.get("addressId")[0]));
				address.setNeighborhood(data.get("neighborhood")[0]);
				address.setCity(city);
				location.setAddress(address);
				zone.setLocation(location);
				business.setZone(zone);
				// LOCATION
				if (!LocationLogic.save(location)) {
					result += "Error al guardar los datos de ubicaci&oacute;n \n";
				}
				// ZONE
				if (ZoneLogic.save(business)) {
					result = Json.stringify(Json.toJson(zone));
				} else {
					result += "Error al guardar los datos de la zona de trabajo \n";
				}
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando guardar la zona de trabajo \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando guardar la zona de trabajo ( " + e.getMessage() + " )");
		}
	}

	public static Result removeUserZone(int userId, int zoneId) {
		try {
			String result = "No se ha podido completar la solicitud";
			User user = new User(userId);
			Zone zone = new Zone(zoneId);
			user.setZone(zone);
			if (ZoneLogic.removeUserZone(user)) {
				result = Constant.RESPONSE_OK;
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando remover la zona de trabajo \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando remover la zona de trabajo ( " + e.getMessage() + " )");
		}
	}

	public static Result addUserZone(int userId, int zoneId) {
		try {
			String result = "No se ha podido completar la solicitud";
			User user = new User(userId);
			Zone zone = new Zone(zoneId);
			user.setZone(zone);
			if (ZoneLogic.addUserZone(user)) {
				result = Constant.RESPONSE_OK;
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando remover la zona de trabajo \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando remover la zona de trabajo ( " + e.getMessage() + " )");
		}
	}
}
