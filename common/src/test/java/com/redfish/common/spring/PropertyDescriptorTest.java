package com.redfish.common.spring;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PropertyDescriptorTest {

    @Getter
    @Setter
    @NoArgsConstructor
    public class Student {

        private String personId;

        private String humanName;

        private String age;
    }

    @Data
    public class Person {

        private String id;

        private String email;

        private BigDecimal money;

        // 因为要通过BeanWrapper的setPropertyValue方法赋值,因此此处要有初始值
        private List<String> hobbyList = new ArrayList<>();

        // 因为要通过BeanWrapper的setPropertyValue方法赋值,因此此处要有初始值
        private Student student = new Student();
    }

    @Test
    public void demoTest(){
        // 创建person对象
        Person person = new Person();
// 通过Spring的PropertyAccessorFactory对象创建BeanWrapper对象
        BeanWrapper personBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(person);

// 获取id属性的PropertyDescriptor对象,如果id属性不存在或者没有get/set方法会报错
        PropertyDescriptor idDescriptor = personBeanWrapper.getPropertyDescriptor("id");
        System.out.println(idDescriptor);
    }

}
