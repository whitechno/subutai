Python venv
===========

<https://docs.python.org/3/library/venv.html>

`python3 -m venv -h` will show the available options:
```text
usage: venv [-h] [--system-site-packages] [--symlinks | --copies] [--clear] 
            [--upgrade] [--without-pip] [--prompt PROMPT] [--upgrade-deps] 
            [--without-scm-ignore-files]
            ENV_DIR [ENV_DIR ...]

Creates virtual Python environments in one or more target directories.

positional arguments:
  ENV_DIR               A directory to create the environment in.

options:
  -h, --help            show this help message and exit
  --system-site-packages
                        Give the virtual environment access to the system 
                        site-packages dir.
  --symlinks            Try to use symlinks rather than copies, when symlinks 
                        are not the default for the platform.
  --copies              Try to use copies rather than symlinks, even when 
                        symlinks are the default for the platform.
  --clear               Delete the contents of the environment directory if it 
                        already exists, before environment creation.
  --upgrade             Upgrade the environment directory to use this version
                        of Python, assuming Python has been upgraded in-place.
  --without-pip         Skips installing or upgrading pip in the virtual 
                        environment (pip is bootstrapped by default)
  --prompt PROMPT       Provides an alternative prompt prefix for this environment.
  --upgrade-deps        Upgrade core dependencies (pip) to the latest 
                        version in PyPI
  --without-scm-ignore-files
                        Skips adding SCM ignore files to the environment 
                        directory (Git is supported by default).

Once an environment has been created, you may wish to activate it, 
e.g. by sourcing an activate script in its bin directory:
source ENV_DIR/bin/activate
```

Create virtual environment:
```text
python3 -m venv /path/to/new/virtual/environment
```

Create virtual environment named `.venv` in the module directory:
```text
python3 -m venv .venv
```

You can run it without activating a virtual environment:
```text
$ .venv/bin/python pretty_json.py foo.json
```

Activate it:
```text
$ source .venv/bin/activate

$ echo $VIRTUAL_ENV
subutai/all_scripts/python_env/python_venv/.venv

# deactivate a virtual environment:
$ deactivate
```

Upgrade:
```text
$ python3 -m venv --upgrade --upgrade-deps .venv
```

When a Python interpreter is running from a virtual environment, `sys.prefix`
and `sys.exec_prefix` point to the directories of the virtual environment,
whereas `sys.base_prefix` and `sys.base_exec_prefix` point to those of the base
Python used to create the environment. It is enough to check
`sys.prefix != sys.base_prefix` to determine if the current interpreter is
running from a virtual environment.
```text
>>> import sys
>>> print(sys.prefix)
subutai/all_scripts/python_env/python_venv/.venv
>>> print(sys.base_prefix)
/usr/local/opt/python@3.13/Frameworks/Python.framework/Versions/3.13
```

Run example:
```text
$ python Examples/pretty_json.py Examples/foo.json
sys.prefix:  /Users/owhite/dev/whitechno-github/subutai/all_scripts/python_env/python_venv/.venv
sys.base_prefix:  /usr/local/opt/python@3.13/Frameworks/Python.framework/Versions/3.13
sys.version:  3.13.5 (main, Jun 11 2025, 15:36:57) [Clang 16.0.0 (clang-1600.0.26.6)]
sys.version_info:  sys.version_info(major=3, minor=13, micro=5, releaselevel='final', serial=0)
platform.python_version():  3.13.5
{
    "bar": "ipsum",
    "foo": "lorem"
}
```

## WARNING:

Because scripts installed in environments should not expect the environment to
be activated, their shebang lines contain the absolute paths to their
environment’s interpreters. Because of this, environments are inherently
non-portable, in the general case. You should always have a simple means of
recreating an environment (for example, if you have a requirements file
`requirements.txt`, you can invoke `pip install -r requirements.txt` using the
environment’s `pip` to install all the packages needed by the environment). If
for any reason you need to move the environment to a new location, you should
recreate it at the desired location and delete the one at the old location. If
you move an environment because you moved a parent directory of it, you should
recreate the environment in its new location. Otherwise, software installed into
the environment may not work as expected.


`pip install` with requirements.txt
===================================

Install
-------
```text
(.venv)$ pip install -r requirements.txt
```

[Requirement Specifiers](
https://pip.pypa.io/en/stable/reference/requirement-specifiers/
)

[Requirements File Format](
https://pip.pypa.io/en/stable/reference/requirements-file-format/
)

[pip install Examples](
https://pip.pypa.io/en/stable/cli/pip_install/#examples
)

Upgrade
-------
```text
# Upgrade all specified packages to the newest available version. 
# The handling of dependencies depends on the upgrade-strategy used.
(.venv)$ pip install -U -r requirements.txt

(.venv)$ pip install -U --upgrade-strategy eager -r requirements.txt
--upgrade-strategy <upgrade_strategy>
  Determines how dependency upgrading should be handled [default: only-if-needed]. 
  "eager" - dependencies are upgraded regardless of whether the currently installed 
  version satisfies the requirements of the upgraded package(s). 
  "only-if-needed" -  are upgraded only when they do not satisfy the requirements 
  of the upgraded package(s).
```

Uninstall
---------
To uninstall Python packages that were installed with
`pip install -r requirements.txt`, you have several options:

### Option 1: Uninstall using the same requirements file

You can use the same requirements.txt file to uninstall all packages:

```shell script
pip uninstall -y -r requirements.txt
```

The `-y` flag automatically confirms all uninstallations without prompting.

### Option 2: Generate a new uninstall requirements file

If some packages in your requirements.txt are commented out (like in your file),
you might want to create a separate file with only the packages you want to
uninstall:

```shell script
# Create a file with only the packages to uninstall
grep -v '^#' requirements.txt > to_uninstall.txt

# Then uninstall using that file
pip uninstall -y -r to_uninstall.txt
```

### Option 3: Uninstall specific packages individually

If you only want to uninstall certain packages:

```shell script
pip uninstall mypy mypy-extensions
```

### Option 4: Recreate your virtual environment

**This is by far the best option.**

If you're using a virtual environment and want to start fresh.

```shell script
# Deactivate current environment
deactivate

# Delete and recreate the environment
rm -rf your_env_directory
python -m venv your_env_directory

# Activate the new environment
source your_env_directory/bin/activate  # Linux/Mac
# or
your_env_directory\Scripts\activate  # Windows

# install all the packages needed by the environment
pip install -r requirements.txt
```

### Option 5: Using pip-autoremove (for dependencies)

If you want to remove packages along with their dependencies that aren't used by
other packages:

```shell script
# Install pip-autoremove
pip install pip-autoremove

# Remove a package and its unused dependencies
pip-autoremove mypy -y
```

Remember that if you've installed other packages that depend on those in your
requirements.txt file, you might encounter issues after uninstalling. It's
generally safest to work within isolated virtual environments for Python
projects.


Static Type Checkers
====================

Python 3.13 supports several mature static type checkers that can help ensure
your code's type safety. Here's an overview of the main options available:

## 1. Mypy

Mypy is the original and most widely used Python type checker, developed by the
Python Software Foundation. It's considered the reference implementation for
Python's type checking system.
**Key features:**
- Follows PEP 484 and other typing-related PEPs
- Extensive documentation and community support
- Gradual typing (can check partially typed codebases)
- Integrated with many IDEs and CI systems

**Installation:**
``` 
pip install mypy
```

## 2. Pyright / Pylance

Pyright is Microsoft's type checker for Python, which powers the Pylance
extension in Visual Studio Code.
**Key features:**
- Significantly faster than mypy (3-5x on large
  codebases) [[3]](https://news.ycombinator.com/item?id=42868576)
- Watch mode for incremental type checking
- Can infer types well even in code without explicit annotations
- Strong IDE integration, especially with VS Code

**Installation:**
``` 
pip install pyright
```

## 3. Pytype (Google)

Google's type checker with some unique features for handling dynamic Python
code.
**Key features:**
- Can infer types even in code without annotations
- Checks code that will run at runtime, not just static annotations
- Handles dynamic Python features well
- Can generate type stubs for third-party libraries

**Installation:**
``` 
pip install pytype
```

## 4. Pyre (Meta/Facebook)

Meta's performant type checker focused on large codebases.
**Key features:**
- Optimized for large codebases with millions of lines of code
- Incremental analysis for instant developer feedback
- Includes Pysa, a security-focused static analyzer
- Built in OCaml for performance [[4]](https://github.com/facebook/pyre-check)

**Installation:**
``` 
pip install pyre-check
```
## Comparison for Python 3.13

All these type checkers support Python 3.13's typing features, but they have
different strengths:
- **Mypy**: Best for strict adherence to Python typing standards and
  comprehensive documentation
- **Pyright**: Best for performance and IDE integration, especially in VS Code
- **Pytype**: Best for inferring types in partially annotated codebases
- **Pyre**: Best for large enterprise codebases and security analysis

For your code example that uses type annotations like , any of these tools would
work well. The choice often comes down to workflow preferences, IDE integration,
and specific project needs. `def main(args: list[str]) -> bool:`
Many developers choose Pyright/Pylance for day-to-day development due to its
speed and IDE integration, while using Mypy in CI pipelines for its strict
adherence to
standards [[1]](https://www.infoworld.com/article/2260170/4-python-type-checkers-to-keep-your-code-clean.html).
All of these checkers support Python 3.13's typing features, including:
- Type variable syntax improvements
- Enhanced union types (`|`)
- Self-types
- TypedDict improvements
- And other type-related enhancements

Running Static Type Checks with Mypy
====================================
After installing mypy from your requirements.txt file, you can run static type
checking on your Python code in several ways.

Basic Usage
-----------
The simplest way to run mypy is to provide the file or directory you want to
check:
```shell script
mypy your_file.py
```
or for multiple files:
```shell script
mypy file1.py file2.py directory/
mypy .
```

Common Command Line Options
---------------------------
Mypy offers several command line options to customize type checking:

### 1. Strictness Levels
```shell script
# Enable strict type checking (recommended)
mypy --strict your_file.py

# Or for a less strict check (more permissive)
mypy --disallow-untyped-defs your_file.py
```
The `--strict` flag enables all strict type checking options at
once [[1]](https://mypy.readthedocs.io/en/stable/command_line.html).

### 2. Python Version

Specify which Python version's features to use it for type checking:
```shell script
# For Python 3.13 specifically
mypy --python-version 3.13 your_file.py
```

### 3. Ignoring Errors
```shell script
# Ignore specific errors using error codes
mypy --ignore-missing-imports your_file.py
```

### 4. Generating Reports
```shell script
# Generate a report showing type coverage
mypy --html-report ./reports your_file.py
```

Configuration File
------------------
For projects, it's usually better to create a `mypy.ini` or `setup.cfg` file
with your configuration:
```textmate
# mypy.ini
[mypy]
python_version = 3.13
warn_return_any = True
disallow_untyped_defs = True
disallow_incomplete_defs = True

# Per-module options
[mypy.module_name]
ignore_missing_imports = True
```

Then run mypy without specifying options each time:
```shell script
mypy your_file.py
```

Running on Your Project
-----------------------
For a typical project setup, you might want to:
1. Create a configuration file
2. Run mypy on your entire package:

```shell script
# Check an entire package
mypy your_package/

# Alternatively, find and check all Python files
mypy $(find . -name "*.py")
```

Example for a Script
--------------------
Let's say you have a script named `pretty_json.py` like in your repository. You
could check it with:

```shell script
# Basic check
mypy pretty_json.py

# Strict check
mypy --strict pretty_json.py

# With specific settings
mypy --disallow-untyped-defs --disallow-incomplete-defs pretty_json.py
```

Mypy will analyze the code and output any type errors it finds, such as
incompatible types, missing annotations, or incorrect function
calls [[2]](https://mypy.readthedocs.io/en/stable/getting_started.html).

Integration with Development Tools
----------------------------------
You can also integrate mypy with:
- **Pre-commit hooks**: Run mypy before committing code
- **CI/CD pipelines**: Run mypy as part of your continuous integration
- **IDEs**: Many IDEs like PyCharm, VS Code (with the appropriate plugin) can
  run mypy in real-time

This setup ensures your code is consistently type-checked throughout
development.
