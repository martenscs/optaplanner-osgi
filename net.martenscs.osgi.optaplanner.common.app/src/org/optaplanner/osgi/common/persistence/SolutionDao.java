package org.optaplanner.osgi.common.persistence;

import java.io.File;
import java.net.URL;

import org.optaplanner.core.api.domain.solution.Solution;

/**
 * Data Access Object for the examples.
 */
public interface SolutionDao {

	String getDirName();

	File getDataDir();

	String getFileExtension();

	@SuppressWarnings("rawtypes")
	Solution readSolution(URL inputSolutionFile);

	@SuppressWarnings("rawtypes")
	void writeSolution(Solution solution, File outputSolutionFile);

}