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

/* $Id: None.java,v bc1d5fde7b73 2009/06/01 14:40:54 maxberger $ */

package net.sourceforge.jeuclid.elements.presentation.script;

import net.sourceforge.jeuclid.elements.AbstractInvisibleJEuclidElement;

import org.apache.batik.dom.AbstractDocument;
import org.w3c.dom.Node;

/**
 * This class represent the empty elements none.
 * 
 * @version $Revision: bc1d5fde7b73 $
 */
public final class None extends AbstractInvisibleJEuclidElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "none";

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor. Sets MathML Namespace.
     * 
     * @param qname
     *            Qualified name.
     * @param odoc
     *            Owner Document.
     */
    public None(final String qname, final AbstractDocument odoc) {
        super(qname, odoc);
    }

    /** {@inheritDoc} */
    @Override
    protected Node newNode() {
        return new None(this.nodeName, this.ownerDocument);
    }
}
