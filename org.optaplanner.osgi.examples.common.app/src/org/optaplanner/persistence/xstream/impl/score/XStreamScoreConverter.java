package org.optaplanner.persistence.xstream.impl.score;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.impl.score.definition.ScoreDefinition;

/**
 * Some {@link Score} implementations require specific subclasses: For
 * {@link BendableScore}, use {@link XStreamBendableScoreConverter}.
 */
public class XStreamScoreConverter implements Converter {
	@SuppressWarnings("rawtypes")
	private final ScoreDefinition scoreDefinition;

	@SuppressWarnings("rawtypes")
	public XStreamScoreConverter(ScoreDefinition scoreDefinition) {
		this.scoreDefinition = scoreDefinition;
	}

	@SuppressWarnings("rawtypes")
	public XStreamScoreConverter(Class<? extends Score> scoreClass,
			Class<? extends ScoreDefinition> scoreDefinitionClass) {
		if (BendableScore.class.equals(scoreClass)) {
			throw new IllegalArgumentException(XStreamScoreConverter.class
					+ " is not compatible with scoreClass (" + scoreClass
					+ "), use "
					+ XStreamBendableScoreConverter.class.getSimpleName()
					+ " instead.");
		}
		try {
			scoreDefinition = scoreDefinitionClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("The scoreDefinitionClass ("
					+ scoreDefinitionClass
					+ ") does not have a public no-arg constructor", e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("The scoreDefinitionClass ("
					+ scoreDefinitionClass
					+ ") does not have a public no-arg constructor", e);
		}
		if (scoreClass != scoreDefinition.getScoreClass()) {
			throw new IllegalStateException("The scoreClass (" + scoreClass
					+ ") of the Score field to serialize to XML"
					+ " does not match the scoreDefinition's scoreClass ("
					+ scoreDefinition.getScoreClass() + ").");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean canConvert(Class type) {
		return scoreDefinition.getScoreClass().isAssignableFrom(type);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void marshal(Object scoreObject, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		String scoreString = scoreDefinition.formatScore((Score) scoreObject);
		writer.setValue(scoreString);
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String scoreString = reader.getValue();
		return scoreDefinition.parseScore(scoreString);
	}

}
