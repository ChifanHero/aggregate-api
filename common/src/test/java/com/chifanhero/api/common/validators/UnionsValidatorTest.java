package com.chifanhero.api.common.validators;

import com.chifanhero.api.common.annotations.Union;
import com.chifanhero.api.common.annotations.Unions;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class UnionsValidatorTest {

    @Test
    public void testValid() {
        TestClass testClass = new TestClass();
        testClass.setField1("test");
        testClass.setField3("test");
        Set<ConstraintViolation<TestClass>> violations = validate(testClass);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testBothNull() {
        TestClass testClass = new TestClass();
        Set<ConstraintViolation<TestClass>> violations = validate(testClass);
        Assert.assertTrue(violations.size() == 2);
    }

    @Test
    public void testBothNotNull() {
        TestClass testClass = new TestClass();
        testClass.setField1("");
        testClass.setField2("");
        testClass.setField3("");
        testClass.setField4("");
        Set<ConstraintViolation<TestClass>> violations = validate(testClass);
        Assert.assertTrue(violations.size() == 2);
    }

    private Set<ConstraintViolation<TestClass>> validate(TestClass testClass) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(testClass);
    }

    @Unions({
            @Union(field1 = "field1", field2 = "field2"),
            @Union(field1 = "field3", field2 = "field4")
    })
    public class TestClass {
        private String field1;
        private String field2;
        private String field3;
        private String field4;

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public void setField3(String field3) {
            this.field3 = field3;
        }

        public void setField4(String field4) {
            this.field4 = field4;
        }

        public String getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }

        public String getField3() {
            return field3;
        }

        public String getField4() {
            return field4;
        }
    }
}
