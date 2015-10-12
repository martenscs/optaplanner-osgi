package org.optaplanner.osgi.common.persistence;

import java.io.File;
import java.net.URL;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;
import org.osgi.framework.BundleContext;

@SuppressWarnings("rawtypes")
public abstract class XStreamSolutionDao extends AbstractSolutionDao {

	protected XStreamSolutionFileIO xStreamSolutionFileIO;

	public XStreamSolutionDao(BundleContext bundleContext, String dirName,
			Class... xStreamAnnotations) {
		super(dirName);
		xStreamSolutionFileIO = new XStreamSolutionFileIO(bundleContext,
				xStreamAnnotations);
	}

	public String getFileExtension() {
		return xStreamSolutionFileIO.getOutputFileExtension();
	}

	public Solution readSolution(URL inputSolutionFile) {
		Solution solution = xStreamSolutionFileIO.read(inputSolutionFile);
		logger.info("Opened: {}", inputSolutionFile);
		return solution;
	}

	public void writeSolution(Solution solution, File outputSolutionFile) {
		xStreamSolutionFileIO.write(solution, outputSolutionFile);
		logger.info("Saved: {}", outputSolutionFile);
	}

}
