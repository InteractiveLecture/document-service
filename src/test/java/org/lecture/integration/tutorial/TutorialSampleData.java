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

import org.lecture.model.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;



@Component
public class TutorialSampleData {

  @Autowired
  MongoOperations mongoOperations;

  public void seed() {

    for (int i = 0; i < 10; i++) {
      Tutorial b = new Tutorial();
      b.setId(String.valueOf(i));
      mongoOperations.save(b);
    }
  }

  public void destroy() {
    mongoOperations.dropCollection("Tutorial");
  }
}