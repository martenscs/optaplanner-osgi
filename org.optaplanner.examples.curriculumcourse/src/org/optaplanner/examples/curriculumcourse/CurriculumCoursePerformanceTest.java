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

package org.optaplanner.examples.curriculumcourse;

import java.net.URL;

import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.examples.curriculumcourse.app.CurriculumCourseApp;
import org.optaplanner.examples.curriculumcourse.persistence.CurriculumCourseDao;
import org.optaplanner.osgi.common.app.SolverPerformanceTest;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class CurriculumCoursePerformanceTest extends SolverPerformanceTest {

	@Override
	protected String createSolverConfigResource() {
		return CurriculumCourseApp.SOLVER_CONFIG;
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new CurriculumCourseDao();
	}

	// ************************************************************************
	// Tests
	// ************************************************************************

	public void solveComp01_initialized() {
		URL url = Activator
				.getContext()
				.getBundle()
				.getEntry(
						"data/curriculumcourse/unsolved/comp01_initialized.xml");
		runSpeedTest(url, "0hard/-99soft");
	}

	public void solveComp01_initializedFastAssert() {
		URL url = Activator
				.getContext()
				.getBundle()
				.getEntry(
						"data/curriculumcourse/unsolved/comp01_initialized.xml");
		runSpeedTest(url, "0hard/-140soft", EnvironmentMode.FAST_ASSERT);
	}

	@Override
	protected void solveAll() {
		this.solveComp01_initialized();
		solveComp01_initializedFastAssert();

	}

}
