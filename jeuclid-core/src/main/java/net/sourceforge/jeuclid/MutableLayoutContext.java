/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
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

/* $Id: MutableLayoutContext.java,v 03dc0884e86f 2008/06/21 10:53:35 maxberger $ */

package net.sourceforge.jeuclid;

import net.sourceforge.jeuclid.context.Parameter;


/**
 * @version $Revision: 03dc0884e86f $
 */
public interface MutableLayoutContext extends LayoutContext {

    /**
     * Set a layout Parameter.
     * 
     * @param which
     *            the parameter to set
     * @param newValue
     *            the new Value for this parameter.
     * @return itself for convenience.
     */
    LayoutContext setParameter(Parameter which, Object newValue);

}
