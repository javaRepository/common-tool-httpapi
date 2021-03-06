# common-tool-httpapi------java工具类

简易基于http的api请求客户端。不依赖其他库直接使用HttpURLConnection，支持keepalives。支持map和pojo作为请求参数。支持设置header。默认支持tool的_logId日志系统。

#Quick start
1.添加tool的github的repository
```xml
<repositories>
    <repository>
        <id>tool-maven-repository</id>
        <url>https://raw.github.com/javaRepository/maven-repository/master/releases</url>
    </repository>
</repositories>
```
2.添加依赖
```xml
<dependency>
    <groupId>common.tool</groupId>
    <artifactId>tool-httpapi</artifactId>
    <version>0.0.6</version>
</dependency>
```
2.添加import
```xml
import static HttpApi.Api;
```
3.使用
```java
Api().get("http://www.example.org");
or
Api().post("http://www.example.org");
```
##方法介绍
###header方法。当发送的请求需要带上header:
```java
Map<String, String> header = new HashMap<String, String>();
header.put("Authorization", "Basic xxx");
Api().header(header).post("http://www.example.org");
```
###param方法。当请求需要带上参数:   
带pojo参数：
```java
public class User{
  private String name;
  ...
}
...
User user = new User();
user.setName("Andy");
Api().param(user).post("http://www.example.org");
```
带Map参数（推荐：Map&lt;String, Object&gt;）：
```java
//普通参数
Map<String, Object> user = new HashMap<>();
user.put("user", "Andy");
Api().param(user).post("http://www.example.org");
```
```java
//发文件
Map<String, Object> uploadImgParam = new HashMap<>();
uploadImgParam.put("img", new File("~/photo.jpg"));
uploadImgParam.put("fileName", "myphoto");
Api().param(uploadImgParam).post("http:/www.example.org");
```
#关于tool的_logId的说明
get/post方法默认会添加一个_logId参数到你的header
_logId参数来自于MDC,当需要别的线程发送又希望请求带上_logId, 可以手动设置_logId:
```java
Api().logId("a_random_logId").get("http://www.example.org");
```
#异常：StatusCodeNot200Exception
当请求返回的status code返回的码不在大于等于200，小于400的时候，会抛出一个自定义的运行时异常:StatusCodeNot200Exception
