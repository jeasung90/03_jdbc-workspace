package com.kh.model.vo;

public class PhoneBook {

	private int pbnum;
	private String name;
	private int age;
	private String address;
	private String phone;
	
	public PhoneBook() {}
	
	public PhoneBook(int pbnum,String name, int age, String address, String phone) {
		super();
		this.pbnum = pbnum;
		this.name = name;
		this.age = age;
		this.address = address;
		this.phone = phone;
	}

	
	public PhoneBook(String name, int age, String address, String phone) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
		this.phone = phone;
	}

	public int getPbnum() {
		return pbnum;
	}

	public void setPbnum(int pbnum) {
		this.pbnum = pbnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "PhoneBook [pbnum=" + pbnum + ", name=" + name + ", age=" + age + ", address=" + address + ", phone="
				+ phone + "]";
	}

	
	
}
