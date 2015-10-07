/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.osgi.compiler;

import org.drools.compiler.kie.builder.impl.AbstractKieModule;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.compiler.kproject.models.KieModuleModelImpl;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import static org.drools.core.util.IoUtils.readBytesFromInputStream;

public class OsgiKieModule extends AbstractKieModule {

	private final Bundle bundle;

	private Collection<String> fileNames;

	private final long creationTimestamp = System.currentTimeMillis();

	private OsgiKieModule(ReleaseId releaseId, KieModuleModel kModuleModel,
			Bundle bundle) {
		super(releaseId, kModuleModel);
		this.bundle = bundle;
	}

	@Override
	public byte[] getBytes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAvailable(String pResourceName) {
		return fileNames.contains(pResourceName);
	}

	@Override
	public byte[] getBytes(String pResourceName) {
		// Add a leading '/' char as it has been removed from
		// file Resource name
		pResourceName = "/" + pResourceName;
		URL url = bundle.getResource(pResourceName);
		return url == null ? null : readUrlAsBytes(url);
	}

	@Override
	public Collection<String> getFileNames() {
		if (fileNames != null) {
			return fileNames;
		}
		fileNames = new ArrayList<String>();
		Enumeration<URL> e = bundle.findEntries("", "*", true);
		while (e.hasMoreElements()) {
			URL url = e.nextElement();
			String path = url.getPath();
			if (path.endsWith("/")) {
				continue;
			}
			if (path.startsWith("/target")) {
				continue;
			}
			fileNames.add(path.substring(1, path.length()));
		}
		return fileNames;
	}

	@Override
	public File getFile() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	public static OsgiKieModule create(URL url) {
		KieModuleModel kieProject = KieModuleModelImpl.fromXML(url);
		Bundle bundle = getBundle(url.toString());

		if (bundle != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("version=");

			String pomProperties = bundle.getVersion().toString();
			sb.append(pomProperties + "\n");

			String groupId = bundle.getSymbolicName();
			sb.append("groupId=");
			sb.append(groupId + "\n");
			String artifactId = bundle.getSymbolicName();
			sb.append("artifactId=");
			sb.append(artifactId + "\n");
			// String version = props.getProperty("version");
			ReleaseId releaseId = ReleaseIdImpl.fromPropertiesString(sb
					.toString());
			return new OsgiKieModule(releaseId, kieProject, bundle);
		} else {
			throw new RuntimeException(
					"Bundle does not exist or no retrieved for this URL :  "
							+ url);
		}

	}

	public static OsgiKieModule create(URL url, ReleaseId releaseId,
			KieModuleModel kieProject) {
		Bundle bundle = getBundle(url.toString());
		if (bundle != null) {
			return new OsgiKieModule(releaseId, kieProject, bundle);
		} else {
			throw new RuntimeException(
					"Bundle does not exist or no retrieved for this URL :  "
							+ url);
		}
	}

	private static String getPomProperties(Bundle bundle) {
		Enumeration<URL> e = bundle.findEntries("META-INF/maven",
				"pom.properties", true);
		if (e == null || !e.hasMoreElements()) {
			throw new RuntimeException(
					"Cannot find pom.properties file in bundle " + bundle);
		}
		return readUrlAsString(e.nextElement());
	}

	private static Bundle getBundle(String url) {
		String urlString = url.toString();
		String id = null;
		if (url.startsWith("bundleentry://"))
			id = urlString.substring("bundleentry://".length(),
					urlString.indexOf('.'));
		else if (url.startsWith("bundleresource://"))
			id = urlString.substring("bundleresource://".length(),
					urlString.indexOf('.'));
		else
			id = urlString.substring("bundle://".length(),
					urlString.indexOf('.'));
		long bundleId = Long.parseLong(id);
		return FrameworkUtil.getBundle(OsgiKieModule.class).getBundleContext()
				.getBundle(bundleId);
	}

	private static String readUrlAsString(URL url) {
		return new String(readUrlAsBytes(url));
	}

	private static byte[] readUrlAsBytes(URL url) {
		InputStream is = null;
		try {
			is = url.openStream();
			return readBytesFromInputStream(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}
}