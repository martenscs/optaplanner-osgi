/*
 * Copyright 2012 JBoss Inc
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

package org.optaplanner.examples.vehiclerouting.domain.location;

import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.osgi.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamInclude;

@XStreamAlias("VrpLocation")
@XStreamInclude({ AirLocation.class, RoadLocation.class })
public abstract class Location extends AbstractPersistable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name = null;
	protected double latitude;
	protected double longitude;

	public Location() {
	}

	public Location(long id, double latitude, double longitude) {
		super(id);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	// ************************************************************************
	// Complex methods
	// ************************************************************************

	/**
	 * The distance's unit of measurement depends on the
	 * {@link VehicleRoutingSolution}'s {@link DistanceType}. It can be in miles
	 * or km, but for most cases it's in the TSPLIB's unit of measurement.
	 * 
	 * @param location
	 *            never null
	 * @return a positive number, the distance multiplied by 1000 to avoid
	 *         floating point arithmetic rounding errors
	 */
	public abstract long getDistanceTo(Location location);

	public double getAirDistanceDoubleTo(Location location) {
		// Implementation specified by TSPLIB
		// http://www2.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/
		// Euclidean distance (Pythagorean theorem) - not correct when the
		// surface is a sphere
		double latitudeDifference = location.latitude - latitude;
		double longitudeDifference = location.longitude - longitude;
		return Math.sqrt((latitudeDifference * latitudeDifference)
				+ (longitudeDifference * longitudeDifference));
	}

	/**
	 * The angle relative to the direction EAST.
	 * 
	 * @param location
	 *            never null
	 * @return in Cartesian coordinates
	 */
	public double getAngle(Location location) {
		// Euclidean distance (Pythagorean theorem) - not correct when the
		// surface is a sphere
		double latitudeDifference = location.latitude - latitude;
		double longitudeDifference = location.longitude - longitude;
		return Math.atan2(latitudeDifference, longitudeDifference);
	}

	@Override
	public String toString() {
		if (name == null) {
			return super.toString();
		}
		return name;
	}

}