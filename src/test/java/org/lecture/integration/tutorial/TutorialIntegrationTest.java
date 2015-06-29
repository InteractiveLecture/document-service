package org.lecture.integration.tutorial;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestUtil.TEXT_PLAIN_UTF8;
import static util.TestUtil.toJson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lecture.model.Tutorial;
import org.lecture.patchservice.PatchService;
import org.lecture.patchservice.dmp.DmpPatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import util.TestUtil;

import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * A integration test for Tutorials.
 *
 * @author Rene Richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TutorialIntegrationTestConfig.class})
@WebAppConfiguration
//TODO add custom sample-data in tutorialSampleData.java
public class TutorialIntegrationTest {

  @Autowired
  TutorialSampleData sampleData;
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;

  /**
   * sets up the test.
   */
  @Before
  public void setUp() {
    sampleData.seed();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @After
  public void destroy() {
    sampleData.destroy();
  }


  @Test
  public void testGetOne()
      throws Exception {
    mockMvc.perform(get("/tutorials/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
        .andExpect(
            jsonPath("$._links.self.href", is("http://localhost/tutorials/1")));
  }

  @Test
  public void testCreateTutorial() throws Exception {
    mockMvc.perform(post("/tutorials")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(toJson(new Tutorial())))
        .andExpect(status().isCreated());
  }

  @Test
  public void testPatch() throws Exception {

    String original = "Hallo Welt!";

    String modified = new String(
        Files.readAllBytes(Paths.get(getClass().getResource("/modified.md").toURI())));

    PatchService ps = new DmpPatchService();

    String patch = ps.createPatch(original, modified);
    mockMvc.perform(patch("/tutorials/1").content(patch).contentType(TEXT_PLAIN_UTF8))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/tutorials/1/raw"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", is(modified)));

  }
}