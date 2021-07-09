package com.paulkapa.btd6gamelogger.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.gson.Gson;
/**
 * <h4>Top level class</h4>
 * <hr>
 * <p>
 * Provides easy access to {@code ID}, {@code name} and {@code type}
 * fields. Also maps these attributes to the columns with
 * respective names making use of the annotation {@code javax.persistence.Column}.
 * <hr>
 * The class is annotated with {@code @MappedSuperClass}, meaning that
 * it will pass the column 
 * </p>
 */
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int ID;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	public BaseEntity() {
		this.name= null;
		this.type = null;
	}

	/**
	 * Preferred Constructor.
	 * @param name
	 * @param type
	 */
	public BaseEntity(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Copy constructor.
	 * @param other
	 */
	public BaseEntity(BaseEntity other) {
		this.name = other.getName();
		this.type = other.getType();
	}

	public BaseEntity getInstance() {return this;}

	public int getID() {return this.ID;}

	public void setID(int ID) {this.ID = ID;}

	public String getName() {return this.name;}

	public void setName(String name) {this.name = name;}

	private boolean hasNotNullName() {return !(this.name == null) ? true : false;}

	private boolean hasNotEmptyName() {return !this.name.trim().equals("") ? true : false;}

	public boolean checkName() {return this.hasNotEmptyName() && this.hasNotNullName() ? true : false;}

	public String getType() {return this.type;}

	public void setType(String type) {this.type = type;}

	/**
	 * Concatenates the attributes of this object instance into a string
	 * and return it.
	 * <p>
	 * <b>Format example</b>: "attr1=<code>val1</code>, [...],
	 * attr2=<code>val2</code>, [...], attrN=<code>valN</code>"
	 * @return a string representation of the object's attributes
	 */
	public String createString() {
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("ID=").append(this.ID);
		sb.append(", name=").append(this.name);
		sb.append(", type=").append(this.type);
		return sb.toString();
	}

	/**
	 * Return a json string representation of this object.
	 * @return json string
	 */
	@Override
	public String toString() {return new Gson().toJson(this);}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (ID != other.ID)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
