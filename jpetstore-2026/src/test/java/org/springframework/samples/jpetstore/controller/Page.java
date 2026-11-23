package org.springframework.samples.jpetstore.controller;

import java.util.Map;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public class Page {

	private final String view;

	private final String redirect;

	private final Map<String, Object> attributes;

	public Page(String forwardedUrl, String redirect, Map<String, Object> attributes) {
		this.view = viewName(forwardedUrl);
		this.redirect = redirect;
		this.attributes = attributes;
	}

	public String view() {
		return view;
	}

	public String redirect() {
		return redirect;
	}

	public Object value(String path) {
		return read(attributes, path);
	}

	public Errors errors(String formName) {
		return (Errors) attributes.get(BindingResult.MODEL_KEY_PREFIX + formName);
	}

	static Object read(Map<String, Object> roots, String path) {
		int dot = path.indexOf('.');
		if (dot < 0) {
			return roots.get(path);
		}
		Object root = roots.get(path.substring(0, dot));
		if (root == null) {
			return null;
		}
		return new BeanWrapperImpl(root).getPropertyValue(path.substring(dot + 1));
	}

	private static String viewName(String forwardedUrl) {
		if (forwardedUrl == null) {
			return null;
		}
		String name = forwardedUrl.substring(forwardedUrl.lastIndexOf('/') + 1);
		return name.endsWith(".jsp") ? name.substring(0, name.length() - 4) : name;
	}
}
