/**
 * 
 */
package com.contalcob.bsp.business.control;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.contalcob.bsp.business.entity.Business;
import com.contalcob.bsp.business.logic.BusinessLogic;
import com.contalcob.sec.login.control.LoginControl;
import com.contalcob.sec.login.entity.Login;
import com.contalcob.sec.login.logic.LoginLogic;
import com.contalcob.set.masterValue.entity.MasterValue;
import com.contalcob.set.user.entity.User;
import com.contalcob.utils.Constant;

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

public class BusinessControl extends Controller {

	public static Result business() {
		return ok(views.html.bsp.business.business.render());
	}

	public static Result save() {
		try {
			String result = "No se ha podido completar la solicitud";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Business business = new Business(session(Constant.SESSION_BUSINESS_ID) == null ? 0 : Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
				User user = new User(session(Constant.SESSION_USER_ID) == null ? 0 : Integer.parseInt(session(Constant.SESSION_USER_ID)));
				Login login = new Login(session(Constant.SESSION_LOGIN_ID) == null ? 0 : Integer.parseInt(session(Constant.SESSION_LOGIN_ID)));
				business.setName(data.get("businessName")[0]);
				user.setName(data.get("userName")[0]);
				login.setEmail(data.get("emailLogin")[0]);
				login.setUser(data.get("userLogin")[0]);
				login.setPassword(data.get("password")[0]);
				login.setType(new MasterValue(Login.LENDER));
				user.setLogin(login);
				business.setUser(user);
				if (!BusinessLogic.exists(business)) {
					if (!LoginLogic.exists(login)) {
						if (BusinessLogic.save(business)) {
							result = Constant.RESPONSE_OK;
						} else {
							result = "Ha ocurrido un error intentando guarda los datos del negocio.";
						}
					} else {
						result = "Este nombre de usuario no esta disponible";
					}
				} else {
					result = "Este nombre del negocio no esta disponible";
				}
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando crear la empresa \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error crear crear la empresa ( " + e.getMessage() + " )");
		}
	}

	public static Result load() {
		try {
			if (session(Constant.SESSION_LOGIN_ID) == null) {
				return LoginControl.signOut();
			}
			String result = "No se ha podido completar la solicitud";
			Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
			if (BusinessLogic.load(business)) {
				result = Json.stringify(Json.toJson(business));
			} else {
				result = "Error cargando los datos del negocio";
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando cargar los datos del negocio \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando cargar los datos del negocio ( " + e.getMessage() + " )");
		}
	}
}
