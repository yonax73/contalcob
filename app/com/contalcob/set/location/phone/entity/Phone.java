/**
 * 
 */
package com.contalcob.set.location.phone.entity;

import com.contalcob.set.masterValue.entity.MasterValue;
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
public class Phone extends Entity {

	public static final int LANDLINE = 41;
	public static final int MOBILE = 42;

	private static final long serialVersionUID = 1L;
	private String number;
	private String extension;
	private MasterValue type;

	/**
	 * @param id
	 * 
	 */
	public Phone(int id) {
		super(id);
	}

	public Phone(String number) {
		super(0);
		this.number = number;
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the type
	 */
	public MasterValue getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(MasterValue type) {
		this.type = type;
	}

}
