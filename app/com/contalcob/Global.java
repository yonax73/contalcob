/**
 * 
 */
package com.contalcob;

import static play.mvc.Results.badRequest;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

/**
 * company : ContalCob S.A<br/>
 * user : YQ<br/>
 * created : Apr 13, 2015<br/>
 * update date : Apr 13, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

	@Override
	public Promise<Result> onBadRequest(RequestHeader request, String error) {
		Logger.error("Bad Request uri: " + request.uri());
		Logger.error("Bad Request Error: " + error);
		return Promise.<Result> pure(badRequest("Don't try to hack the URI!"));
	}

}
