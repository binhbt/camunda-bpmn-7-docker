package org.camunda.quickstart.servicetask.invocation.sync;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.ExecutionListener;
public class ExampleExecutionListenerOne implements ExecutionListener {

    public void notify(DelegateExecution execution) throws Exception {
      System.out.println("====ExampleExecutionListenerOne=====");
      execution.setVariable("variableSetInExecutionListener", "firstValue");
      execution.setVariable("eventReceived", execution.getEventName());

    }
}