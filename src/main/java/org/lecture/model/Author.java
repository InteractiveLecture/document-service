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

/**
 * Entity that represents Authors.
 * @author Rene Richter
 */
@Document
public class Author extends BaseEntity {

  private String firstname;
  private String lastname;
  private String email;
  

  public Author(){}


  /**
   * a convenience constructor.
   */
  public  Author(String firstname,String lastname,String email) {

    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    
  }

  
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getFirstname() {
    return this.firstname;
  }
  
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getLastname() {
    return this.lastname;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }
  
}
