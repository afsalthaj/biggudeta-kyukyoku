### Ansible Playbook

Instead of running adhoc commands to set up the machine from the scratch, let us have a set of yml files, as our automation code to set up the system based on the [inventory](inventory)

Please refer to [site.yml](site.yml) for further information.

## How to run ansible Playbook
```
ansible-playbook -i inventory site.yml -c docker
```

## How to create [roles](roles) directory
```
ansible-galaxy init --offline openssh
```

You are now ready to incorporate roles into site.yml