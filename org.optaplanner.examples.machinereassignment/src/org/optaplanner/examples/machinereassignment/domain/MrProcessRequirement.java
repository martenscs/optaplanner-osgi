/*
 * Copyright 2011 JBoss Inc
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

package org.optaplanner.examples.machinereassignment.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.osgi.common.domain.AbstractPersistable;

@XStreamAlias("MrProcessRequirement")
public class MrProcessRequirement extends AbstractPersistable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1150142211862319898L;
	private MrProcess process;
    private MrResource resource;

    private long usage;

    public MrProcess getProcess() {
        return process;
    }

    public void setProcess(MrProcess process) {
        this.process = process;
    }

    public MrResource getResource() {
        return resource;
    }

    public void setResource(MrResource resource) {
        this.resource = resource;
    }

    public long getUsage() {
        return usage;
    }

    public void setUsage(long usage) {
        this.usage = usage;
    }

}
