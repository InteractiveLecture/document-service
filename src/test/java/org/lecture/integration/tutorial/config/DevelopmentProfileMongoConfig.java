package org.lecture.integration.tutorial.config;

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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoFactoryBean;

/**
 * Created by Rene Richter on 29.06.15.
 *
 * @author Rene Richter
 * @version 0.0.1
 */
@Configuration
@Profile("development")
public class DevelopmentProfileMongoConfig {
  @Bean
  public MongoFactoryBean mongo() {
    MongoFactoryBean mongo = new MongoFactoryBean();
    mongo.setHost("localhost");
    return mongo;
  }
}
