// $Id$
/*
 * ====================================================================
 * Copyright (c) 2002-2003, Christophe Labouisse All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package net.ggtools.grand.ui.graph;

import java.io.File;

import net.ggtools.grand.ant.AntProject;
import net.ggtools.grand.exceptions.GrandException;
import net.ggtools.grand.graph.Graph;
import net.ggtools.grand.graph.GraphProducer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A UI friendly wrapper over the Grand core task.
 * 
 * @author Christophe Labouisse
 */
public class GraphModel implements GraphProducer {

    private static final Log log = LogFactory.getLog(GraphModel.class);

    private File lastLoadedFile;

    private GraphProducer producer = null;

    /**
     * @return Returns the currentGraph.
     * @throws GrandException
     */
    public final Graph getGraph() throws GrandException {
        // Do not cache the graph as node may be filtered out
        Graph graph = null;
        if (producer != null) {
            graph = producer.getGraph();
        }
        return graph;
    }

    public void openFile(final File file) throws GrandException {
        if (log.isDebugEnabled()) log.debug("Loading " + file);
        lastLoadedFile = file;
        producer = new AntProject(file);
    }

    public void reload() throws GrandException {
        if (lastLoadedFile != null) {
            if (log.isDebugEnabled()) log.debug("Reloading last file");
            openFile(lastLoadedFile);
        }
        else {
            log.warn("No file previously loaded, skipping reload");
        }
    }

    /**
     * @return Returns the lastLoadedFile.
     */
    final File getLastLoadedFile() {
        return lastLoadedFile;
    }
}