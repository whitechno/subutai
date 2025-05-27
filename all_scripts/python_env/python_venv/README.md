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
  --upgrade             Upgrade the environment directory to use this version of 
                        Python, assuming Python has been upgraded in-place.
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
```

You can deactivate a virtual environment by typing `deactivate` in your shell.

When a Python interpreter is running from a virtual environment, `sys.prefix`
and `sys.exec_prefix` point to the directories of the virtual environment,
whereas `sys.base_prefix` and `sys.base_exec_prefix` point to those of the base
Python used to create the environment. It is sufficient to check
`sys.prefix != sys.base_prefix` to determine if the current interpreter is
running from a virtual environment.
```text
>>> import sys
>>> print(sys.prefix)
subutai/all_scripts/python_env/python_venv/.venv
>>> print(sys.base_prefix)
/usr/local/opt/python@3.13/Frameworks/Python.framework/Versions/3.13
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

Static Type Checkers
--------------------

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

