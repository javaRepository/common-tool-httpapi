package com.tool.httpapi.test.model;

import org.springframework.web.multipart.MultipartFile;

public class PeopleRequest {
    private String name;
    private Integer age;
    private Double height;
    private MultipartFile avatar;
    private MultipartFile gallery;
    private MultipartFile article;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public MultipartFile getGallery() {
        return gallery;
    }

    public void setGallery(MultipartFile gallery) {
        this.gallery = gallery;
    }

    public MultipartFile getArticle() {
        return article;
    }

    public void setArticle(MultipartFile article) {
        this.article = article;
    }


    @Override
    public String toString() {
        return "PeopleRequest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", avatar=" + avatar +
                ", gallery=" + gallery +
                ", article=" + article +
                '}';
    }

}
