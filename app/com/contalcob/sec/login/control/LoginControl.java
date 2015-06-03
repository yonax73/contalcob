/**
 * 
 */
package com.contalcob.sec.login.control;

import java.util.Map;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.contalcob.bsp.business.entity.Business;
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
public class LoginControl extends Controller {

	public static Result login() {
		session().clear();
		return ok(views.html.sec.login.login.render());
	}

	public static Result signIn() {
		try {
			String result = "No se ha podido completar la solicitud";
			final Map<String, String[]> data = request().body().asFormUrlEncoded();
			if (data != null) {
				Business business = new Business(0);
				User user = new User(0);
				Login login = new Login(0);
				business.setName(data.get("businessName")[0]);
				login.setUser(data.get("userLogin")[0]);
				login.setPassword(data.get("password")[0]);
				login.setType(new MasterValue(Integer.parseInt(data.get("loginType")[0])));
				user.setLogin(login);
				business.setUser(user);
				if (LoginLogic.signIn(business)) {
					session(Constant.SESSION_BUSINESS_ID, String.valueOf(business.getId()));
					session(Constant.SESSION_BUSINESS_NAME, business.getName());
					session(Constant.SESSION_USER_ID, String.valueOf(user.getId()));
					session(Constant.SESSION_USER_NAME, user.getName());
					session(Constant.SESSION_LOGIN_ID, String.valueOf(login.getId()));
					session(Constant.SESSION_LOGIN_USER, login.getUser());
					result = Constant.RESPONSE_OK;
				} else {
					result = "El nombre del negocio, el usuario o la contrase&ntilde;a son incorrectos.";
				}
			} else {
				result = "Este nombre de usuario no esta disponible";
			}

			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando ingresar al sistema \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando ingresar al sistema ( " + e.getMessage() + " )");
		}
	}

	public static Result signOut() {
		return redirect("/");
	}

	public static Result initApp() {
		try {
			String result = "No se ha podido completar la solicitud";
			Business business = new Business(Integer.parseInt(session(Constant.SESSION_BUSINESS_ID)));
			User user = new User(Integer.parseInt(session(Constant.SESSION_USER_ID)));
			Login login = new Login(Integer.parseInt(session(Constant.SESSION_LOGIN_ID)));
			if (business.exists() && user.exists() && login.exists()) {
				business.setName(session(Constant.SESSION_BUSINESS_NAME));
				user.setName(session(Constant.SESSION_USER_NAME));
				login.setUser(session(Constant.SESSION_LOGIN_USER));
				user.setLogin(login);
				business.setUser(user);
				result = Json.stringify(Json.toJson(business));
			} else {
				return signOut();
			}
			return ok(result);
		} catch (Exception e) {
			Logger.error("Ha ocurrido un error intentando iniciar al sistema \n" + e.getMessage(), e);
			return badRequest("Ha ocurrido un error intentando iniciar al sistema ( " + e.getMessage() + " )");
		}
	}
}
