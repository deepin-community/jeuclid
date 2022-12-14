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

/* $Id: GraphicsObject.java,v 8afef6dd0d58 2007/09/14 08:29:58 maxberger $ */

package net.sourceforge.jeuclid.layout;

import java.awt.Graphics2D;

/**
 * @version $Revision: 8afef6dd0d58 $
 */
public interface GraphicsObject {

    /**
     * Actually draw the object.
     * 
     * @param x
     *            X-offset of surrounding element.
     * @param y
     *            Y-Offset of baseline of surrounding element.
     * @param g
     *            Graphics Context.
     */
    void paint(float x, float y, Graphics2D g);

}
