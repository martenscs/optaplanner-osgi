/*
 * Copyright 2014 JBoss Inc
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

package org.optaplanner.examples.examination.domain.solver;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import org.optaplanner.examples.examination.domain.Exam;
import org.optaplanner.examples.examination.domain.Examination;
import org.optaplanner.examples.examination.domain.LeadingExam;
import org.optaplanner.examples.examination.domain.PeriodPenalty;

public class ExamDifficultyWeightFactory implements SelectionSorterWeightFactory<Examination, Exam> {

    @SuppressWarnings("rawtypes")
	public Comparable createSorterWeight(Examination examination, Exam exam) {
        int studentSizeTotal = exam.getTopicStudentSize();
        int maximumDuration = exam.getTopicDuration();
        for (PeriodPenalty periodPenalty : examination.getPeriodPenaltyList()) {
            if (periodPenalty.getLeftTopic().equals(exam.getTopic())) {
                switch (periodPenalty.getPeriodPenaltyType()) {
                    case EXAM_COINCIDENCE:
                        studentSizeTotal += periodPenalty.getRightTopic().getStudentSize();
                        maximumDuration = Math.max(maximumDuration, periodPenalty.getRightTopic().getDuration());
                        break;
                    case EXCLUSION:
                        // Do nothing
                        break;
                    case AFTER:
                        // Do nothing
                        break;
                    default:
                        throw new IllegalStateException("The periodPenaltyType ("
                                + periodPenalty.getPeriodPenaltyType() + ") is not implemented.");
                }
            } else if (periodPenalty.getRightTopic().equals(exam.getTopic())) {
                switch (periodPenalty.getPeriodPenaltyType()) {
                    case EXAM_COINCIDENCE:
                        studentSizeTotal += periodPenalty.getLeftTopic().getStudentSize();
                        maximumDuration = Math.max(maximumDuration, periodPenalty.getLeftTopic().getDuration());
                        break;
                    case EXCLUSION:
                        // Do nothing
                        break;
                    case AFTER:
                        studentSizeTotal += periodPenalty.getLeftTopic().getStudentSize();
                        maximumDuration = Math.max(maximumDuration, periodPenalty.getLeftTopic().getDuration());
                        break;
                    default:
                        throw new IllegalStateException("The periodPenaltyType ("
                                + periodPenalty.getPeriodPenaltyType() + ") is not implemented.");
                }
            }
        }
        return new ExamDifficultyWeight(exam, studentSizeTotal, maximumDuration);
    }

    public static class ExamDifficultyWeight implements Comparable<ExamDifficultyWeight> {

        private final Exam exam;
        private final int studentSizeTotal;
        private final int maximumDuration;

        public ExamDifficultyWeight(Exam exam, int studentSizeTotal, int maximumDuration) {
            this.exam = exam;
            this.studentSizeTotal = studentSizeTotal;
            this.maximumDuration = maximumDuration;
        }

        public int compareTo(ExamDifficultyWeight other) {
            return new CompareToBuilder()
                    .append(studentSizeTotal, other.studentSizeTotal)
                    .append(maximumDuration, other.maximumDuration)
                    .append(exam instanceof LeadingExam, other.exam instanceof LeadingExam)
                    .append(exam.getId(), other.exam.getId())
                    .toComparison();
        }

    }

}
