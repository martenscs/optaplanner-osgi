package net.martenscs.optaplanner.vehicle.routing;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		VehicleRoutingPerformanceTest cheapTimePerformanceTest = new VehicleRoutingPerformanceTest();
		cheapTimePerformanceTest.setBundleContext(bundleContext);
		cheapTimePerformanceTest.setUp();
		cheapTimePerformanceTest.createSolutionDao();
		cheapTimePerformanceTest.createSolverConfigResource();

		cheapTimePerformanceTest.solveModel_cvrp_32customers();;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
