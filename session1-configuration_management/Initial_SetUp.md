#### Initial Set Up
The idea here is to spin up a cluster from scratch. The hosts are now represented by docker containers.
Please see the initial set up design [here](images/inital_Setup_Docker_CentOS.png)

* Since this is a virtual end to end design, we make use of dockers and it has its own advantages. We are not building any VMs that has everything pre-configured. We spin up containers instead, and build a system from scratch.

* Once you spin up the containers, we configure the system using ansible. Going forward, let us visualise containers as actual host physical machines. The first step is to run CENTOS in each of these machines.

* Although Ansible transports through SSH, we are going to use docker connectors, as the containers have nothing in it intially.


##### Why we went for docker containers?
* Easy and flexible
* Portable anywhere
* Easy to switch to VMs, or cloud seamlessly
* Works anywhere.

##### Prerequisites
If you are trying out this project in your local machine, it is better you are using Linux/Mac machines. Or else, you may have to configure VMs. The machine should have:

* [A running docker](https://docs.docker.com/engine/installation/)
* [Ansible installed](AnsibleInstallation.md)
