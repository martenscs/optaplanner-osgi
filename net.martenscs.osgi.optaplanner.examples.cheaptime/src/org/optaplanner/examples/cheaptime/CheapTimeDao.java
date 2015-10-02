package org.optaplanner.examples.cheaptime;

import org.optaplanner.examples.cheaptime.domain.CheapTimeSolution;
import org.optaplanner.osgi.common.persistence.XStreamSolutionDao;

public class CheapTimeDao extends XStreamSolutionDao {

    public CheapTimeDao() {
        super("cheaptime", CheapTimeSolution.class);
    }

}
