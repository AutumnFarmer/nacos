# Spring Cloud + Kubernetes：双重架构解析

你的自我纠正非常**精准**！这正是 "Cloud Native" (K8s 原生) 和 "Spring Cloud" (应用层框架) 结合时最容易混淆的地方。

结论是：**Gateway 对外使用 K8s Service (VIP)，内部微服务间调用继续使用 Nacos。**

## 1. 流量入口：Gateway (混合模式)

*   **对外 (K8s 层)**:
    *   因为外部用户（浏览器/手机）不知道 Nacos 的存在，也不可能连上 Nacos。
    *   所以 **Gateway 必须配置 K8s Service (Type=LoadBalancer)** 或者 **Ingress**。
    *   **作用**: 提供一个公网 IP/域名，把外部流量引入集群，打到 Gateway 的 Pod 上。

*   **对内 (应用层)**:
    *   Gateway 启动后，把自己的 Pod IP 注册到 Nacos。

## 2. 内部调用：Gateway -> Microservices (Nacos 模式)

当 Gateway 收到请求转发给 `nacos-provider`，或者 `consumer` 调用 `provider` 时：

*   **不走 K8s Service VIP**: 虽然你可以给 Provider 建一个 K8s Service，但 Spring Cloud Alibaba 默认不理会它。
*   **走 Nacos 注册发现**:
    1.  **注册**: `provider` Pod 启动，把自己的 **Pod IP** (比如 10.1.5.8) 告诉 Nacos。
    2.  **发现**: `consumer` 问 Nacos："谁是 provider？"
    3.  **直连**: Nacos 返回 `[10.1.5.8, 10.1.5.9]`。`consumer` 使用 Feign **直接** 发起 HTTP 请求到 `http://10.1.5.8:8080/hello`。

## 架构对比图

```mermaid
graph TD
    User((外部用户))
    
    subgraph Kubernetes Cluster
        LB[Service / Ingress <br> (VIP: 192.168.x.x)]
        
        subgraph "Pod: Gateway (x2)"
            GW1[Gateway Pod 1]
            GW2[Gateway Pod 2]
        end
        
        subgraph "Pod: Internal Services"
            P1[Provider Pod 1]
            P2[Provider Pod 2]
            C1[Consumer Pod]
        end
        
        Nacos[Nacos Server <br> (Pod or External)]
    end

    %% Flow 1: External Access (K8s Native)
    User --"1. 访问 VIP"--> LB
    LB --"2. 负载均衡"--> GW1 & GW2

    %% Flow 2: Registration (App Layer)
    GW1 & GW2 -.-"3. 注册 IP"--> Nacos
    P1 & P2 -.-"3. 注册 IP"--> Nacos

    %% Flow 3: Discovery & Call (Spring Cloud)
    GW1 --"4. 查询地址"--> Nacos
    GW1 --"5. 直连 Pod IP (不经过 Service)"--> P1
```

## 为什么不干脆全用 K8s Service？

你可能会问：“既然 K8s Service 也有负载均衡 (VIP)，为什么还要用 Nacos + Feign？”

1.  **更高级的治理**: Nacos/Feign 支持**权重路由**、**灰度发布**、**流量控制** (Sentinel)，这些是 K8s Service (主要做 4 层转发) 做不到的。
2.  **Java 生态惯性**: 你的代码已经写了 `@FeignClient`，迁移成本最低。

## 总结

*   **Gateway**: 是“双面人”。对外是 K8s Service 的后端，对内是 Nacos 的客户端。
*   **Consumer/Provider**: 在这个架构里，它们**不需要** K8s Service (VIP) 也能在内部互相工作（但为了运维排查方便，我们通常还是会配一个 ClusterIP Service，只是业务代码不用它）。
