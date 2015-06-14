package org.lecture.model;

/*
 * Copyright (c) 2015 Rene Richter
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

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

/**
 * Entity that represents Tutorials.
 * @author Rene Richter
 */
@Document
public class Tutorial extends BaseEntity {

  private List<Author> authors;
  private String title;
  private String content;
  private String format;
  private LocalDate publishingDate;
  private LocalDate lastChangedDate;
  

  public Tutorial(){}


  /**
   * a convenience constructor.
   */
  public  Tutorial(List<Author> authors,String title,String content,String format,LocalDate publishingDate,LocalDate lastChangedDate) {

    this.authors = authors;
    this.title = title;
    this.content = content;
    this.format = format;
    this.publishingDate = publishingDate;
    this.lastChangedDate = lastChangedDate;
    
  }

  
  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public List<Author> getAuthors() {
    return this.authors;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }
  
  public void setContent(String content) {
    this.content = content;
  }

  public String getContent() {
    return this.content;
  }
  
  public void setFormat(String format) {
    this.format = format;
  }

  public String getFormat() {
    return this.format;
  }
  
  public void setPublishingdate(LocalDate publishingDate) {
    this.publishingDate = publishingDate;
  }

  public LocalDate getPublishingdate() {
    return this.publishingDate;
  }
  
  public void setLastchangeddate(LocalDate lastChangedDate) {
    this.lastChangedDate = lastChangedDate;
  }

  public LocalDate getLastchangeddate() {
    return this.lastChangedDate;
  }
  
}
