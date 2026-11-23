package org.springframework.samples.jpetstore.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account implements Serializable {

	private String username;

	private String password;

	private String email;

	private String firstName;

	private String lastName;

	private String status;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String zip;

	private String country;

	private String phone;

	private String favouriteCategoryId;

	private String languagePreference;

	private boolean listOption;

	private boolean bannerOption;

	private String bannerName;

	public int getListOptionAsInt() { return listOption ? 1 : 0; }

	public int getBannerOptionAsInt() { return bannerOption ? 1 : 0; }
}
