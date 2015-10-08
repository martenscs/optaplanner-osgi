package org.optaplanner.examples.cheaptime;

import org.optaplanner.examples.cheaptime.domain.CheapTimeSolution;
import org.optaplanner.osgi.common.persistence.XStreamSolutionDao;
import org.osgi.framework.BundleContext;

public class CheapTimeDao extends XStreamSolutionDao {

	public CheapTimeDao(BundleContext bundleContext) {
		super(bundleContext, "cheaptime", CheapTimeSolution.class);
	}

}
