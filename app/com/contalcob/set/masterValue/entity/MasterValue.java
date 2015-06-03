/**
 * 
 */
package com.contalcob.set.masterValue.entity;

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
public class MasterValue extends Entity {

	private static final long serialVersionUID = 1L;
	private int masterId;
	private String value1;
	private String value2;
	private String value3;

	/**
	 * @param id
	 */
	public MasterValue(int id) {
		super(id);
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	/**
	 * @return the masterId
	 */
	public int getMasterId() {
		return masterId;
	}

	/**
	 * @param masterId
	 *            the masterId to set
	 */
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	/**
	 * @return the value1
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * @param value1
	 *            the value1 to set
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * @return the value2
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * @param value2
	 *            the value2 to set
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * @return the value3
	 */
	public String getValue3() {
		return value3;
	}

	/**
	 * @param value3
	 *            the value3 to set
	 */
	public void setValue3(String value3) {
		this.value3 = value3;
	}

}
