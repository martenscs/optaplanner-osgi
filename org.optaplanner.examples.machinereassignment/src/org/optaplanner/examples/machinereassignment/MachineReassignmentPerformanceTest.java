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

package org.optaplanner.examples.machinereassignment;

import java.net.URL;

import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.machinereassignment.app.MachineReassignmentApp;
import org.optaplanner.examples.machinereassignment.persistence.MachineReassignmentDao;
import org.optaplanner.osgi.common.app.SolverPerformanceTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class MachineReassignmentPerformanceTest extends SolverPerformanceTest {

	@Override
	protected String createSolverConfigResource() {
		return MachineReassignmentApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new MachineReassignmentDao();
	}

	// ************************************************************************
	// Tests
	// ************************************************************************

	public void solveModel_a2_1() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/machinereassignment/unsolved/model_a2_1.xml");
		runSpeedTest(unsolvedDataFile, "0hard/-117351236soft");
	}

	public void solveModel_a2_1FastAssert() {
		URL unsolvedDataFile = Activator.getContext().getBundle()
				.getEntry("data/machinereassignment/unsolved/model_a2_1.xml");
		runSpeedTest(unsolvedDataFile, "0hard/-272621414soft",
				EnvironmentMode.FAST_ASSERT);
	}

	@Override
	protected void solveAll() {
		this.solveModel_a2_1();
		this.solveModel_a2_1FastAssert();

	}

}
