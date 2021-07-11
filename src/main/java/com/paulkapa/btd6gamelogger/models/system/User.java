package com.paulkapa.btd6gamelogger.models.system;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <h4>Class that defines the properties of a real user</h4>
 *
 * May be used for database storage, defining {@code password}, {@code email},
 * and {@code creation_date} columns.
 *
 * @Entity name="User"
 * @Table name="users"
 * @see BaseEntity
 */
@Entity(name = "User")
@Table(name = "users")
public class User extends BaseEntity {

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "creation_date")
	private Timestamp creationDate;

	@Transient
	private long accountAge;

	/**
	 * Default constructor.
	 */
	public User() {
		super();
		this.password = null;
		this.email = null;
		this.creationDate = null;
		this.accountAge = 0l;
	}

	/**
	 * Prefferd constructor.
	 * @param name
	 * @param type
	 * @param password
	 */
	public User(String name, String type, String password) {
		super(name, type);
		this.setPassword(password);
		this.email = null;
		this.creationDate = null;
		this.accountAge = 0l;
	}

	public User(String name, String type, String password, String email, Timestamp creationDate) {
		super(name, type);
		this.setPassword(password);
		this.email = email;
		this.setCreationDate(creationDate);
		this.setAccountAge();
	}

	/**
	 * Copy constructor.
	 * @param other
	 */
	public User(User other) {
		super(other.getInstance());
		this.password = other.getPassword();
		this.email = other.getEmail();
		this.setCreationDate(other.getCreationDate());
		this.accountAge = other.getAccountAge();
	}

	public static User getDefaultUser() {return new User("btd6gluser", "anonymous", "pass", null, null);}

	public String getPassword() {return this.password;}

	public void setPassword(String password) {
		try {this.password = User.encryptPassword(password);
		} catch (NoSuchAlgorithmException e) {e.printStackTrace();}
	}

	/**
	 * Encrypts a password string provided as parameter.
	 * @param password the password to be encrypted provided as a string
	 * @return a string containing the encrypted password
	 * @throws NoSuchAlgorithmException
	 */
	private static String encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		StringBuffer sb = new StringBuffer();
		byte[] saltBytes = User.visualizeAccountAge(123456789).getBytes();
		md.update(saltBytes);
		byte[] passBytes = password.trim().getBytes();
		md.update(passBytes);
		byte[] bytes = md.digest();
		for(byte b : bytes) sb.append(b);
		return sb.toString().substring(2, sb.length() - 2);
	}

	public String getEmail() {return this.email;}

	public void setEmail(String email) {this.email = email;}

	public Timestamp getCreationDate() {return this.creationDate;}

	public void setCreationDate(Timestamp creationDate) {this.creationDate = creationDate == null ? new Timestamp(System.currentTimeMillis()) : creationDate;}

	public long getAccountAge() {return this.accountAge;}

	public void setAccountAge() {this.accountAge = this.creationDate == null ? 0l : System.currentTimeMillis() - this.creationDate.getTime();}

	/**
	 * Transforms the account age provided as parameter
	 * to a readable time string, and returns it.
	 * @param accountAge current account age in milliseconds
	 * @return a string containing the converted time
	 */
	public static String visualizeAccountAge(long accountAge) {
		StringBuffer sb = new StringBuffer();
		long difference = accountAge;
		String y = null, d = null, h = "00 h ", m = ": 00 m ", s = ": 00 s", ms = ". 000 ms";
		while(difference != 0) {
			if(difference < 1000) {
				ms = String.format(". %03dms", difference);
				difference = 0;
			} else if(difference < 60000) {
				s = String.format(": %02ds ", difference/1000);
				difference = difference%1000;
			} else if(difference < 3600000l) {
				m = String.format(": %02dm ", difference/60000);
				difference = difference%60000;
			} else if(difference < 86400000l) {
				h = String.format("%02dh ", difference/3600000);
				difference = difference%3600000;
			} else if(difference < 31536000000l) {
				d = String.format("%d day(s)", difference/86400000l);
				difference = difference%86400000l;
			} else {
				y = String.format("%d year(s), ", difference/31536000000l);
				difference = difference%31536000000l;
			}
		}
		if(d != null) {
			if(y != null) sb.append(y).append(d);
			else sb.append(d);
		} else sb.append(h).append(m).append(s).append(ms);
		return sb.toString();
	}

	@Override
	public String createString() {
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append(super.createString());
		sb.append(", password=").append(this.password);
		sb.append(", email=").append(this.email);
		sb.append(", creation_date=").append(this.creationDate);
		sb.append(", account_age=").append(User.visualizeAccountAge(this.accountAge));
		return sb.toString();
	}

	@Override
	public String toString() {return new Gson().toJson(this);}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (accountAge ^ (accountAge >>> 32));
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountAge != other.accountAge)
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}
