package org.optaplanner.osgi.common.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class DelegationClassloader extends ClassLoader {
	private final ClassLoader[] delegates;

	public DelegationClassloader(ClassLoader... classLoaders) {
		if (classLoaders == null)
			throw new NullPointerException();

		delegates = classLoaders;
	}

	@Override
	public URL getResource(String name) {
		for (ClassLoader cl : delegates) {
			URL res = cl.getResource(name);
			if (res != null)
				return res;
		}
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		for (ClassLoader cl : delegates) {
			InputStream is = cl.getResourceAsStream(name);
			if (is != null)
				return is;
		}
		return null;
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		List<URL> urls = new ArrayList<URL>();
		for (ClassLoader cl : delegates) {
			urls.addAll(Collections.list(cl.getResources(name)));
		}
		return Collections.enumeration(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		ClassNotFoundException lastEx = null;
		for (ClassLoader cl : delegates) {
			try {
				return cl.loadClass(name);
			} catch (ClassNotFoundException e) {
				lastEx = e;
			}
		}
		throw lastEx;
	}
}
