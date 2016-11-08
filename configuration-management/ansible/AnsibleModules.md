## Sample Commands

Command to run uname -a commands through Shell Module in all hosts listed in inventory
```
ansible all -i inventory -m shell -a "uname -a" -c docker

```

### Module shell

Other shell Module functionalities
* uname -a : Gives the details of the kernel
* hostname
* df -h
* ifconfig

### Module Yum
```
yum install httpd

```

It is better not to use shell module, so that Idempotence is not lost. Using shell module doesn't ensure checking the existing state of the machine. The command which is running by shell module is basically not under the control of configuration management.

```
ansible all -i inventory -m yum -a "name=dialog state=present" -c docker

```  

If you try to use the command to install dialog again into the same set of nodes, it wont re-install. Dialog will be installed only if they are `absent`. That is, `state=absent` makes sure that `dialog` is cleared

### Module Service

An example of a service is httpd. We know the containers didn't have anything at all when they were spinned up, so is the case for httpd too.

Enter the container:
```
docker exec -it node01.afsalthaj.com /bin/bash
```
We can see there is nothing in /etc/init.d, this implies there is no ssh in container.

After coming back to your local machine, to the location where inventory file is present, execute the following ansible command to install ssh-server in all nodes

```
ansible all -i inventory -m yum -a "name=openssh-server" state="present" -c docker
```

You can see, ssh service is stopped in your container.

We will use the same Module Service to run the ssh in the container.

```
ansible all -i inventory -m service -a "name=sshd state=running" -c docker
```

Also, you may not be able to see the logs, since there is no syslog.

So, let us install syslog using yum module

```
ansible all -i inventory -m yum -a "name=syslog state=present" -c docker

ansible all -i inventory -m service -a "name=rsyslog state=running" -c docker
```

You can see the logs in /var/log/messages for all services.

*This is just an overview of adhoc ansible commands*
