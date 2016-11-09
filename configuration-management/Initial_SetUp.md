#### Initial Set Up
The idea here is to spin up a cluster from scratch. The hosts are now represented by docker containers.
Please see the initial set up design [here](images/Initial_Setup_Docker_CentOS.png)

* Since this is a virtual end to end design, we make use of dockers and it has its own advantages. We are not building any VMs that has everything pre-configured. We spin up containers instead, and build a system from scratch.

* Once you spin up the containers, we configure the system using ansible. Going forward, let us visualise containers as actual host physical machines. The first step is to run CENTOS in each of these machines.

* Although Ansible transports through SSH, we are going to use docker connectors, as the containers have no ssh-server running in it.


##### Why we went for docker containers?
* Easy and flexible
* Portable anywhere
* Easy to switch to VMs, or cloud seamlessly
* Easy to test

##### Prerequisites
If you are trying out this project in your local machine, it is better you are using Linux/Mac machines. Or else, you may have to configure VMs. The machine should have:

* [A running docker](https://docs.docker.com/engine/installation/)
* [Ansible installed](AnsibleInstallation.md)


##### Spin 3 containers

To list the containers:

```
docker ps
```

To pull docker centos:6

```
docker pull centos:6
```

Images will now consist of centos:6

```
dokcer images
```

Spin 3 containers running CentOS, in sleeping state

```
docker run -d --name node01.afsalthaj.com --hostname node01.afsalthaj.com centos:6 /bin/sleep infinity

docker run -d --name node02.afsalthaj.com --hostname node02.afsalthaj.com centos:6 /bin/sleep infinity

docker run -d --name node03.afsalthaj.com --hostname node03.afsalthaj.com centos:6 /bin/sleep infinity

docker run -d --name node03.afsalthaj.com --hostname node04.afsalthaj.com centos:6 /bin/sleep infinity

docker run -d --name node03.afsalthaj.com --hostname node05.afsalthaj.com centos:6 /bin/sleep infinity

```

Assume here, that node 1, node 2 and node 3 are 3 different physical machines.


Please refer to [ansible](ansible) [inventory](inventory) that list down the hosts.
