/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.integrationtest.deployment.cfg;

import java.util.List;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.repository.Resource;
import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.camunda.bpm.integrationtest.util.DeploymentHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Sebastian Menski
 */
@RunWith(Arquillian.class)
public class TestAdditionalResourceSuffixes extends AbstractFoxPlatformIntegrationTest {

  @Deployment
  public static WebArchive processArchive() {

    WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
        .addAsWebInfResource("org/camunda/bpm/integrationtest/beans.xml", "beans.xml")
        .addAsLibraries(DeploymentHelper.getEngineCdi())
        .addAsResource("org/camunda/bpm/integrationtest/deployment/cfg/processes-additional-resource-suffixes.xml", "META-INF/processes.xml")
        .addClass(AbstractFoxPlatformIntegrationTest.class)
        .addClass(DummyProcessApplication.class)
        .addAsResource("org/camunda/bpm/integrationtest/deployment/cfg/invoice-it.bpmn20.xml")
        .addAsResource("org/camunda/bpm/integrationtest/deployment/cfg/hello.groovy")
        .addAsResource("org/camunda/bpm/integrationtest/deployment/cfg/hello.py");

    return archive;
  }

  @Test
  public void testDeployProcessArchive() {
    assertNotNull(processEngine);
    RepositoryService repositoryService = processEngine.getRepositoryService();

    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
      .processDefinitionKey("invoice-it");

    assertEquals(1, processDefinitionQuery.count());
    ProcessDefinition processDefinition = processDefinitionQuery.singleResult();

    String deploymentId = repositoryService.createDeploymentQuery()
      .deploymentId(processDefinition.getDeploymentId())
      .singleResult()
      .getId();
    List<Resource> deploymentResources = repositoryService.getDeploymentResources(deploymentId);
    assertEquals(3, deploymentResources.size());
  }

}
