/*
 * Copyright (c) ANTVISION 2011 All Rights Reserved Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package cn.antvision.jmx.hello;

/**
 * <p>功能描述,该部分必须以中文句号结尾。<p>
 * 
 * 创建日期 2013-3-29<br>
 * @author $Author$<br>
 * @version $Revision$ $Date$
 * @since 3.0.0
 */
public class Hello implements HelloMBean {
    private String name = "";

    public Hello() {}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void print() {
        System.out.println("Hello, " + name + "!");
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addName() {
        this.name = "zml";
    }
}