# Biggudeta-Kyokyoku

This project is made for personal learning purpose, and to show case an instance of an end to end solution in a Big Data environment. Although the solution discussed in this project can be specific to a problem, it covers some of the basics from an end to end solution perspective. A basic level knowledge on Scala-Spark (py-spark is also enough), Linux, Docker, Virtual Machines, Ansible etc can be handy. Before we dive into the solution, let us discuss a few things about the tecnology stack used. It is just a brain dump and I don't intend to explain each of these technologies.

## Unix to GNU to Linux Kernel
A history of Linux: It started when Unix was invented by Dennis Ritchie, as a solution to bringing in multi-user concept into Operating Systems. While Unix started becoming proprietary, Richard Stallman came up with GNU (GNU is Not Unix) while failing to come up with a proper kernel system. Later on, Linus Torvalds came up with a super-cool now-highly-used Linux kernel, and Linus colloborated with Stallman to incorporate the highly powerful Linux Kernel to work with GNU - to become the so called LINUX - NOW THE OPEN SOURCE. Distributions include REDHAT, Google Android, Ubuntu, DSL (Damn Small Linux) etc. In 1994, there was only Linux, and from then distributions came into existence.


#### Which distribution to use?
Depends on what you want to do with your computer. Every distribution is specific to the need. For instance, if you want enterprise level support, go for Redhat, if you want really fast small light weight Linux, go for DSL and so on. In our example, we will centOS.

#### A key note on Open Source Licensing
First of all, Open Source software is not a free software. Open source software vendors are getting paid here. So is Linux too.

## Virtual Machines
If you need to have virtual machines running over the host, you need to have a Hypervisor that manages the kernel resources to each VM instance. On top of Hypervisor, you have various virtual machines that can have separate OS, separate /usr/lib, specific configurations and capabilties. All of them have a pre-shared set of kernel resources managed through hypervisor. This has its overhead. Various virtual boxes are available in industry for you to have this set up in your environment. It is also important you know about **vagrant**, that it is a command line utility for managing the life cycle of virtual machines. It is able to provide a portable and reproducible development environment using virtual machines.

## Docker

Docker works on the concept of containerisation. So how does it differ from VMs? Firstly, It is not a replacement of Virtual Boxes and Virtual Machines. Secondly, it can solve a lot of problems that VMs used to solve, with a less overhead. It spawns containers directly on top of Linux Kernel. Multiple containers share the resources of host kernel. Hence they can manage the utilities of actual Linux Kernel more efficiently. Also, having a container for each of your process or applications makes it easy for you to manage things. It is faster, and probably easier to reason about. You can spin up as many containers as you want; each one is spinned up through different docker images, making use of the core host linux kernel. You invoke docker image through a docker run command. The command is turned on from the client, communicating the docker server that in turn spin up the container for that application - as simple as that. The whole notion of dockerisation becomes a real value add in cluster computing. You have client with a docker swam/mesos (manager) that simply manages containers in different nodes through its own docker server. And you spawn as many containers as you want and is really scalable.

#### Host and docker container
The host knows (ps -ef) what is running in each of its containers. You can notice that, process ID of the container process through host, is different from that you see for the same process when you are inside the container. However, you can't see any information about anything that runs on the host when you are inside the container and do a ps -ef. This explains the abstraction. This explains what is a container. Yes! It is a container that just handles only your application, only your process, but with the required resources of host kernel.

#### Anti docker patterns
It is important to know, that, it is better, and is always better if you are not mutating your container. Your container should ideally be handling just one process. This is hard to satisfy when you are actually applying dockerisation in Big Data environment. Assume that you need 5 nodes as 5 containers. Each container should have a data node, task tracker etc as the essential daemons of Big Data. However this isn't ideal. Here, your first spawn the container and then you keep adding the processes. This is just mutating the container, and wouldn't be able to track its previous state, and can be error prone. However, it is just our assumption and isn't theoretically written anywhere. This simply means, it is a pattern that enterprise can follow and you needn't be surprised of it. In this project, I am going with this pattern as it is a straight forward easy implementation.


## Ansible
Ansible is an IT automation tool, mainly intended to configure systems, deploy software and manage highly intensive advanced IT management tasks such as continuous deployment. Please note that, it is just a configuration management systems that may work with other authentication management systems such as Kerberos, LDAP and so forth. I thought of going with Ansible, since I liked the simplicity. If you define the inventory and the set of operations in yml file, you are almost there.
In inventory, you could define how many nodes has to be spawned, and assign which nodes should be master nodes, which node can be namenode, which node can be secondary namenode, which ones can be datanodes and task tracker. Then in yml file, you can configure for each node about the containers you need to spawn. In short, each node can be a container with multiple processes running in it. And it is easy to test too, and even change the system from docker to actual Virtual Machines. All you need to do is change the yml file from docker processes to actual VM machines, and change the inventory to the VM host names. VM can be more closely related to an actual cluster - a set of VMs that can act as multiple nodes. However, let us go with containerisation for easy testability and the fact that it can be changed to VM based at any time.

## Spark-Scala
https://github.com/afsalthaj/supaku-sukara

This is my personal repo and exists for the personal purpose of learning scala, functional programming and Big Data spark, with the intend to share it with my friends later on.

## Reference
https://github.com/Ranjandas/hadoop-yarn
