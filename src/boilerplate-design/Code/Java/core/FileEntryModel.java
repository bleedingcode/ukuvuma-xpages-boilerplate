package core;

import java.io.Serializable;

public class FileEntryModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public FileEntryModel() {
		_InitModel();
	}

	// VARIABLES
	public String id;
	public String name;
	public String imageURL;
	public int size;

	// PRIVATE METHODS
	private void _InitModel() {
		id = "";
		name = "";
		imageURL = "";
		size = 0;
	}

	// GETTERS AND SETTERS
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
