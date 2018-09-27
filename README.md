# Introduction to Kubernetes

This repository contains some sample services, resource definitions and scripts used to demonstrate a small subset of what Kubernetes can do.

## Local requirements

In order to reproduce the examples demonstrated, you will need to have the following:

- bash (Windows users, install [Git for Windows](https://git-scm.com/download/win))
- curl
- Access to a Kubernetes cluster
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- [helm](https://docs.helm.sh/using_helm/#installing-helm) - for installing nginx/cert-manager (external web access w/Let's Encrypt)

## Directory structure

```bash
.
├── 01-k8s                     # Kubernetes definitions for deploying the demo services
├── 02-scripts                 # Utility scripts for managing the cluster and teardown/setup of a fresh cluster.
├── 03-presentation            # The presentation held in the video introduction
├── customer-service           # .NET Core: Manages customers
├── dashboard                  # Node: Displays customers and carts (AngularJS)
├── shopping-cart-service      # Java: Manages carts for customers
└── simple-service             # Go: A simple service that returns a counter value
```

## Slides

The slides used in the presentation is [available as a PDF](./03-presentation/presentation.pdf).
