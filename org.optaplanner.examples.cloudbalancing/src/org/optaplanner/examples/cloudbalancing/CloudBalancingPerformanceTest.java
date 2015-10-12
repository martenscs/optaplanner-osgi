/*
 * Copyright 2012 JBoss Inc
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

package org.optaplanner.examples.cloudbalancing;

import java.net.URL;

import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.cloudbalancing.app.CloudBalancingApp;
import org.optaplanner.examples.cloudbalancing.persistence.CloudBalancingDao;
import org.optaplanner.osgi.common.app.SolverPerformanceTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class CloudBalancingPerformanceTest extends SolverPerformanceTest {

	@Override
	protected String createSolverConfigResource() {
		return CloudBalancingApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new CloudBalancingDao();
	}

	// ************************************************************************
	// Tests
	// ************************************************************************

	public void solveModel_200computers_600processes() {
		URL url = Activator
				.getContext()
				.getBundle()
				.getEntry(
						"data/cloudbalancing/unsolved/200computers-600processes.xml");
		runSpeedTest(url, "0hard/-218850soft");
	}

	public void solveModel_200computers_600processesFastAssert() {
		URL url = Activator
				.getContext()
				.getBundle()
				.getEntry(
						"data/cloudbalancing/unsolved/200computers-600processes.xml");
		runSpeedTest(url, "0hard/-223260soft", EnvironmentMode.FAST_ASSERT);
	}

	@Override
	protected void solveAll() {
		solveModel_200computers_600processes();
		solveModel_200computers_600processesFastAssert();

	}

}
