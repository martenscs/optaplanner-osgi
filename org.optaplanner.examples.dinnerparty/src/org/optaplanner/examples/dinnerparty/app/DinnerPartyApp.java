/*
 * Copyright 2010 JBoss Inc
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

package org.optaplanner.examples.dinnerparty.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.examples.dinnerparty.persistence.DinnerPartyDao;
import org.optaplanner.osgi.common.app.CommonApp;
import org.optaplanner.osgi.common.app.OSGiSolverFactory;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class DinnerPartyApp extends CommonApp {

	public static final String SOLVER_CONFIG = "solver/dinnerPartySolverConfig.xml";

	public static void main(String[] args) {
		prepareSwingEnvironment();
		new DinnerPartyApp().init();
	}

	public DinnerPartyApp() {
		super("Dinner party",
				"Decide the seating at a big fancy dinner party with round tables.\n"
						+ "Assign guests to seats at tables.", "");
	}

	@Override
	protected Solver createSolver() {
		OSGiSolverFactory solverFactory = OSGiSolverFactory
				.createFromXmlResource(SOLVER_CONFIG);
		return solverFactory.buildSolver();
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new DinnerPartyDao();
	}

}
