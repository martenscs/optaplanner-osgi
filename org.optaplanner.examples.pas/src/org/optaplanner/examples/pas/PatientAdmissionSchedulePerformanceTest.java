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

package org.optaplanner.examples.pas;

import java.net.URL;

import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.pas.app.PatientAdmissionScheduleApp;
import org.optaplanner.examples.pas.persistence.PatientAdmissionScheduleDao;
import org.optaplanner.osgi.common.app.SolverPerformanceTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class PatientAdmissionSchedulePerformanceTest extends
		SolverPerformanceTest {

	@Override
	protected String createSolverConfigResource() {
		return PatientAdmissionScheduleApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new PatientAdmissionScheduleDao();
	}

	// ************************************************************************
	// Tests
	// ************************************************************************

	public void solveTestdata01() {

		URL url = Activator.getContext().getBundle()
				.getEntry("data/pas/unsolved/testdata01.xml");
		runSpeedTest(url, "0hard/0medium/-7458soft");
		// TODO Adding overconstrained functionality reduced Solver efficiency,
		// so this ran too long (over 1 minute):
		// runSpeedTest(unsolvedDataFile, "0hard/0medium/-7172soft");
	}

	public void solveTestdata01FastAssert() {
		URL url = Activator.getContext().getBundle()
				.getEntry("data/pas/unsolved/testdata01.xml");
		runSpeedTest(url, "0hard/0medium/-7408soft",
				EnvironmentMode.FAST_ASSERT);
		// TODO Adding overconstrained functionality reduced Solver efficiency,
		// so this ran too long (over 1 minute):
		// runSpeedTest(unsolvedDataFile, "0hard/0medium/-7192soft",
		// EnvironmentMode.FAST_ASSERT);
	}

	@Override
	protected void solveAll() {
		this.solveTestdata01();
		this.solveTestdata01FastAssert();

	}

}
