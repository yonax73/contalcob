/**
 * 
 */
package com.contalcob.utils.base.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public abstract class Entity implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	protected int id;
	protected boolean active;
	protected LocalDateTime created;

	public Entity() {
	}

	public Entity(int id) {
		this.id = id;
	}

	public abstract boolean isEmpty();

	public boolean exists() {
		return this.id > 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
