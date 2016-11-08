#### Various Configuration Management Systems
* **CF ENGINE**; It is in `C`
* **Puppet**; Ruby; With a Ruby DSL for end user.
* **Chef**; Ruby; with Ruby file for end user.
* **Salt Stack**; Python; with YAML file for the end user
* **Ansible**; Python; with YAML file for the end user.

#### Architecture Comparison between various configuration management systems - a brain dump

* **Puppet/Chef**: Puppet has  a puppet master, with various daemons running in various nodes. Configuration was simpler using puppet. However, the orchestration turned out to be a little complicated because of its pull mechanism. The `pull` architecture is followed for chef too. Daemons pulls the latest configuration changes from the master once in 30 minutes. It uses active MQ and such complicated queue mechanism for orchestration.

* **Salt Stack** Salt Stack has a manager and various nodes. The orchestration became simpler when compared to Puppet/Chef. However, this also follows pull model.


* **Ansible** Follows push model, easy to use, uses ssh-protocol for transport. As you know, many linux systems has SSH-SERVER running, and hence additional set up is not needed while using ansible. Install ansible in the master (or any node) and you are ready to configure a multi node system. Push model also ensures your configuration is set up with no latency.

* In this project, we are going to set up an infrastructure using Ansible. While we do this, it is important to know about the **negatives of ansible** too. While ssh-protocol seem to be useful, spinning up multiple threads of ssh from a server to various nodes can be a **overhead**. The **testability** of ansible is less when compared to puppet. It has it's inherent problem of `python` that **it isn't type safe**. Puppet can catch errors at compile time for your automation code and it is one of it's major advantages over `ansible`. However, this can be compensated in ansible by bringing in standards for your automation code.
