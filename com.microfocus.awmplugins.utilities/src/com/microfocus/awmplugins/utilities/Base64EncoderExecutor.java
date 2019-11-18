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

import java.util.Base64;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;

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

public class Base64EncoderExecutor implements IToolExecutor2 {

    private static final String PARM_IN_INPUT = "in.input";

    private static final String PARM_OUT_OUTPUT = "out.output";

    @Override
    public IToolResult executeSingleProcessing(final IToolContext toolContext, final IProgressMonitor progressMonitor) throws TaurusToolException {
        final IStringInputParameter inputParameter = ToolUtility.getInputParameter(toolContext, PARM_IN_INPUT, IStringInputParameter.class);
        final String input = inputParameter.getParameterValue();

        final String output = encode(input);
        final IModeledPropertyOutputParameter outputParameter = ToolUtility.getOutputParameter(toolContext, PARM_OUT_OUTPUT, IModeledPropertyOutputParameter.class);

        final ToolOutput toolOutput = new ToolOutput(Collections.singletonList(new PropertyOutputParameter(outputParameter, output)));
        return new ToolResult(toolContext, toolOutput);
    }

    private static String encode(final String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
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
