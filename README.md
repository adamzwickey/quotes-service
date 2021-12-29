# quotes-service
This service is a spring boot application responsible for providing up to date company and ticker/quote information. 

```bash
export REGISTRY=gcr.io/abz-perm 
make -e image-repo=$REGISTRY 
```

*** This build produces a native image. It will take a few minutes.  Spring Native compilation requires A LOT of memory for your docker engine -- 8GB or more is recommended.


