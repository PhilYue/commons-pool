/*
 * Copyright 2006 The Apache Software Foundation.
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

package org.apache.commons.pool.composite;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Specifies how active objects are tracked while they are out of the pool.
 *
 * @see CompositeObjectPoolFactory#setTrackerType(TrackingType)
 * @see CompositeKeyedObjectPoolFactory#setTrackerType(TrackingType)
 * @author Sandy McArthur
 * @since #.#
 * @version $Revision$ $Date$
 */
public final class TrackingType implements Serializable {

    private static final long serialVersionUID = 181851949909846032L;

    /**
     * Do not track how objects are borrowed from the pool. While this is the fastest tracking type it is
     * incompatable with pools that limit the number of objects in the pool.
     */
    public static final TrackingType NULL = new TrackingType("NULL");

    /**
     * Borrowed object tracker that trusts you'll return the objects you borrow. This is the most common method of
     * tracking active objects. If you return more objects than you borrow then this will throw an
     * {@link IllegalStateException}.
     */
    public static final TrackingType SIMPLE = new TrackingType("SIMPLE");

    /**
     * Borrowed object tracker that detects when borrowed objects are lost. This implementation keeps track of
     * borrowed objects with {@link WeakReference weak references} and decrements the active count when lost objects
     * are garbage collected. The point in time at which an object is detected as being lost is dependant on the
     * first object borrowed or returned to the pool after garbage collector has collected the lost object. If you
     * return an object that wasn't borrowed from the pool an {@link IllegalStateException} will be thrown.
     */
    public static final TrackingType REFERENCE = new TrackingType("REFERENCE");

    /**
     * Like {@link #REFERENCE} but prints a stack trace for lost objects from when the object was borrowed from the
     * pool. The point in time at which the stack trace is printed is dependent on the garbage collector's behavior.
     */
    public static final TrackingType DEBUG = new TrackingType("DEBUG");

    /**
     * enum name.
     */
    private final String name;

    private TrackingType(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    // Autogenerated with Java 1.5 enums
    public static TrackingType[] values() {
        return new TrackingType[] {NULL, SIMPLE, REFERENCE, DEBUG};
    }

    // necessary for serialization
    private static int nextOrdinal = 0;
    private final int ordinal = nextOrdinal++;
    private Object readResolve() throws ObjectStreamException {
        return values()[ordinal];
    }
}