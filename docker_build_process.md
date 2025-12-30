# 做镜像分两步：炒菜与装盘

要把你的 Java 代码变成 K8s 能跑的镜像，只需要 **1 个配置文件** 和 **2 条核心指令**。

## 1. 核心配置文件：`Dockerfile`

这就像是**装盘说明书**。你需要告诉 Docker：用什么盘子？装什么菜？怎么吃？

我们在 `nacos-provider` 目录下创建了这个文件：

```dockerfile
# 1. 选盘子（基础镜像）：找一个装好 Java 17 的 Linux 系统
FROM eclipse-temurin:17-jre

# 2. 装菜（拷贝文件）：把你炒好的 jar 包放进容器里的 /app.jar
COPY target/nacos-provider-0.0.1-SNAPSHOT.jar app.jar

# 3. 怎么吃（启动命令）：告诉 K8s 启动时运行什么命令
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 2. 两条核心指令

### 第一步：炒菜 (Maven Build)
你需要先把代码编译成可执行的 jar 包。这是 Java 开发的标准操作。
*   **指令**: `mvn clean package`
*   **产物**: 生成 `target/nacos-provider-0.0.1-SNAPSHOT.jar`

### 第二步：装盘 (Docker Build)
按照 Dockerfile 的说明书，把 jar 包封进镜像里。
*   **指令**: `docker build -t 镜像名字:版本号 .`
    *   `-t`: 给镜像起个名 (tag)
    *   `.`: 说明书 (Dockerfile) 在当前目录
*   **产物**: 生成一个 Docker Image，存放在本地镜像仓库。

---

## 接下来我们演示 `nacos-consumer`
我们用 `nacos-consumer` 再做一遍，这次请你观察每一个步骤。
