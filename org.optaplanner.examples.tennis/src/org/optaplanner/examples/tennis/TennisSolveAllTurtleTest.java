/*
 * Copyright 2013 JBoss Inc
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

package org.optaplanner.examples.tennis;

import java.net.URL;
import java.util.Collection;

import org.optaplanner.examples.tennis.app.TennisApp;
import org.optaplanner.examples.tennis.persistence.TennisDao;
import org.optaplanner.osgi.common.app.UnsolvedDirSolveAllTurtleTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class TennisSolveAllTurtleTest extends UnsolvedDirSolveAllTurtleTest {

	public static Collection<Object[]> getSolutionFilesAsParameters() {
		return getUnsolvedDirFilesAsParameters(new TennisDao());
	}

	public TennisSolveAllTurtleTest(URL unsolvedDataFile) {
		super(unsolvedDataFile);
	}

	@Override
	protected String createSolverConfigResource() {
		return TennisApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new TennisDao();
	}

}
