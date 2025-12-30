# K8s 配置管理：ConfigMap

我们遇到了把应用搬进 K8s 的第一个大坑：**配置冲突**。

## 1. 为什么 `localhost` 会失效？

你的 `application.yml` 里面写着：
```yaml
nacos:
  server-addr: 127.0.0.1:8848
```

*   **在本地运行时**: `127.0.0.1` 是你的电脑。你的 Nacos Server 也就跑在你的电脑上，所以能通。
*   **在 K8s Pod 里时**: `127.0.0.1` 是 **Pod 自己**。容器以为你在连它自己，但它里面并没有跑 Nacos Server，所以报错。

## 2. 笨办法 vs 聪明办法

*   ❌ **笨办法**: 修改代码里的 `application.yml`，改成 `nacos-server`，然后重新打包 Docker 镜像。
    *   缺点：每次改 IP 都要重新构建镜像，太慢了！测试环境和生产环境 IP 不一样怎么办？
*   ✅ **聪明办法 (ConfigMap)**: 镜像里的配置**留空**或者**写默认值**。在 K8s 部署时，用一张“外挂配置单”覆盖掉镜像里的配置。

## 3. ConfigMap 是什么？

ConfigMap 就是 K8s 里的“记事本”。你把配置写在里面，K8s 会在启动 Pod 时，把这个记事本的内容：
1.  **挂载为文件**: 覆盖掉容器里的 `src/main/resources/application.yml`。
2.  **注入环境变量**: 比如 `SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR=nacos-service`。

## 4. 实战目标

我们将创建一个 `nacos-config.yaml`，告诉 K8s：
> "不管镜像里写什么，启动时请把 `server-addr` 强制改为 `nacos-headless-service`。"
