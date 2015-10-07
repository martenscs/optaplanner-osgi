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

package org.optaplanner.osgi.common.domain;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class PersistableIdComparator implements Comparator<AbstractPersistable>, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3482663212421071202L;

	public int compare(AbstractPersistable a, AbstractPersistable b) {
        return new CompareToBuilder().append(a.getId(), b.getId()).toComparison();
    }

}