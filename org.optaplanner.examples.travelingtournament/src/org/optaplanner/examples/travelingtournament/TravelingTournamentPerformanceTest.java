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

package org.optaplanner.examples.travelingtournament;

import java.net.URL;

import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.travelingtournament.app.TravelingTournamentApp;
import org.optaplanner.examples.travelingtournament.persistence.TravelingTournamentDao;
import org.optaplanner.osgi.common.app.SolverPerformanceTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class TravelingTournamentPerformanceTest extends SolverPerformanceTest {

	@Override
	protected String createSolverConfigResource() {
		return TravelingTournamentApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new TravelingTournamentDao();
	}

	// ************************************************************************
	// Tests
	// ************************************************************************

	public void solveComp01_initialized() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/travelingtournament/unsolved/1-nl10.xml");
		runSpeedTest(unsolvedDataFile, "0hard/-75968soft");
	}

	public void solveTestdata01_initializedFastAssert() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/travelingtournament/unsolved/1-nl10.xml");
		runSpeedTest(unsolvedDataFile, "0hard/-77619soft",
				EnvironmentMode.FAST_ASSERT);
	}

	@Override
	protected void solveAll() {
		this.solveComp01_initialized();
		this.solveTestdata01_initializedFastAssert();

	}

}
