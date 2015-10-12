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

package org.optaplanner.examples.pas.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.examples.pas.persistence.PatientAdmissionScheduleDao;
import org.optaplanner.examples.pas.persistence.PatientAdmissionScheduleExporter;
import org.optaplanner.osgi.common.app.CommonApp;
import org.optaplanner.osgi.common.app.OSGiSolverFactory;
import org.optaplanner.osgi.common.persistence.AbstractSolutionExporter;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class PatientAdmissionScheduleApp extends CommonApp {

	public static final String SOLVER_CONFIG = "solver/patientAdmissionScheduleSolverConfig.xml";

	public static void main(String[] args) {
		prepareSwingEnvironment();
		new PatientAdmissionScheduleApp().init();
	}

	public PatientAdmissionScheduleApp() {
		super("Hospital bed planning",
				"Official competition name: PAS - Patient admission scheduling\n\n"
						+ "Assign patients to beds.", "");
	}

	@Override
	protected Solver createSolver() {
		OSGiSolverFactory solverFactory = OSGiSolverFactory
				.createFromXmlResource(SOLVER_CONFIG);
		return solverFactory.buildSolver();
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new PatientAdmissionScheduleDao();
	}

	@Override
	protected AbstractSolutionExporter createSolutionExporter() {
		return new PatientAdmissionScheduleExporter();
	}

}
