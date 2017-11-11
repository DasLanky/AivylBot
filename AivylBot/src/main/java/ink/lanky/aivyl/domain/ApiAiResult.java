/*
 * Copyright 2017 Pivotal Software, Inc..
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
package ink.lanky.aivyl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiAiResult {
    private String source;
    private String resolvedQuery;
    private String action;
    private boolean actionIncomplete;
    private List<ApiAiContext> contexts;
    private ApiAiMetadata metadata;
    private ApiAiFulfillment fulfillment;
    private HashMap<String, Object> parameters;
    private double score;

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResolvedQuery() {
        return resolvedQuery;
    }

    public void setResolvedQuery(String resolvedQuery) {
        this.resolvedQuery = resolvedQuery;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isActionIncomplete() {
        return actionIncomplete;
    }

    public void setActionIncomplete(boolean actionIncomplete) {
        this.actionIncomplete = actionIncomplete;
    }

    public List<ApiAiContext> getContexts() {
        return contexts;
    }

    public void setContexts(List<ApiAiContext> contexts) {
        this.contexts = contexts;
    }

    public ApiAiMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ApiAiMetadata metadata) {
        this.metadata = metadata;
    }

    public ApiAiFulfillment getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(ApiAiFulfillment fulfillment) {
        this.fulfillment = fulfillment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
