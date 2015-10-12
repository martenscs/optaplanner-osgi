/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.persistence.xstream.impl.domain.solution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import org.apache.aries.util.AriesFrameworkUtil;
import org.apache.commons.io.IOUtils;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.osgi.common.app.DelegationClassloader;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.osgi.framework.BundleContext;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

public class XStreamSolutionFileIO implements SolutionFileIO {

	public static final String FILE_EXTENSION = "xml";
	private BundleContext context;

	public BundleContext getBundleContext() {
		return context;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.context = bundleContext;
	}

	private XStream xStream;

	public XStreamSolutionFileIO(BundleContext context) {
		xStream = new XStream();
		ClassLoader cl = AriesFrameworkUtil.getClassLoader(context.getBundle());
		// xStream.setClassLoader(cl);

		ClassLoader[] delegates = new ClassLoader[] {
				SolverConfig.class.getClassLoader(), cl,
				XStream.class.getClassLoader() };
		cl = new DelegationClassloader(delegates);
		xStream.setClassLoader(cl);
		xStream.setMode(XStream.ID_REFERENCES);
	}

	@SuppressWarnings("rawtypes")
	public XStreamSolutionFileIO(BundleContext bundleContext,
			Class... xStreamAnnotatedClasses) {
		this(bundleContext);
		xStream.processAnnotations(xStreamAnnotatedClasses);
	}

	public String getInputFileExtension() {
		return FILE_EXTENSION;
	}

	public String getOutputFileExtension() {
		return FILE_EXTENSION;
	}

	@SuppressWarnings("rawtypes")
	public Solution read(URL inputSolutionFile) {
		Solution unsolvedSolution;
		Reader reader = null;
		InputStream in = null;
		try {
			in = inputSolutionFile.openStream();

			// xStream.fromXml(InputStream) does not use UTF-8
			reader = new InputStreamReader(in, "UTF-8");
			unsolvedSolution = (Solution) xStream.fromXML(reader);
		} catch (XStreamException e) {
			throw new IllegalArgumentException(
					"Problem reading inputSolutionFile (" + inputSolutionFile
							+ ").", e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Problem reading inputSolutionFile (" + inputSolutionFile
							+ ").", e);
		} finally {
			IOUtils.closeQuietly(reader);
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return unsolvedSolution;
	}

	@SuppressWarnings("rawtypes")
	public void write(Solution solution, File outputSolutionFile) {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(
					outputSolutionFile), "UTF-8");
			xStream.toXML(solution, writer);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Problem writing outputSolutionFile (" + outputSolutionFile
							+ ").", e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

}
