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

package org.optaplanner.examples.cloudbalancing.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.osgi.common.domain.AbstractPersistable;

@XStreamAlias("CloudComputer")
public class CloudComputer extends AbstractPersistable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private Integer cpuPower; // in gigahertz
	private Integer memory; // in gigabyte RAM
	private Integer networkBandwidth; // in gigabyte per hour
	private Integer cost; // in euro per month

	public int getCpuPower() {
		return cpuPower;
	}

	public void setCpuPower(Integer cpuPower) {
		this.cpuPower = cpuPower;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public int getNetworkBandwidth() {
		return networkBandwidth;
	}

	public void setNetworkBandwidth(Integer networkBandwidth) {
		this.networkBandwidth = networkBandwidth;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	// ************************************************************************
	// Complex methods
	// ************************************************************************

	public int getMultiplicand() {
		return cpuPower * memory * networkBandwidth;
	}

	public String getLabel() {
		return "Computer " + id;
	}

}
