package org.lecture.assembler;

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

import org.lecture.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * A common base class for assembler.
 *
 * @author Rene Richter
 */
@Component
public abstract class BaseAssembler<T extends BaseEntity, D extends ResourceSupport>
    extends IdentifiableResourceAssemblerSupport<T, D> {

  protected Class<D> resourceTypeClass;
  @Autowired
  LoadBalancerClient loadBalancerClient;


  public BaseAssembler(Class<?> controllerClass, Class<D> resourceType) {
    super(controllerClass, resourceType);
    this.resourceTypeClass = resourceType;
  }

  /**
   * Adds a link for the given service to the given resource.
   *
   * @param resource    the resource you want to add the link to.
   * @param serviceName the name of the service.
   * @param subUri      the path the resource is located.
   * @param ref         the ref of the link.
   */
  public void linkToService(ResourceSupport resource,
                            String serviceName,
                            String subUri,
                            String ref) {
    ServiceInstance instance = loadBalancerClient.choose(serviceName);
    if (instance != null) {
      resource.add(new Link(instance.getUri().toString() + "/" + subUri, ref));
    }
  }

  /**
   * Adds a link for the given service to the given resource.
   *
   * @param resource    the resource you want to add the link to.
   * @param serviceName the name of the service.
   * @param ref         the ref of the link.
   */
  public void linkToService(ResourceSupport resource,
                            String serviceName,
                            String ref) {
    ServiceInstance instance = loadBalancerClient.choose(serviceName);
    if (instance != null) {
      resource.add(new Link(instance.getUri().toString(), ref));
    }
  }


  @Override
  protected D instantiateResource(T entity) {
    try {
      return resourceTypeClass
          .getConstructor(entity.getClass())
          .newInstance(entity);
    } catch (InvocationTargetException
        | NoSuchMethodException
        | InstantiationException
        | IllegalAccessException e) {
      throw new RuntimeException(
          "Resource does not have an appropriate constructor!", e);
    }
  }
}
