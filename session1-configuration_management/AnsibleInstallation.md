#### Linux Python Installers
* easy-install is a tool to install python packages
* A tip: When you install softwares, install it to user directory instead of mutating the machine state.
* Similar to easy-install, we have pip that installs python packages to home directory.

#### Instructions to install pip
**Step 1:**
```
curl -LO https://bootstrap.pypa.io/get-pip.py
```
**Step 2:**
```
python get-pip.py --user
```
**Step 3:**

Verify the installation. Please note that the installation is in home directory, and not in `/Library`
```
ls ~/Library/Python/2.7/bin
```
**Step 4:**  

Add python bins to path
```
export PATH = $PATH:~/Library/Python/2.7/bin
```

#### Install Ansible

**Step 1:**

Install Ansible through pip. It is installed from Python's latest Package Index.
```
pip install ansible --user
```

**Step 2:**

Make sure python bins are added to your paths.
Step 3: Verify the installation
```
ansible --version

ansible 2.2.0.0
  config file =
  configured module search path = Default w/o overrides
```
