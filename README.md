# Api统一异常处理   

所有业务异常统一使用ApiException抛出，并根据业务错误编码释义详细信息

```
ApiException          Api统一异常
ApiExceptionHandler   异常处理器
Assertion             断言并抛出异常
NotFoundEndpoint      无法找到Api时的处理器，覆盖spring boot默认的/error页面
```
## 依赖项目
cloud-starter-i18n


## 集成到自已的项目时添加依赖项  

```pom
<dependency>
  <groupId>com.minlia.cloud.starter</groupId>
  <artifactId>cloud-starter-exception</artifactId>
  <version>2.0.0.RELEASE</version>
</dependency>
```



