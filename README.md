# Api统一异常处理   

所有业务异常统一使用ApiException抛出，并根据业务错误编码释义详细信息

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.minlia.cloud.starter/cloud-starter-exception/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.minlia.cloud.starter/cloud-starter-exception/) 
[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt) 
[![Build Status](https://travis-ci.org/minlia-projects/cloud-starter-exception.svg?branch=master)](https://travis-ci.org/minlia-projects/cloud-starter-exception)
[![Waffle.io - Columns and their card count](https://badge.waffle.io/minlia-projects/cloud-starter-exception.svg?columns=all)](https://waffle.io/minlia-projects/cloud-starter-exception)


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



