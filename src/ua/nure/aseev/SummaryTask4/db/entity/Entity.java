package ua.nure.aseev.SummaryTask4.db.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Root of all entities which have identifier field.
 * 
 * 
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 1248;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long entityId) {
		this.id = entityId;
	}

	protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

}
