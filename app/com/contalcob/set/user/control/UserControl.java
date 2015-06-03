/**
 * 
 */
package com.contalcob.set.user.control;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.zone.logic.ZoneLogic;
import com.contalcob.sec.login.control.LoginControl;
import com.contalcob.sec.login.entity.Login;
import com.contalcob.sec.login.logic.LoginLogic;
import com.contalcob.set.location.entity.Location;
import com.contalcob.set.location.logic.LocationLogic;
import com.contalcob.set.location.phone.entity.Phone;
import com.contalcob.set.masterValue.entity.MasterValue;
import com.contalcob.set.user.entity.User;
import com.contalcob.set.user.logic.UserLogic;
import com.contalcob.utils.Constant;

/**
 * company : yonax73@gmail.com <br/>
 * user : yonatan<br/>
 * update date : May 24, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 24, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class UserControl extends Controller {

	public static Result users() {
		if (session(Constant.SESSION_LOGIN_ID) == null) {
			return LoginControl.signOut();
		}
		return ok(views.html.set.user.users.render());
	}

	public static Result user() {
		if (session(Constant.SESSION_LOGIN_ID) == null) {
			return LoginControl.signOut();
		}
		return ok(views.html.set.user.user.render());
	}

	public static Result userZones(int id) {
		try {
			String result = "No hay zonas para este usuario";
			Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
			User user = new User(id);
			if (ZoneLogic.search(business)) {
				result = Json.stringify(Json.toJson(user));
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar las zonas de este usuario \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando cargar las zonas de este usuario ( " + e.getMessage() + " )");
		}
	}

	public static Result search() {
		try {
			String result = "No hay usuarios";
			Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
			User user = new User(0);
			Login login = new Login(0);
			login.setType(new MasterValue(Login.COLLECTOR));
			user.setLogin(login);
			business.setUser(user);
			if (UserLogic.search(business)) {
				result = Json.stringify(Json.toJson(business));
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando buscar los usuarios \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando buscar los usuarios ( " + e.getMessage() + " )");
		}
	}

	public static Result load(int id) {
		try {
			String result = "No se ha podido completar la solicitud";
			User user = new User(id);
			if (UserLogic.load(user)) {
				result = Json.stringify(Json.toJson(user));
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar los usuarios \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando cargar los usuarios ( " + e.getMessage() + " )");
		}
	}

	public static Result save() {
		try {
			String result = "No se ha podido completar la solicitud. \n";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
				User user = new User(Integer.parseInt(data.get("userId")[0]));
				Login login = new Login(Integer.parseInt(data.get("loginId")[0]));
				Location location = new Location(Integer.parseInt(data.get("locationId")[0]));
				Phone mobile = new Phone(Integer.parseInt(data.get("mobilePhoneId")[0]));
				mobile.setNumber(data.get("mobilePhone")[0]);
				mobile.setType(new MasterValue(Phone.MOBILE));
				location.setMobilePhone(mobile);
				login.setEmail(data.get("emailLogin")[0]);
				login.setUser(data.get("userLogin")[0]);
				login.setPassword(data.get("password")[0]);
				login.setType(new MasterValue(Integer.parseInt(data.get("loginTypeId")[0])));
				user.setLocation(location);
				user.setName(data.get("userFullName")[0]);
				user.setLogin(login);
				business.setUser(user);
				// CHECK USER LOGIN
				if (!LoginLogic.exists(login)) {
					// LOCATION
					if (!LocationLogic.save(location)) {
						result += "Error al guardar los datos de ubicaci&oacute;n \n";
					}
					// LOGIN
					if (!LoginLogic.save(login)) {
						result += "Error al guardar los datos de la cuenta. \n";
					}
					// USER
					if (UserLogic.save(business)) {
						result = Json.stringify(Json.toJson(business.getUser()));
					} else {
						result += "Error al guardar los datos del usuario. \n";
					}

				} else {
					result += "Este nombre de usuario no esta disponible.\n";
				}
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando guardar los datos del usuario \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando guardar los datos del usuario ( " + e.getMessage() + " )");
		}
	}
}
