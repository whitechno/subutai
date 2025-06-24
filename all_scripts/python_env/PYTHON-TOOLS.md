Python Tools
============
June 1, 2025

The right Python tool stack?
- pyenv: install and manage multiple versions of Python
  - <https://github.com/pyenv/pyenv>
- pipx: install and manage Python CLI packages
  - <https://github.com/pypa/pipx>
- uv: set and manage Python virtual environments
  - <https://github.com/astral-sh/uv>
- mypy: static type checker for Python
  - <https://mypy.readthedocs.io>

_This doc covers macOS/bash only._

brew upgrade log
----------------

- 2025-06-22
  - brew uninstall python@3.11
  - pyenv 2.6.0 -> 2.6.3
  - python@3.13 3.13.3_1 -> 3.13.5
  - python@3.12 3.12.10_1 -> 3.12.11

Python with Homebrew
--------------------

### Original macOS python:
```text
$ /usr/bin/python3 --version
Python 3.9.6
```

```shell
$ brew search python@3
==> Formulae
python@3.10    python@3.12 ✔  python@3.8     ipython        jython
python@3.11 ✔  python@3.13 ✔  python@3.9     bpython        cython
```

### python3.13
```shell
$ brew info python@3.13
==> python@3.13: stable 3.13.3 (bottled)
Installed
/usr/local/Cellar/python@3.13/3.13.3_1 (3,845 files, 67.9MB) *
  Poured from bottle using the formulae.brew.sh API on 2025-05-24 at 14:44:30
==> Dependencies
Build: pkgconf ✔
Required: mpdecimal ✔, openssl@3 ✔, sqlite ✔, xz ✔, expat ✔
==> Caveats
Python is installed as
  /usr/local/bin/python3

Unversioned symlinks `python`, `python-config`, `pip` etc. pointing to
`python3`, `python3-config`, `pip3` etc., respectively, are installed into
  /usr/local/opt/python@3.13/libexec/bin

You can install Python packages with
  pip3.13 install <package>
They will install into the site-package directory
  /usr/local/lib/python3.13/site-packages
```
```shell
$ which python3.13
/usr/local/bin/python3.13
```
```shell
$ which python3
/usr/local/bin/python3
```
```shell
$ python3.13 --version
Python 3.13.3
```

### python3.12
```shell
$ brew info python@3.12
==> python@3.12: stable 3.12.10 (bottled)
Installed
/usr/local/Cellar/python@3.12/3.12.10_1 (3,688 files, 64.9MB) *
  Poured from bottle using the formulae.brew.sh API on 2025-05-24 at 14:50:12
==> Dependencies
Build: pkgconf ✔
Required: mpdecimal ✔, openssl@3 ✔, sqlite ✔, xz ✔
==> Caveats
Python is installed as
  /usr/local/bin/python3.12

Unversioned and major-versioned symlinks `python`, `python3`, `python-config`, 
`python3-config`, `pip`, `pip3`, etc. pointing to
`python3.12`, `python3.12-config`, `pip3.12` etc., respectively, are installed into
  /usr/local/opt/python@3.12/libexec/bin
```
```shell
$ which python3.12
/usr/local/bin/python3.12
```
```shell
$ python3.12 --version
Python 3.12.10
```

Pyenv
-----

<https://github.com/pyenv/pyenv>

NOTE: Most Pyenv-provided Python releases are source releases and are built from
source as part of the installation (that's why you need Python build
dependencies preinstalled).

### pyenv install log

- 2025-06-24
  - 3.12.11


### Prerequisite: mac build environment
```shell
$ xcode-select --install
$ brew install openssl readline sqlite3 xz zlib tcl-tk@8 libb2
```

### Download Pyenv with Homebrew
```shell
$ brew install pyenv

$ brew info pyenv
==> pyenv: stable 2.6.0 (bottled), HEAD
Python version management
https://github.com/pyenv/pyenv
Installed
/usr/local/Cellar/pyenv/2.6.0 (1,325 files, 4.2MB) *
  Poured from bottle using the formulae.brew.sh API on 2025-06-01 at 14:12:36
==> Dependencies
Required: autoconf ✔, openssl@3 ✔, pkgconf ✔, readline ✔

$ which pyenv
/usr/local/bin/pyenv

$ pyenv --version
pyenv 2.6.0
```

### Set up a shell environment (bash)

Add the commands to `.bash_profile` by running the following in your terminal:
```shell
echo 'export PYENV_ROOT="$HOME/.pyenv"' >> ~/.bash_profile
echo '[[ -d $PYENV_ROOT/bin ]] && export PATH="$PYENV_ROOT/bin:$PATH"' >> ~/.bash_profile
echo 'eval "$(pyenv init - bash)"' >> ~/.bash_profile
```
Last command adds `$HOME/.pyenv/shims` to `PATH`. Check that pyenv's shims
directory is in your `PATH`:
```text
echo $PATH | grep --color=auto "$(pyenv root)/shims"
```

```text
Usage: eval "$(pyenv init [-|--path] [--no-push-path] [--no-rehash] [<shell>])"

  -               Initialize shims directory, print PYENV_SHELL variable, 
                  completions path and shell function
  --path          Print shims path
  --no-push-path  Do not push shim to the start of PATH if they're already there
  --no-rehash     Add no rehash command to output  
```

See [Advanced Configuration](
https://github.com/pyenv/pyenv/blob/master/README.md#advanced-configuration
)

### Uninstalling Pyenv

1. To disable Pyenv managing your Python versions, simply remove the
   `pyenv init` invocations from your shell startup configuration. This will
   remove the Pyenv shims directory from `PATH`, and future invocations like
   `python` will execute the system Python version, as it was before Pyenv.

   `pyenv` will still be accessible on the command line, but your Python apps
   won't be affected by version switching.

2. To completely uninstall Pyenv, remove all Pyenv configuration lines from your
   shell startup configuration, and then remove its root directory. This will
   delete all Python versions that were installed under the
   `$(pyenv root)/versions/` directory:
   ```
   rm -rf $(pyenv root)
   ```
   and then
   ```
   brew uninstall pyenv
   ```

### Using Pyenv

<https://github.com/pyenv/pyenv/blob/master/COMMANDS.md>

**help**
```text
$ pyenv root
/Users/owhite/.pyenv

$ pyenv help
Usage: pyenv <command> [<args>]

Some useful pyenv commands are:
   --version   Display the version of pyenv
   commands    List all available pyenv commands
   exec        Run an executable with the selected Python version
   global      Set or show the global Python version(s)
   help        Display help for a command
   hooks       List hook scripts for a given pyenv command
   init        Configure the shell environment for pyenv
   install     Install a Python version using python-build
   latest      Print the latest installed or known version with the given prefix
   local       Set or show the local application-specific Python version(s)
   prefix      Display prefixes for Python versions
   rehash      Rehash pyenv shims (run this after installing executables)
   root        Display the root directory where versions and shims are kept
   shell       Set or show the shell-specific Python version
   shims       List existing pyenv shims
   uninstall   Uninstall Python versions
   version     Show the current Python version(s) and its origin
   version-file   Detect the file that sets the current pyenv version
   version-name   Show the current Python version
   version-origin   Explain how the current Python version is set
   versions    List all Python versions available to pyenv
   whence      List all Python versions that contain the given executable
   which       Display the full path to an executable

See `pyenv help <command>' for information on a specific command.
For full documentation, see: https://github.com/pyenv/pyenv#readme
```

**Check system installation**
```text
$ pyenv versions
* system (set by /Users/owhite/.pyenv/version)

$ which python3
/Users/owhite/.pyenv/shims/python3

$ pyenv prefix
/usr/local

$ pyenv prefix system
/usr/local

# Displays the full path to the executable that pyenv 
# will invoke when you run the given command:
$ pyenv which python3
/usr/local/bin/python3

# python3.13 and python3.12 installed by Homebrew

$ pyenv which python3.13
/usr/local/bin/python3.13

$ python3 -V
Python 3.13.3

$ pyenv which python3.12
/usr/local/bin/python3.12

$ python3.12 -V
Python 3.12.10
```

**Install Python with Pyenv**
```text
# Displays the latest known version with the given prefix:
$ pyenv latest -k 3.13
3.13.3
$ pyenv latest -k 3.12
3.12.10
$ pyenv latest -k 3
3.13.3

# gives the list of all available versions:
$ pyenv install -l | less
... it is pretty long ...

$ pyenv install -l | grep "3\.12\."
  3.12.0
  3.12.1
  3.12.2
  3.12.3
  3.12.4
  3.12.5
  3.12.6
  3.12.7
  3.12.8
  3.12.9
  3.12.10

$ pyenv install 3.12
python-build: use openssl@3 from homebrew
python-build: use readline from homebrew
Downloading Python-3.12.10.tar.xz...
-> https://www.python.org/ftp/python/3.12.10/Python-3.12.10.tar.xz
Installing Python-3.12.10...
python-build: use tcl-tk from homebrew
python-build: use readline from homebrew
python-build: use zlib from xcode sdk
Installed Python-3.12.10 to /Users/owhite/.pyenv/versions/3.12.10


# Displays the latest installed version with the given prefix
$ pyenv latest 3
3.12.10
$ pyenv latest 3.12
3.12.10

$ ls -la $PYENV_ROOT/versions/
drwxr-xr-x  6 owhite  staff  192 Jun  1 17:40 3.12.10
```

**shell**
`pyenv shell` Sets a shell-specific Python version by setting the PYENV_VERSION
environment variable in your shell. This version overrides application-specific
versions and the global version.
```text
$ pyenv versions
* system (set by /Users/owhite/.pyenv/version)
  3.12.10

$ python3 --version
Python 3.13.3

# Sets a shell-specific Python version:
$ pyenv shell 3.12

$ pyenv versions
  system
* 3.12.10 (set by PYENV_VERSION environment variable)

$ python --version
Python 3.12.10

$ pyenv prefix
/Users/owhite/.pyenv/versions/3.12.10

# unset the shell version:
$ pyenv shell --unset
$ pyenv versions
* system (set by /Users/owhite/.pyenv/version)
  3.12.10
```

**global**
`pyenv global` Sets the global version of Python to be used in all shells by
writing the version name to the `~/.pyenv/version` file. This version can be
overridden by an application-specific `.python-version` file, or by setting the
`PYENV_VERSION` environment variable.

When run without a version number, `pyenv global` reports the currently
configured global version.
```text
$ pyenv global
system
```

**local**
`pyenv local` Sets a local application-specific Python version by writing the
version name to a `.python-version` file in the current directory. This version
overrides the global version, and can be overridden itself by setting the
`PYENV_VERSION` environment variable or with the `pyenv shell` command.
```text
$ pyenv versions
* system (set by /Users/owhite/.pyenv/version)
  3.12.10

$ pyenv local 3.12

$ pyenv versions
  system
* 3.12.10 (set by /Users/owhite/dev/whitechno-github/subutai/all_scripts/python_env/python_uv/Examples/.python-version)

$ cat .python-version 
3.12

$ python --version
Python 3.12.10

$ pyenv local --unset
$ pyenv versions
* system (set by /Users/owhite/.pyenv/version)
  3.12.10
```

pipx
----

Install and Run Python Applications in Isolated Environments.

<https://github.com/pypa/pipx>

[pipx](https://pypi.org/project/pipx/)

```text
$ brew install pipx
==> Caveats
Bash completion has been installed to:
  /usr/local/etc/bash_completion.d


$ brew info pipx
==> pipx: stable 1.7.1 (bottled), HEAD
Execute binaries from Python packages in isolated environments
https://pipx.pypa.io
Installed
/usr/local/Cellar/pipx/1.7.1_1 (155 files, 1020.6KB) *
  Poured from bottle using the formulae.brew.sh API on 2025-06-02 at 10:11:38
From: https://github.com/Homebrew/homebrew-core/blob/HEAD/Formula/p/pipx.rb
License: MIT
==> Dependencies
Required: python@3.13 ✔
```

Upgrade pipx with `brew update && brew upgrade pipx`.

```text
$ pipx -h
usage: pipx [-h] [--quiet] [--verbose] [--global] [--version]
            {install,install-all,uninject,inject,pin,unpin,upgrade,upgrade-all,
            upgrade-shared,uninstall,uninstall-all,reinstall,reinstall-all,
            list,interpreter,run,runpip,ensurepath,environment,completions} ...

Install and execute apps from Python packages.

Binaries can either be installed globally into isolated Virtual Environments
or run directly in a temporary Virtual Environment.

Virtual Environment location is /Users/owhite/.local/pipx/venvs.
Symlinks to apps are placed in /Users/owhite/.local/bin.

Get help for commands with pipx COMMAND --help
```

```text
# Ensure directories necessary for pipx operation 
# are in your PATH environment variable:
$ pipx ensurepath
Success! Added /Users/owhite/.local/bin to the PATH environment variable.

$ cat .bashrc 
# Created by `pipx` on 2025-06-02 17:24:09
export PATH="$PATH:/Users/owhite/.local/bin"

# optional to allow pipx actions with --global argument
sudo pipx ensurepath --global
```

`pipx` is a tool to help you install and run end-user applications written in
Python. It's roughly similar to macOS's `brew`, JavaScript's [npx](
https://medium.com/@maybekatz/introducing-npx-an-npm-package-runner-55f7d4bd282b
), and Linux's `apt`.

It's closely related to pip. In fact, it uses pip but is focused on installing
and managing Python packages that can be run from the command line directly as
applications.

pip is a general-purpose package installer for both libraries and apps with no
environment isolation. pipx is made specifically for application installation,
as it adds isolation yet still makes the apps available in your shell: pipx
creates an isolated environment for each application and its associated
packages.

`pipx` enables you to

- Expose CLI entrypoints of packages ("apps") installed to isolated environments
  with the `install` command. This guarantees no dependency conflicts and clean
  uninstallations!
- List, upgrade, and uninstall packages installed with pipx.
- Run the latest version of a Python application in a temporary environment with
  the `run` command.

```text
$ pipx list
nothing has been installed with pipx 😴

$ pipx install pycowsay
  installed package pycowsay 0.0.0.2, installed using Python 3.13.3
  These apps are now globally available
    - pycowsay
  These manual pages are now globally available
    - man6/pycowsay.6
done! ✨ 🌟 ✨


$ pipx list
venvs are in /Users/owhite/.local/pipx/venvs
apps are exposed on your $PATH at /Users/owhite/.local/bin
manual pages are exposed at /Users/owhite/.local/share/man
   package pycowsay 0.0.0.2, installed using Python 3.13.3
    - pycowsay
    - man6/pycowsay.6

$ pycowsay mooo

  ----
< mooo >
  ----
   \   ^__^
    \  (oo)\_______
       (__)\       )\/\
           ||----w |
           ||     ||


$ pipx uninstall pycowsay
uninstalled pycowsay! ✨ 🌟 ✨

$ pipx list
nothing has been installed with pipx 😴

$ pipx run pycowsay mooo

  ----
< mooo >
  ----
   \   ^__^
    \  (oo)\_______
       (__)\       )\/\
           ||----w |
           ||     ||


```

Example: install `uv`.
```text
$ pipx install uv
  installed package uv 0.7.9, installed using Python 3.13.3
  These apps are now globally available
    - uv
    - uvx
done! ✨ 🌟 ✨


$ uv venv .venv
Using CPython 3.12.10 interpreter at: /Users/owhite/.pyenv/versions/3.12.10/bin/python3.12
Creating virtual environment at: .venv
Activate with: source .venv/bin/activate
```

If an application installed by pipx requires additional packages, you can add
them with `pipx inject`. For example, if you have `ipython` installed and want
to add the `matplotlib` package to it, you would use:
```text
pipx inject ipython matplotlib
```
You can inject multiple packages by specifying them all on the command line, or
by listing them in a text file, with one package per line, or a combination. For
example:
```text
pipx inject ipython matplotlib pandas
# or:
pipx inject ipython -r useful-packages.txt
```

virtualenv
----------

<https://virtualenv.pypa.io/en/latest/installation.html>

[virtualenv](
https://pypi.org/project/virtualenv/
) is a CLI tool that needs a Python interpreter to run. If you already have a
Python 3.7+ interpreter, the best is to use `pipx` to install `virtualenv` into
an isolated environment. This has the added benefit that later you’ll be able to
upgrade `virtualenv` without affecting other parts of the system.
```shell
pipx install virtualenv
virtualenv --help
```

Other notes
-----------

Several examples of twin primes
```text
3, 5
5, 7
11, 13
1031, 1033
1,000,037, 1,000,039
1,000,000,007, 1,000,000,009
```

Structure-randomness dichotomy decomposes complex mathematical objects into two
parts: structured components with predictable patterns and pseudorandom
components that appear patternless, enabling mathematicians to apply specialized
techniques to analyze each part separately.
