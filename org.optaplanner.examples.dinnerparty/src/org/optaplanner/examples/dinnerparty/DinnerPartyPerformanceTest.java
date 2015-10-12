/*
 * Copyright 2014 JBoss Inc
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

package org.optaplanner.examples.dinnerparty;

import java.net.URL;

import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.dinnerparty.app.DinnerPartyApp;
import org.optaplanner.examples.dinnerparty.persistence.DinnerPartyDao;
import org.optaplanner.osgi.common.app.SolverPerformanceTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class DinnerPartyPerformanceTest extends SolverPerformanceTest {

	@Override
	protected String createSolverConfigResource() {
		return DinnerPartyApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new DinnerPartyDao();
	}

	// ************************************************************************
	// Tests
	// ************************************************************************

	public void solveModel_wedding01() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/dinnerparty/unsolved/wedding01.xml");
		runSpeedTest(unsolvedDataFile, "-90");
	}

	public void solveModel_wedding01FastAssert() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/dinnerparty/unsolved/wedding01.xml");
		runSpeedTest(unsolvedDataFile, "-390", EnvironmentMode.FAST_ASSERT);
	}

	@Override
	protected void solveAll() {
		this.solveModel_wedding01();
		this.solveModel_wedding01FastAssert();

	}

}
