/*
/*
 * (c) Copyright 2019 Micro Focus or one of its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.microfocus.awmplugins.utilities;

import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.microfocus.awm.control.toolexecution.PropertyOutputParameter;
import com.microfocus.awm.control.toolexecution.ToolOutput;
import com.microfocus.awm.control.toolexecution.ToolResult;
import com.microfocus.awm.control.toolexecution.ToolUtility;
import com.microfocus.awm.core.TaurusToolException;
import com.microfocus.awm.model.toolexecution.IMassProcessingToolContext;
import com.microfocus.awm.model.toolexecution.IModeledPropertyOutputParameter;
import com.microfocus.awm.model.toolexecution.IStringInputParameter;
import com.microfocus.awm.model.toolexecution.IToolContext;
import com.microfocus.awm.model.toolexecution.IToolExecutor2;
import com.microfocus.awm.model.toolexecution.IToolResult;

public class TagStripperExecutor implements IToolExecutor2 {

    private static final String INPARM_INPUT = "in.input";

    private static final String OUTPARM_OUTPUT = "out.output";

    @Override
    public IToolResult executeSingleProcessing(final IToolContext toolContext, final IProgressMonitor progressMonitor) throws TaurusToolException {
        final IStringInputParameter inputParameter = ToolUtility.getInputParameter(toolContext, INPARM_INPUT, IStringInputParameter.class);
        final String input = inputParameter.getParameterValue();

        final String output = stripTags(input);
        final IModeledPropertyOutputParameter outputParameter = ToolUtility.getOutputParameter(toolContext, OUTPARM_OUTPUT, IModeledPropertyOutputParameter.class);

        final ToolOutput toolOutput = new ToolOutput(Collections.singletonList(new PropertyOutputParameter(outputParameter, output)));
        return new ToolResult(toolContext, toolOutput);
    }

    private static String stripTags(final String s) {
        final Document document = Jsoup.parse(s);
        final Element element = document.ownerDocument().body();
        return element.wholeText();
    }

    @Override
    public IToolResult executeMassProcessing(final IMassProcessingToolContext massProcessingToolContext, final IProgressMonitor progressMonitor) throws TaurusToolException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean supportsMassProcessing() {
        return false;
    }

}
