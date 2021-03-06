package com.tool.httpapi.test;

import com.alibaba.fastjson.JSON;
import com.tool.httpapi.test.model.People;
import com.tool.httpapi.test.model.ResponsePeople;
import com.tool.httpapi.exception.StatusCodeNot200Exception;
import com.jtool.support.log.LogHelper;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.tool.httpapi.HttpApi.Api;

@SpringBootApplication
public class HttpApiTest {

    private final String host = "http://localhost:8080";

    @BeforeClass
    public static void setup() {
        LogHelper.setLogId(UUID.randomUUID().toString());
        String[] args = new String[0];
        SpringApplication.run(HttpApiTest.class, args);
    }

    @Test
    public void getTest() throws Exception {
        Assert.assertEquals("{}", Api().get(host + "/sentGet"));

        ResponsePeople responsePeople = JSON.parseObject(Api().get(host + "/sentGet?name=中文名"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void getURLEncoderTest() throws Exception {
        People people = new People();
        people.setName("1+1");

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).get(host + "/sentGet"), ResponsePeople.class);
        Assert.assertEquals("1+1", responsePeople.getName());
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void getTest2() throws Exception {
        People people = new People();
        people.setName("中文名");
        people.setAge(30);
        people.setHeight(1.73);

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).get(host + "/sentGet"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(30), responsePeople.getAge());
        Assert.assertEquals(new Double(1.73), responsePeople.getHeight());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void getTest3() throws Exception {
        ResponsePeople responsePeople = JSON.parseObject(Api().get(host + "/sentGet?name=中文&age=22&height=1.66"), ResponsePeople.class);
        Assert.assertEquals("中文", responsePeople.getName());
        Assert.assertEquals(new Integer(22), responsePeople.getAge());
        Assert.assertEquals(new Double(1.66), responsePeople.getHeight());
    }

    @Test
    public void getTest4() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "map中文名");
        params.put("age", 31);
        params.put("height", 1.74);

        ResponsePeople responsePeople = JSON.parseObject(Api().param(params).get(host + "/sentGet"), ResponsePeople.class);
        Assert.assertEquals("map中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(31), responsePeople.getAge());
        Assert.assertEquals(new Double(1.74), responsePeople.getHeight());
    }

    @Test(expected = StatusCodeNot200Exception.class)
    public void getGet404() throws Exception {
        Api().get(host + "/404");
    }

    @Test(expected = IOException.class)
    public void getIoException() throws Exception {
        Api().get("http://xxx.abc");
    }

    @Test
    public void postWithNoParamTest() throws Exception {
        //发送没参数的post请求
        Assert.assertEquals("{}", Api().post(host + "/sentPost"));
        Assert.assertEquals("{}", Api().restPost(host + "/restPost"));
    }

    @Test
    public void postWithUrlParamTest() throws Exception {
        ResponsePeople responsePeople = JSON.parseObject(Api().post(host + "/sentPost?name=中文名"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void restPostWithUrlParamTest() throws Exception {
        ResponsePeople responsePeople = JSON.parseObject(Api().restPost(host + "/restPost?name=中文名"), ResponsePeople.class);
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void postWithUrlAndBeanParamTest() throws Exception {
        People people = new People();
        people.setAge(30);
        people.setHeight(1.73);

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).post(host + "/sentPost?name=中文名"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(30), responsePeople.getAge());
        Assert.assertEquals(new Double(1.73), responsePeople.getHeight());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void restPostWithUrlAndBeanParamTest() throws Exception {
        People people = new People();
        people.setAge(30);
        people.setHeight(1.73);

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).restPost(host + "/restPost?name=中文名"), ResponsePeople.class);
        Assert.assertEquals(new Integer(30), responsePeople.getAge());
        Assert.assertEquals(new Double(1.73), responsePeople.getHeight());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void postWithUrlAndBeanParamTestWithURLEncode() throws Exception {
        People people = new People();
        people.setName("1+1");

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).post(host + "/sentPost"), ResponsePeople.class);
        Assert.assertEquals("1+1", responsePeople.getName());
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void restPostWithUrlAndBeanParamTestWithURLEncode() throws Exception {
        People people = new People();
        people.setName("中文名");

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).restPost(host + "/restPost"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test(expected = RuntimeException.class)
    public void restPostWithUrlAndBeanWithFileParamTest() throws Exception {
        People people = new People();
        people.setAge(30);
        people.setHeight(1.73);
        people.setAvatar(new File(HttpApiTest.class.getResource("/media/g.gif").getFile()));
        JSON.parseObject(Api().param(people).restPost(host + "/restPost?name=中文名"), ResponsePeople.class);
    }

    @Test
    public void postWithUrlAndBeanWithFileParamTest() throws Exception {

        People people = new People();
        people.setAge(30);
        people.setHeight(1.73);
        people.setAvatar(new File(HttpApiTest.class.getResource("/media/g.gif").getFile()));

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).post(host + "/sentPost?name=中文名"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(30), responsePeople.getAge());
        Assert.assertEquals(new Double(1.73), responsePeople.getHeight());
        Assert.assertEquals(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(people.getAvatar())), responsePeople.getAvatar());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getArticle());
    }

    @Test
    public void mypostWithUrlAndBeanWithFileParamTest() throws Exception {

        People people = new People();
        people.setAvatar(new File(HttpApiTest.class.getResource("/media/g.gif").getFile()));

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).post(host + "/sentPost"), ResponsePeople.class);
        Assert.assertEquals(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(people.getAvatar())), responsePeople.getAvatar());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getArticle());
    }

    @Test
    public void postWithUrlAndBeanWithTwoFileParamTest() throws Exception {

        People people = new People();
        people.setAge(30);
        people.setHeight(1.73);
        people.setAvatar(new File(HttpApiTest.class.getResource("/media/g.gif").getFile()));
        people.setGallery(new File(HttpApiTest.class.getResource("/media/j.jpg").getFile()));

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).post(host + "/sentPost?name=中文名"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(30), responsePeople.getAge());
        Assert.assertEquals(new Double(1.73), responsePeople.getHeight());
        Assert.assertEquals(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(people.getAvatar())), responsePeople.getAvatar());
        Assert.assertEquals(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(people.getGallery())), responsePeople.getGallery());
        Assert.assertNull(responsePeople.getArticle());
    }

    @Test
    public void postWithUrlAndBeanWithThreeFileParamTest() throws Exception {

        People people = new People();
        people.setName("中文名");
        people.setAge(30);
        people.setHeight(1.73);
        people.setAvatar(new File(HttpApiTest.class.getResource("/media/g.gif").getFile()));
        people.setGallery(new File(HttpApiTest.class.getResource("/media/j.jpg").getFile()));
        people.setArticle(new File(HttpApiTest.class.getResource("/media/myarticle.txt").getFile()));

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).post(host + "/sentPost"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(30), responsePeople.getAge());
        Assert.assertEquals(new Double(1.73), responsePeople.getHeight());
        Assert.assertEquals(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(people.getAvatar())), responsePeople.getAvatar());
        Assert.assertEquals(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(people.getGallery())), responsePeople.getGallery());
        Assert.assertEquals(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(people.getArticle())), responsePeople.getArticle());
    }

    @Test
    public void get404() throws Exception {
        String url404 = "http://www.example.org/404";
        try {
            Api().post(url404);
        } catch (StatusCodeNot200Exception e) {
            Assert.assertEquals(404, e.getStatusCode());
            Assert.assertEquals(url404, e.getUrl());
            Assert.assertTrue(e.getParams() == null || e.getParams().size() == 0);
        }
    }

    @Test(expected = IOException.class)
    public void postIoExceptionWithFile() throws Exception {

        People people = new People();
        people.setName("中文名");
        people.setAge(30);
        people.setHeight(1.73);
        people.setAvatar(new File(HttpApiTest.class.getResource("/media/g.gif").getFile()));

        Api().param(people).post("http://xxx.abc");
    }

    @Test(expected = IOException.class)
    public void postIoException() throws Exception {
        Api().post("http://xxx.abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCallHeaderTwice() {
        Api().header(new HashMap<String, String>()).header(new HashMap<String, String>());
    }

    @Test
    public void testHeader() throws IOException {
        Map<String, String> header = new HashMap<String, String>();
        header.put("myHeader1", "myHeader1");
        header.put("myHeader2", "myHeader2");
        Assert.assertEquals(header, JSON.parseObject(Api().header(header).get(host + "/sentGetWithHeader"), HashMap.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCallParamTwice() {
        Api().param(new Object()).param(new Object());
    }

    @Test
    public void testSetLogId() throws IOException {
        String uuid = UUID.randomUUID().toString();
        Assert.assertEquals(uuid, Api().logId(uuid).get(host + "/forTestSetLogId"));
    }

    @Test
    public void testRedirect() throws IOException {
        Assert.assertEquals("ok", Api().get(host + "/forTestRedirect"));
    }


}
