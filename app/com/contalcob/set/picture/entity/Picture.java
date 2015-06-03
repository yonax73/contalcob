/**
 * 
 */
package com.contalcob.set.picture.entity;

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
public class Picture extends Entity {

	private static final long serialVersionUID = 1L;
	private String path;
	private boolean main;

	@Override
	public boolean isEmpty() {

		return false;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
