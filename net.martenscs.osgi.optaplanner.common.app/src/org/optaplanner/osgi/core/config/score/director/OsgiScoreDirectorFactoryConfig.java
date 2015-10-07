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

package org.optaplanner.osgi.core.config.score.director;

import java.io.File;
import java.net.URL;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.optaplanner.core.config.score.director.ScoreDirectorFactoryConfig;
import org.optaplanner.core.config.score.trend.InitializingScoreTrendLevel;
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.core.config.util.ConfigUtils;
import org.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import org.optaplanner.core.impl.score.definition.ScoreDefinition;
import org.optaplanner.core.impl.score.director.AbstractScoreDirectorFactory;
import org.optaplanner.core.impl.score.director.InnerScoreDirectorFactory;
import org.optaplanner.core.impl.score.director.drools.DroolsScoreDirectorFactory;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import org.optaplanner.core.impl.score.director.easy.EasyScoreDirectorFactory;
import org.optaplanner.core.impl.score.director.incremental.IncrementalScoreCalculator;
import org.optaplanner.core.impl.score.director.incremental.IncrementalScoreDirectorFactory;
import org.optaplanner.core.impl.score.trend.InitializingScoreTrend;
import org.optaplanner.osgi.common.app.OSGiSolverFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OsgiScoreDirectorFactoryConfig extends ScoreDirectorFactoryConfig {

	private static final Logger logger = LoggerFactory
			.getLogger(OsgiScoreDirectorFactoryConfig.class);

	private BundleContext bundleContext;

	public BundleContext getBundleContext() {
		return bundleContext;
	}

	public void setBundleContext(BundleContext context) {
		this.bundleContext = context;
	}

	// ************************************************************************
	// Builder methods
	// ************************************************************************

	protected InnerScoreDirectorFactory buildScoreDirectorFactory(
			EnvironmentMode environmentMode,
			SolutionDescriptor solutionDescriptor,
			ScoreDefinition scoreDefinition) {

		AbstractScoreDirectorFactory scoreDirectorFactory;
		// TODO this should fail-fast if multiple scoreDirectorFactory's are
		// configured or if none are configured
		scoreDirectorFactory = buildEasyScoreDirectorFactory();
		if (scoreDirectorFactory == null) {
			scoreDirectorFactory = buildIncrementalScoreDirectorFactory();
		}
		if (scoreDirectorFactory == null) {
			scoreDirectorFactory = buildDroolsScoreDirectorFactory();
		}
		scoreDirectorFactory.setSolutionDescriptor(solutionDescriptor);
		scoreDirectorFactory.setScoreDefinition(scoreDefinition);
		if (assertionScoreDirectorFactory != null) {
			// if (assertionScoreDirectorFactory
			// .getAssertionScoreDirectorFactory() != null) {
			// throw new IllegalArgumentException(
			// "A assertionScoreDirectorFactory ("
			// + assertionScoreDirectorFactory
			// + ") cannot have a non-null assertionScoreDirectorFactory ("
			// + assertionScoreDirectorFactory
			// .getAssertionScoreDirectorFactory()
			// + ").");
			// }
			// if (assertionScoreDirectorFactory.getScoreDefinitionClass() !=
			// null
			// || assertionScoreDirectorFactory.getScoreDefinitionType() !=
			// null) {
			// throw new IllegalArgumentException(
			// "A assertionScoreDirectorFactory ("
			// + assertionScoreDirectorFactory
			// + ") must reuse the scoreDefinition of its parent."
			// + " It cannot have a non-null scoreDefinition* property.");
			// }
			// if (environmentMode.compareTo(EnvironmentMode.FAST_ASSERT) > 0) {
			// throw new IllegalArgumentException(
			// "A non-null assertionScoreDirectorFactory ("
			// + assertionScoreDirectorFactory
			// + ") requires an environmentMode ("
			// + environmentMode + ") of "
			// + EnvironmentMode.FAST_ASSERT + " or lower.");
			// }
			// scoreDirectorFactory
			// .setAssertionScoreDirectorFactory(assertionScoreDirectorFactory
			// .buildScoreDirectorFactory(
			// EnvironmentMode.PRODUCTION,
			// solutionDescriptor, scoreDefinition));
		}
		scoreDirectorFactory
				.setInitializingScoreTrend(InitializingScoreTrend
						.parseTrend(
								initializingScoreTrend == null ? InitializingScoreTrendLevel.ANY
										.name() : initializingScoreTrend,
								scoreDefinition.getLevelsSize()));
		if (environmentMode.isNonIntrusiveFullAsserted()) {
			scoreDirectorFactory.setAssertClonedSolution(true);
		}
		return scoreDirectorFactory;
	}

	private AbstractScoreDirectorFactory buildEasyScoreDirectorFactory() {
		if (easyScoreCalculatorClass != null) {
			EasyScoreCalculator easyScoreCalculator = ConfigUtils.newInstance(
					this, "easyScoreCalculatorClass", easyScoreCalculatorClass);
			return new EasyScoreDirectorFactory(easyScoreCalculator);
		} else {
			return null;
		}
	}

	private AbstractScoreDirectorFactory buildIncrementalScoreDirectorFactory() {
		if (incrementalScoreCalculatorClass != null) {
			if (!IncrementalScoreCalculator.class
					.isAssignableFrom(incrementalScoreCalculatorClass)) {
				throw new IllegalArgumentException(
						"The incrementalScoreCalculatorClass ("
								+ incrementalScoreCalculatorClass
								+ ") does not implement "
								+ IncrementalScoreCalculator.class
										.getSimpleName() + ".");
			}
			return new IncrementalScoreDirectorFactory(
					incrementalScoreCalculatorClass);
		} else {
			return null;
		}
	}

	private AbstractScoreDirectorFactory buildDroolsScoreDirectorFactory() {
		DroolsScoreDirectorFactory scoreDirectorFactory = null;
		try {
			scoreDirectorFactory = new DroolsScoreDirectorFactory(
					buildKieBase());
		} catch (Exception e) {
			System.out.println();
		}
		return scoreDirectorFactory;
	}

	private KieBase buildKieBase() {
		if (kieBase != null) {
			if (!ConfigUtils.isEmptyCollection(scoreDrlList)
					|| !ConfigUtils.isEmptyCollection(scoreDrlFileList)) {
				throw new IllegalArgumentException(
						"If kieBase is not null, the scoreDrlList ("
								+ scoreDrlList + ") and the scoreDrlFileList ("
								+ scoreDrlFileList + ") must be empty.");
			}
			if (kieBaseConfigurationProperties != null) {
				throw new IllegalArgumentException(
						"If kieBase is not null, the kieBaseConfigurationProperties ("
								+ kieBaseConfigurationProperties
								+ ") must be null.");
			}
			return kieBase;
		} else {
			if (ConfigUtils.isEmptyCollection(scoreDrlList)
					&& ConfigUtils.isEmptyCollection(scoreDrlFileList)) {
				throw new IllegalArgumentException("The scoreDrlList ("
						+ scoreDrlList + ") and the scoreDrlFileList ("
						+ scoreDrlFileList + ") cannot both be empty.");
			}

			// This the important line and available since Equinox 3.7
			ClassLoader loader = getBundleContext().getBundle()
					.adapt(BundleWiring.class).getClassLoader();

			KieServices kieServices = KieServices.Factory.get();
			KieContainer kcont = kieServices.newKieClasspathContainer(loader);
		    KieBase kbase = kcont.getKieBase("vehicleRoutingScoreRulesKBase");

			return kbase;
		}
	}

	public void inherit(ScoreDirectorFactoryConfig inheritedConfig) {
		if (scoreDefinitionClass == null && scoreDefinitionType == null
				&& bendableHardLevelsSize == null
				&& bendableSoftLevelsSize == null) {
			scoreDefinitionClass = inheritedConfig.getScoreDefinitionClass();
			scoreDefinitionType = inheritedConfig.getScoreDefinitionType();
			bendableHardLevelsSize = inheritedConfig
					.getBendableHardLevelsSize();
			bendableSoftLevelsSize = inheritedConfig
					.getBendableSoftLevelsSize();
		}
		easyScoreCalculatorClass = ConfigUtils.inheritOverwritableProperty(
				easyScoreCalculatorClass,
				inheritedConfig.getEasyScoreCalculatorClass());
		incrementalScoreCalculatorClass = ConfigUtils
				.inheritOverwritableProperty(incrementalScoreCalculatorClass,
						inheritedConfig.getIncrementalScoreCalculatorClass());
		kieBase = ConfigUtils.inheritOverwritableProperty(kieBase,
				inheritedConfig.getKieBase());
		scoreDrlList = ConfigUtils.inheritMergeableListProperty(scoreDrlList,
				inheritedConfig.getScoreDrlList());
		scoreDrlFileList = ConfigUtils.inheritMergeableListProperty(
				scoreDrlFileList, inheritedConfig.getScoreDrlFileList());
		kieBaseConfigurationProperties = ConfigUtils
				.inheritMergeableMapProperty(kieBaseConfigurationProperties,
						inheritedConfig.getKieBaseConfigurationProperties());
		initializingScoreTrend = ConfigUtils.inheritOverwritableProperty(
				initializingScoreTrend,
				inheritedConfig.getInitializingScoreTrend());

		assertionScoreDirectorFactory = ConfigUtils
				.inheritOverwritableProperty(assertionScoreDirectorFactory,
						inheritedConfig.getAssertionScoreDirectorFactory());
	}

}
