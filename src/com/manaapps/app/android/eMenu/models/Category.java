package com.manaapps.app.android.eMenu.models;

public class Category {
	private String _id;
	private String name;
	private String parentId;
	private String description;
	private int displayOrder;
	private int typeId;
	private String androidImage;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getAndroidImage() {
		return androidImage;
	}

	public void setAndroidImage(String androidImage) {
		this.androidImage = androidImage;
	}

}
