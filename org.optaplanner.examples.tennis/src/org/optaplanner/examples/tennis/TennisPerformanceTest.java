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

import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.tennis.app.TennisApp;
import org.optaplanner.examples.tennis.persistence.TennisDao;
import org.optaplanner.osgi.common.app.SolverPerformanceTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class TennisPerformanceTest extends SolverPerformanceTest {

	@Override
	protected String createSolverConfigResource() {
		return TennisApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new TennisDao();
	}

	// ************************************************************************
	// Tests
	// ************************************************************************

	public void solveModel_munich_7teams() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/tennis/unsolved/munich-7teams.xml");
		runSpeedTest(unsolvedDataFile, "0hard/-742medium/-288soft");
	}

	public void solveModel_munich_7teamsFastAssert() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/tennis/unsolved/munich-7teams.xml");
		runSpeedTest(unsolvedDataFile, "0hard/-742medium/-294soft",
				EnvironmentMode.FAST_ASSERT);
	}

	@Override
	protected void solveAll() {
		this.solveModel_munich_7teams();
		this.solveModel_munich_7teamsFastAssert();

	}

}
