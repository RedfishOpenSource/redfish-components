package com.redfish.common.spring;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PropertyDescriptorTest {


    public class Person {

        private String id;

        private String email;

        private BigDecimal money;

        // 因为要通过BeanWrapper的setPropertyValue方法赋值,因此此处要有初始值
        private List<String> hobbyList = new ArrayList<>();

        // 因为要通过BeanWrapper的setPropertyValue方法赋值,因此此处要有初始值
        private Student student = new Student();


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public BigDecimal getMoney() {
            return money;
        }

        public void setMoney(BigDecimal money) {
            this.money = money;
        }

        public List<String> getHobbyList() {
            return hobbyList;
        }

        public void setHobbyList(List<String> hobbyList) {
            this.hobbyList = hobbyList;
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }
    }





    public class Student implements Serializable {

        private String personId;

        private String humanName;

        private String age;

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getHumanName() {
            return humanName;
        }

        public void setHumanName(String humanName) {
            this.humanName = humanName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }


    @Test
    public void demo(){
        // 创建person对象
        Person person = new Person();
// 通过Spring的PropertyAccessorFactory对象创建BeanWrapper对象
        BeanWrapper personBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(person);

// 获取id属性的PropertyDescriptor对象,如果id属性不存在或者没有get/set方法会报错
        PropertyDescriptor idDescriptor = personBeanWrapper.getPropertyDescriptor("id");
        System.out.println(idDescriptor);
    }

}
