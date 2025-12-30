# Kubernetes Learning Roadmap

This roadmap is designed to take you from zero to deploying your Nacos microservices on Kubernetes.

## Phase 1: Environment & Hello World 🏁
- [ ] **Install Tools**: `kubectl` + Local Cluster (OrbStack/Minikube).
- [ ] **First Command**: `kubectl run` to start a simple Nginx pod.
- [ ] **Inspection**: `kubectl get`, `kubectl describe`, `kubectl logs`.

## Phase 2: Core Concepts (Stateless Apps) 🧱
- [ ] **Pod**: The atomic unit of K8s. Writing your first YAML.
- [ ] **Deployment**: Managing replicas, updates, and rollbacks.
- [ ] **Service**: Exposing your application (ClusterIP, NodePort).
- [ ] **Namespace**: Isolating resources.

## Phase 3: Configuration & Storage 💾
- [ ] **ConfigMap**: Externalizing configuration (e.g., `application.yml`).
- [ ] **Secret**: Handling sensitive data.
- [ ] **Volume**: Preserving data (PersistentVolume).

## Phase 4: Project - Nacos on K8s 🚀
- [ ] **Deploy Nacos**: Running a single-node Nacos in K8s.
- [ ] **Deploy Provider**: Dockerizing and deploying the Spring Boot Provider.
- [ ] **Deploy Gateway**: Dockerizing and deploying the Spring Cloud Gateway.
- [ ] **Service Discovery**: Verifying they can talk to each other inside K8s.

## Phase 5: Advanced (Optional) 🎓
- [ ] **Ingress**: Managing external access.
- [ ] **Helm**: Package management.
- [ ] **Observability**: Prometheus & Grafana.
