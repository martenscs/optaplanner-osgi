package org.optaplanner.osgi.common.app;

import java.net.URL;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.optaplanner.core.impl.score.director.InnerScoreDirectorFactory;
import org.optaplanner.osgi.common.persistence.SolutionDao;
import org.osgi.framework.BundleContext;

@SuppressWarnings("rawtypes")
public abstract class SolverPerformanceTest extends LoggingMain {

	protected SolutionDao solutionDao;
	private BundleContext bundleContext;

	public void setUp() {

		solutionDao = createSolutionDao();
		OSGiSolverFactory.setContext(bundleContext);
	}

	protected abstract String createSolverConfigResource();

	protected abstract SolutionDao createSolutionDao();

	protected void runSpeedTest(URL unsolvedDataFile,
			String bestScoreLimitString) {
		runSpeedTest(unsolvedDataFile, bestScoreLimitString,
				EnvironmentMode.REPRODUCIBLE);
	}

	protected void runSpeedTest(URL unsolvedDataFile,
			String bestScoreLimitString, EnvironmentMode environmentMode) {
		OSGiSolverFactory solverFactory = buildSolverFactory(
				bestScoreLimitString, environmentMode);
		Solver solver = solve(solverFactory, unsolvedDataFile);
		assertBestSolution(solver, bestScoreLimitString);
	}

	protected OSGiSolverFactory buildSolverFactory(String bestScoreLimitString,
			EnvironmentMode environmentMode) {
		OSGiSolverFactory solverFactory = OSGiSolverFactory
				.createFromXmlResource(createSolverConfigResource());
		solverFactory.getSolverConfig().setEnvironmentMode(environmentMode);
		TerminationConfig terminationConfig = new TerminationConfig();
		terminationConfig.setBestScoreLimit(bestScoreLimitString);
		solverFactory.getSolverConfig().setTerminationConfig(terminationConfig);
		return solverFactory;
	}

	private Solver solve(OSGiSolverFactory solverFactory, URL unsolvedDataFile) {
		Solution planningProblem = solutionDao.readSolution(unsolvedDataFile);
		Solver solver = solverFactory.buildSolver();
		solver.solve(planningProblem);
		return solver;
	}

	private void assertBestSolution(Solver solver, String bestScoreLimitString) {
		Solution bestSolution = solver.getBestSolution();
		// assertNotNull(bestSolution);
		Score bestScore = bestSolution.getScore();
		InnerScoreDirectorFactory scoreDirectorFactory = (InnerScoreDirectorFactory) solver
				.getScoreDirectorFactory();
		Score bestScoreLimit = scoreDirectorFactory.getScoreDefinition()
				.parseScore(bestScoreLimitString);
		logger.info("The bestScore (" + bestScore
				+ ") must be at least bestScoreLimit (" + bestScoreLimit);
		// assertTrue(
		// "The bestScore (" + bestScore
		// + ") must be at least bestScoreLimit ("
		// + bestScoreLimit + ").",
		// bestScore.compareTo(bestScoreLimit) >= 0);
	}

	public BundleContext getBundleContext() {
		return bundleContext;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

}
