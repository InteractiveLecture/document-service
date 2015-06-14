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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.lecture.assembler.TutorialAssembler;
import org.lecture.controller.TutorialController;
import org.lecture.repository.TutorialRepository;
import org.lecture.resource.TutorialResource;
import org.lecture.model.Tutorial;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

/**
* Unit test for Tutorial controllers.
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
    reset(tutorialRepository,tutorialAssembler,pagedResourcesAssembler);
  }


  @Test
  public void getAllShouldReturnAPageOfTutorial() throws Exception {

    List<Tutorial> sampleData = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Tutorial instance = new Tutorial();
      instance.setId(i);
      sampleData.add(instance);
    }
    Page<Tutorial> page = new PageImpl<>(sampleData);
    when(tutorialRepository.findAll(any(Pageable.class))).thenReturn(page);
    when(pagedResourcesAssembler.toResource(page,tutorialAssembler))
      .thenReturn(new PagedResources(sampleData,null));

    Pageable pageable = new PageRequest(2,2);
    PagedResources result = testInstance.getAll(pageable,pagedResourcesAssembler);
    assertEquals(10,result.getContent().size());
    verify(tutorialRepository, times(1)).findAll(any(Pageable.class));
    verify(pagedResourcesAssembler,times(1)).toResource(eq(page),eq(tutorialAssembler));
    verifyNoMoreInteractions(tutorialRepository);
    verifyNoMoreInteractions(tutorialAssembler);
    verifyNoMoreInteractions(pagedResourcesAssembler);
  }

  @Test
  public void getOneShouldReturnResponseContainingTheDataOfOneTutorialAsJson() throws Exception {
    Tutorial instance = new Tutorial();
    instance.setId(1L);
    TutorialResource testResource = new TutorialResource(instance);
    when(tutorialRepository.findOne(1L)).thenReturn(instance);
    when(tutorialAssembler.toResource(instance)).thenReturn(testResource);
    ResponseEntity response = testInstance.getOne(1L);
    assertEquals(200,response.getStatusCode().value());
    verify(tutorialRepository, times(1)).findOne(1L);
    verify(tutorialAssembler, times(1)).toResource(instance);
  }
}