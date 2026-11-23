package org.springframework.samples.jpetstore.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.jpetstore.TestApplication;

public class ShopClient {

	private MockHttpSession session;

	public static ShopClient signedIn(String username, String password) throws Exception {
		ShopClient client = new ShopClient();
		client.post("/shop/signon.do", "username", username, "password", password);
		return client;
	}

	public Page get(String path, String... parameters) throws Exception {
		return perform("GET", path, parameters);
	}

	public Page post(String path, String... parameters) throws Exception {
		return perform("POST", path, parameters);
	}

	public Object sessionValue(String path) {
		return Page.read(sessionAttributes(), path);
	}

	private Page perform(String method, String path, String[] parameters) throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest(TestApplication.servletContext(), method,
				TestApplication.CONTEXT_PATH + path);
		request.setContextPath(TestApplication.CONTEXT_PATH);
		request.setServletPath(path);
		StringBuilder query = new StringBuilder();
		for (int i = 0; i < parameters.length; i += 2) {
			request.addParameter(parameters[i], parameters[i + 1]);
			query.append(query.length() == 0 ? "" : "&").append(parameters[i]).append('=').append(parameters[i + 1]);
		}
		if ("GET".equals(method) && query.length() > 0) {
			request.setQueryString(query.toString());
		}
		request.setSession(currentSession());
		MockHttpServletResponse response = new MockHttpServletResponse();
		TestApplication.dispatcherServlet().service(request, response);
		return new Page(response.getForwardedUrl(), response.getRedirectedUrl(), requestAttributes(request));
	}

	private MockHttpSession currentSession() {
		if (session == null || session.isInvalid()) {
			session = new MockHttpSession(TestApplication.servletContext());
		}
		return session;
	}

	private Map<String, Object> sessionAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		if (session != null && !session.isInvalid()) {
			for (Enumeration<?> names = session.getAttributeNames(); names.hasMoreElements();) {
				String name = (String) names.nextElement();
				attributes.put(name, session.getAttribute(name));
			}
		}
		return attributes;
	}

	private static Map<String, Object> requestAttributes(MockHttpServletRequest request) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		for (Enumeration<?> names = request.getAttributeNames(); names.hasMoreElements();) {
			String name = (String) names.nextElement();
			attributes.put(name, request.getAttribute(name));
		}
		return attributes;
	}
}
