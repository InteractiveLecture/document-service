package org.lecture.unit.tutorial.controller;
/*
 * Copyright (c) 2015 Rene Richter.
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lecture.assembler.TutorialAssembler;
import org.lecture.controller.TutorialController;
import org.lecture.model.Tutorial;
import org.lecture.patchservice.dmp.DmpPatchService;
import org.lecture.repository.TutorialRepository;
import org.lecture.resource.TutorialResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Unit test for Tutorial controllers.
 *
 * @author Rene Richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TutorialControllerUnitTestConfig.class})
public class TutorialControllerUnitTest {

  @Autowired
  private TutorialRepository tutorialRepository;

  @Autowired
  private TutorialAssembler tutorialAssembler;

  @Autowired
  private PagedResourcesAssembler pagedResourcesAssembler;

  @Autowired
  private TutorialController testInstance;


  /**
   * sets up the test.
   */
  @Before
  public void setUp() {
    reset(tutorialRepository, tutorialAssembler, pagedResourcesAssembler);
  }


  @Test
  public void getOneShouldReturnResponseContainingTheDataOfOneTutorialAsJson() throws Exception {
    Tutorial instance = new Tutorial();
    instance.setId("1");
    instance.setFormat("MARKDOWN");
    instance.setContent("# Hallo Welt!");
    TutorialResource testResource = new TutorialResource(instance);
    when(tutorialRepository.findOne("1")).thenReturn(instance);
    when(tutorialAssembler.toResource(instance)).thenReturn(testResource);
    ResponseEntity response = testInstance.getOne("1");
    assertEquals(200, response.getStatusCode().value());
    verify(tutorialRepository, times(1)).findOne("1");
    verify(tutorialAssembler, times(1)).toResource(instance);
  }


  @Test
  public void patchShouldPatchADocumentWithNewContent() throws Exception {
    String original = new String(
        Files.readAllBytes(Paths.get(getClass().getResource("/original.md").toURI())));

    Tutorial instance = new Tutorial();
    instance.setId("1");
    instance.setContent(original);
    instance.setFormat("MARKDOWN");
    TutorialResource testResource = new TutorialResource(instance);
    when(tutorialRepository.findOne("1")).thenReturn(instance);

    String modified = new String(
        Files.readAllBytes(Paths.get(getClass().getResource("/modified.md").toURI())));
    String patch = new DmpPatchService().createPatch(original, modified);
    ResponseEntity<?> response = testInstance.update("1", patch);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    assertEquals(instance.getContent(), modified);

  }
}