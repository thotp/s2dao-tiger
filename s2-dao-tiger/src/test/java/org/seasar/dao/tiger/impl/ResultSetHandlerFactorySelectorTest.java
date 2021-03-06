/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.dao.tiger.impl;

import java.lang.reflect.Method;

import org.seasar.dao.AnnotationReaderFactory;
import org.seasar.dao.BeanMetaData;
import org.seasar.dao.BeanMetaDataFactory;
import org.seasar.dao.DaoAnnotationReader;
import org.seasar.dao.ResultSetHandlerFactory;
import org.seasar.dao.impl.Employee;
import org.seasar.dao.unit.S2DaoTestCase;
import org.seasar.extension.jdbc.ResultSetHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author jundu
 * 
 */
public class ResultSetHandlerFactorySelectorTest extends S2DaoTestCase {

    ResultSetHandlerFactory resultSetHandlerFactory;

    AnnotationReaderFactory annotationReaderFactory;

    BeanMetaDataFactory beanMetaDataFactory;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        include("dao.dicon");
    }

    /**
     * Test method for
     * {@link org.seasar.dao.impl.ResultSetHandlerFactorySelector#getResultSetHandler(org.seasar.dao.DaoAnnotationReader, org.seasar.dao.BeanMetaData, java.lang.reflect.Method)}.
     */
    public void testGetResultSetHandler() {
        BeanDesc daoBeanDesc = BeanDescFactory.getBeanDesc(EmployeeDao.class);
        DaoAnnotationReader daoAnnotationReader = annotationReaderFactory
                .createDaoAnnotationReader(daoBeanDesc);
        BeanMetaData beanMetaData = beanMetaDataFactory
                .createBeanMetaData(Employee.class);
        Method[] methods = EmployeeDao.class.getMethods();
        Method method = findMethod(methods, "fetchAll");
        ResultSetHandler resultSetHandler = resultSetHandlerFactory
                .getResultSetHandler(daoAnnotationReader, beanMetaData, method);
        assertTrue(resultSetHandler instanceof FetchResultSetHandler);
    }

    protected Method findMethod(Method[] methods, String name) {
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }
}
