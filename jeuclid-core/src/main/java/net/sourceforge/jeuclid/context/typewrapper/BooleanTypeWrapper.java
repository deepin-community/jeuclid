/*
 * Copyright 2008 - 2008 JEuclid, http://jeuclid.sf.net
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

/* $Id: BooleanTypeWrapper.java,v 03dc0884e86f 2008/06/21 10:53:35 maxberger $ */

package net.sourceforge.jeuclid.context.typewrapper;

/**
 * Converting String to Boolean and vice versa is also straightforward.
 * 
 * @version $Revision: 03dc0884e86f $
 */
public final class BooleanTypeWrapper extends AbstractSimpleTypeWrapper {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final TypeWrapper INSTANCE = new BooleanTypeWrapper();

    /** Simple constructor. */
    private BooleanTypeWrapper() {
        super(Boolean.class);
    }

    /**
     * @return the singleton instance.
     */
    public static TypeWrapper getInstance() {
        return BooleanTypeWrapper.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public Object fromString(final String value) {
        if (value == null) {
            return null;
        } else {
            return Boolean.valueOf(value);
        }
    }
}
