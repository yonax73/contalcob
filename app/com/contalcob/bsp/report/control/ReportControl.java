/**
 * 
 */
package com.contalcob.bsp.report.control;

import play.mvc.Controller;
import play.mvc.Result;

import com.contalcob.sec.login.control.LoginControl;
import com.contalcob.utils.Constant;

/**
 * company : yonax73@gmail.com <br/>
 * user : YQ<br/>
 * update date : May 20, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : May 20, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class ReportControl extends Controller {

	public static Result dashboard() {
		if (session(Constant.SESSION_LOGIN_ID) == null) {
			return LoginControl.signOut();
		}
		return ok(views.html.bsp.report.dashboard.render());
	}
}
