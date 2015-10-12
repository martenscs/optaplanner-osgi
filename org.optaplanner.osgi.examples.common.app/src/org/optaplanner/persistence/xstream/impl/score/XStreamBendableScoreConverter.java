package org.optaplanner.persistence.xstream.impl.score;

import org.optaplanner.core.impl.score.buildin.bendable.BendableScoreDefinition;

public class XStreamBendableScoreConverter extends XStreamScoreConverter {

	public XStreamBendableScoreConverter(int hardLevelsSize, int softLevelsSize) {
		super(new BendableScoreDefinition(hardLevelsSize, softLevelsSize));
	}

}