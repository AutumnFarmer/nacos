# 配置 OrbStack 镜像加速

由于网络原因，你的 K8s 集群无法从 Docker Hub 拉取镜像（`ImagePullBackOff`）。我们需要配置国内镜像源。

## 步骤

1.  打开 **OrbStack** 应用。
2.  点击顶部菜单栏的 **File** -> **Settings...** (或按 `Cmd + ,`)。
3.  点击左侧的 **Docker** 选项卡。
4.  在 **Registry mirrors** 区域，添加以下镜像地址：
    *   `https://docker.m.daocloud.io`
    *   `https://huecker.io`
5.  点击 **Apply** 或关闭窗口（OrbStack 通常会自动保存并生效）。

## 验证
配置完成后，回到终端，删除并重新创建 Pod：

```bash
kubectl delete pod nginx
kubectl run nginx --image=nginx
```
