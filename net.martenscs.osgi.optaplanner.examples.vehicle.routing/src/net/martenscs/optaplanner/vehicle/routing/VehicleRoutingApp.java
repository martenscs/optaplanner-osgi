package net.martenscs.optaplanner.vehicle.routing;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.examples.vehiclerouting.persistence.VehicleRoutingDao;
import org.optaplanner.osgi.common.app.CommonApp;
import org.optaplanner.osgi.common.app.OSGiSolverFactory;
import org.optaplanner.osgi.common.persistence.SolutionDao;

public class VehicleRoutingApp extends CommonApp {

	protected VehicleRoutingApp(String name, String description,
			String iconResource) {
		super(name, description, iconResource);
	}

	public static final String SOLVER_CONFIG = "examples/vehiclerouting/solver/vehicleRoutingSolverConfig.xml";

	@Override
	protected Solver createSolver() {
		OSGiSolverFactory solverFactory = OSGiSolverFactory
				.createFromXmlResource(SOLVER_CONFIG);
		return solverFactory.buildSolver();
	}

	@Override
	protected SolutionDao createSolutionDao() {
		return new VehicleRoutingDao();
	}

}
