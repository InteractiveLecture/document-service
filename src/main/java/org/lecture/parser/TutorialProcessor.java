package org.lecture.parser;

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
import org.lecture.parser.strategy.FallThroughParseStrategy;
import org.lecture.parser.strategy.MarkdownParseStrategy;
import org.lecture.parser.strategy.ParseStrategy;

/**
 * Created by rene on 15.06.15.
 *
 * @author rene
 * @version 0.0.1
 */
public class TutorialProcessor {

  private ParseStrategy parseStrategy;
  private String resultFormat = "HTML";


  public Tutorial processTutorial(Tutorial tutorial) {
    resolveParseStrategy(tutorial.getFormat());
    String newContent = parseStrategy.parseContent(tutorial.getContent());
    tutorial.setContent(newContent);
    tutorial.setFormat(resultFormat);
    return tutorial;
  }


  //TODO extends list of parsers.
  private void resolveParseStrategy(String format) {
    switch (format) {
      case "MARKDOWN" : this.parseStrategy = new MarkdownParseStrategy();
        break;
      default:
        this.parseStrategy = new FallThroughParseStrategy();
        this.resultFormat = format;

    }
  }
}
