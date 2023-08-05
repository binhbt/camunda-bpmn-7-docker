package org.camunda.quickstart.servicetask.invocation.sync;

import java.util.logging.Level;
import java.util.logging.Logger;


import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;

public class TaskAssignmentListener implements TaskListener {

  // TODO: Set Mail Server Properties
  protected static final String HOST = "mail.example.org";
  protected static final String USER = "admin@example.org";
  protected static final String PWD = "toomanysecrets";

  protected final static Logger LOGGER = Logger.getLogger(TaskAssignmentListener.class.getName());

  public void notify(DelegateTask delegateTask) {

    String assignee = delegateTask.getAssignee();
    String taskId = delegateTask.getId();

    if (assignee != null) {

      // Get User Profile from User Management
      IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
      User user = identityService.createUserQuery().userId(assignee).singleResult();

      if (user != null) {

        // Get Email Address from User Profile
        String recipient = user.getEmail();

        if (recipient != null && !recipient.isEmpty()) {

          System.out.println("Send email to "+recipient);

        } else {
          LOGGER.warning("Not sending email to user " + assignee + "', user has no email address.");
        }

      } else {
        LOGGER.warning("Not sending email to user " + assignee + "', user is not enrolled with identity service.");
      }

    }

  }

}
