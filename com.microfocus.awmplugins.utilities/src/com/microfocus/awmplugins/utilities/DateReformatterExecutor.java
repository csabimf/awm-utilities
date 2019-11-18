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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

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

public class DateReformatterExecutor implements IToolExecutor2 {

    private static final String PARM_IN_INPUT = "in.input";

    private static final String PARM_OUT_OUTPUT = "out.output";

    private static final String ATTR_INPUT_FORMAT = "attr.inputFormat";

    private static final String ATTR_OUTPUT_FORMAT = "attr.outputFormat";

    @Override
    public IToolResult executeSingleProcessing(final IToolContext toolContext, final IProgressMonitor progressMonitor) throws TaurusToolException {
        final String inputFormat = (String) toolContext.getAttributeValue(ATTR_INPUT_FORMAT);
        final String outputFormat = (String) toolContext.getAttributeValue(ATTR_OUTPUT_FORMAT);

        final IStringInputParameter inputParameter = ToolUtility.getInputParameter(toolContext, PARM_IN_INPUT, IStringInputParameter.class);
        final String input = inputParameter.getParameterValue();

        String output;
        try {
            output = reformatDate(input, inputFormat, outputFormat);
        } catch (final ParseException e) {
            throw new TaurusToolException(String.format("Can't parse date `%s` using format `%s`.", input, inputFormat), e);
        }
        final IModeledPropertyOutputParameter outputParameter = ToolUtility.getOutputParameter(toolContext, PARM_OUT_OUTPUT, IModeledPropertyOutputParameter.class);

        final ToolOutput toolOutput = new ToolOutput(Collections.singletonList(new PropertyOutputParameter(outputParameter, output)));
        return new ToolResult(toolContext, toolOutput);
    }

    private static String reformatDate(final String input, final String inputPattern, final String outputPattern) throws ParseException {
        final DateFormat inputFormat = new SimpleDateFormat(inputPattern);
        final Date date = inputFormat.parse(input);
        final DateFormat outputFormat = new SimpleDateFormat(outputPattern);
        return outputFormat.format(date);
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
