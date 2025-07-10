# xxljob-autoregister

A job auto-register project for XXL-JOB client. Automatically registers job handlers with the XXL-JOB admin upon
application startup, simplifying job management and deployment.

## 1.`Example`

[`xxljob-autoregister-test`](https://github.com/photowey/xxljob-autoregister/tree/main/xxljob-autoregister-test)

> // @see `io.github.photowey.xxljob.autoregister.web.handler.HelloHandler`

## 2.`Usage`

### 2.1.`Dependency`

Add this to your `pom.xml`

> 1.`Maven center`
>
> [ [`central.sonatype.com`] ](https://central.sonatype.com)Unsupported now.
>
> 2.`Local`
>
> ```shell
> $ git clone https://github.com/photowey/xxljob-autoregister.git
> $ cd xxljob-autoregister
> $ make deploy
> ```
>
> 

```xml
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
    <type>pom</type>
</dependency>
```

### 2.2.`APIs`

#### 2.2.1.`@XxlJob + @AutoJob`

```java
// @XxlJob + @AutoJob 
@XxlJob("io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#mixes")
@AutoJob(
    // Ignore @Job
    base = @AutoJob.Base(
        description = "mixes",
        author = "photowey",
        email = "photowey@gmail.com"
    ),
    schedule = @AutoJob.Schedule(
        scheduleType = RegisterDictionary.ScheduleType.CRON,
        scheduleConf = "0/10 * * * * ? *"
    ),
    advanced = @AutoJob.Advanced(
        routeStrategy = RegisterDictionary.RouteStrategy.FIRST,
        misfireStrategy = RegisterDictionary.MisfireStrategy.DO_NOTHING,
        blockStrategy = RegisterDictionary.BlockStrategy.SERIAL_EXECUTION,
        executorTimeout = 0,
        executorFailRetryCount = 0
    )
)
public void mixes() {
    log.info("Hello mixes");
}
```

#### 2.2.2.`@AutoJob`

```java
@AutoJob(
    job = @AutoJob.Job(
        value = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#proxy
        enabled = true
    ),
    // ...
)
public void proxy() {
    log.info("Hello proxy");
}
```

#### 2.2.3.`Auto start`

```java
@AutoJob(
    job = @AutoJob.Job(
        value = "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#autoScheduleTask",
        enabled = true
    ),
    // ...
    
    // Auto start
    triggerStatus = 1
)
public void autoScheduleTask() {
    log.info("Hello autoScheduleTask");
}
```

#### 2.2.4.`SpEL`

```java
@AutoJob(
    job = @AutoJob.Job(
        value = "${local.config.job.handler}",
        enabled = true
    ),
    base = @AutoJob.Base(
        description = "spelScheduleTask",
        author = "${local.config.job.base.author}",
        email = "${local.config.job.base.email}"
    ),
    schedule = @AutoJob.Schedule(
        scheduleType = RegisterDictionary.ScheduleType.CRON,
        scheduleConf = "${local.config.job.schedule.cron}"
    ),
    task = @AutoJob.Task(
        handler = "${local.config.job.handler}",
        arguments = "xxljob://spel?cron=${local.config.job.schedule.cron}"
    ),
    // ...
    triggerStatus = 1
)
public void spel() {
    log.info("Hello spel");
}
```

### 2.3.`Configuration`

```yml
xxljob:
  automatic:
    register:
      admin:
        address: "http://127.0.0.1:18080/admin"
        access-token: "${ACCESS_TOKEN:default_token}"

      authentication:
        username: "${ADMIN_USERNAME:admin}"
        password: "${ADMIN_PASSWORD:123456}"

      executor:
        timeout: 30
        appname: "${spring.application.name}"
        address: ""
        ip: "127.0.0.1"
        port: 9999
        log-path: "/tmp/logs/xxljob/jobhandler"
        log-retention-days: 7

      cache:
        key: "xxljob:autoregister:authentication:global:cookie"
        expire-seconds: 3600

      lock:
        key: "xxljob:autoregister:authentication:global:lock"

      group:
        appname: "${spring.application.name}"
        # WARN: data too long `title` ...
        title: "Autoregister"
        address-type: 0
        # addressList:

local:
  config:
    job:
      handler: "io.github.photowey.xxljob.autoregister.web.handler.HelloHandler#spelScheduleTask"
      base:
        author: "photowey"
        email: "photowey@gmail.com"
      schedule:
        cron: "0/10 * * * * ? *"

```



## 3.`API`

#### 3.1.`Http`

#### 3.1.1.`API`

```xml
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-http-api</artifactId>
</dependency>
```

#### 3.1.2.`RestTemplate`

```xml

<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-http-rest-template</artifactId>
</dependency>
```

#### 3.1.2.`...`

> Extend this yourself.



### 3.2.`DistributedLock`

#### 3.2.1.`API`

```xml
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-lock-api</artifactId>
</dependency>
```

#### 3.2.2.`ReentrantLock`

```xml
<!-- In JVM -->
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-lock-reentrantLock</artifactId>
</dependency>
```

#### 3.2.3.`Redisson`

```xml
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-lock-redisson</artifactId>
</dependency>
```

### 3.2.4.`...`

> Extend this yourself.

### 3.3.`Storage`

#### 3.3.1.`API`

```xml
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-storage-api</artifactId>
</dependency>
```

#### 3.3.2.`Local`

```xml
<!-- Default -->
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-storage-caffeine</artifactId>
    <version>${project.version}</version>
</dependency>
```

#### 3.3.3.`Redis`

```xml
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>xxljob-autoregister-storage-redis</artifactId>
</dependency>
```

#### 3.3.4.`...`

> Extend this yourself.

