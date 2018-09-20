#!/bin/bash
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep payex-admin | awk '{print $1}')