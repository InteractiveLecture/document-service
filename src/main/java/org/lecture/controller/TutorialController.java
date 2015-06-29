package org.lecture.controller;

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

import org.lecture.assembler.TutorialAssembler;
import org.lecture.model.Tutorial;
import org.lecture.parser.TutorialProcessor;
import org.lecture.patchservice.PatchService;
import org.lecture.repository.TutorialRepository;
import org.lecture.resource.TutorialResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * A controller for Tutorial Routes.
 * @author Rene Richter
 */
@RestController
@RequestMapping("/tutorials")
@ExposesResourceFor(Tutorial.class)
public class TutorialController extends BaseController {

  @Autowired
  TutorialAssembler tutorialAssembler;

  @Autowired
  TutorialRepository tutorialRepository;

  /**
   * @param entity the tutorial from the post-request. This tutorial is deserialized by
   *              jackson.
   * @return A respoonse containing a link to the new resource.
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> create(@RequestBody Tutorial entity) {
    return super.createEntity(this.tutorialRepository.save(entity));
  }

  /**
   * Returns one Tutorial.
   *
   * @param id the id of the topic to return.
   * @return a response.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<TutorialResource> getOne(@PathVariable String id) {

    TutorialProcessor tutorialProcessor = new TutorialProcessor();
    Tutorial tutorial =
        tutorialProcessor.processTutorial(tutorialRepository.findOne(id));
    TutorialResource result = tutorialAssembler.toResource(tutorial);
    return ResponseEntity.ok().body(result);
  }

  @RequestMapping(value = "/{id}/raw", method = RequestMethod.GET)
  public ResponseEntity<TutorialResource> getOneRaw(@PathVariable String id) {
    TutorialResource result
            = tutorialAssembler.toResource(tutorialRepository.findOne(id));
    return ResponseEntity.ok().body(result);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(@PathVariable String id) {
    tutorialRepository.delete(id);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<?> update(@PathVariable String id,
                                  @RequestBody String patch) {

    PatchService patchService = new PatchService();
    Tutorial tutorial = tutorialRepository.findOne(id);
    tutorial.setContent(patchService.patch(tutorial.getContent(),patch));
    tutorialRepository.save(tutorial);
    return ResponseEntity.noContent().build();
  }


}
