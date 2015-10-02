package org.optaplanner.examples.cheaptime;

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
		 CheapTimePerformanceTest cheapTimePerformanceTest = new
		 CheapTimePerformanceTest();
		 cheapTimePerformanceTest.setBundleContext(bundleContext);
		 cheapTimePerformanceTest.setUp();
		 cheapTimePerformanceTest.createSolutionDao();
		 cheapTimePerformanceTest.createSolverConfigResource();
		
		 cheapTimePerformanceTest.solveInstance00();
		 cheapTimePerformanceTest.solveInstance00FastAssert();
	
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
