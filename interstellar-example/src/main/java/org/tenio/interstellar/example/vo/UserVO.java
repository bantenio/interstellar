package org.tenio.interstellar.example.vo;

public class UserVO {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public UserVO setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public UserVO setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
