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

package org.optaplanner.examples.tsp.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.examples.tsp.persistence.TspDao;
import org.optaplanner.osgi.common.app.CommonApp;
import org.optaplanner.osgi.common.app.OSGiSolverFactory;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class TspApp extends CommonApp {

	public static final String SOLVER_CONFIG = "solver/tspSolverConfig.xml";

	public static void main(String[] args) {
		prepareSwingEnvironment();
		new TspApp().init();
	}

	public TspApp() {
		super(
				"Traveling salesman",
				"Official competition name: TSP - Traveling salesman problem\n\n"
						+ "Determine the order in which to visit all cities.\n\n"
						+ "Find the shortest route to visit all cities.", "");
	}

	@Override
	protected Solver createSolver() {
		OSGiSolverFactory solverFactory = OSGiSolverFactory
				.createFromXmlResource(SOLVER_CONFIG);
		return solverFactory.buildSolver();
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new TspDao();
	}

}
