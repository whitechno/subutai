Python UV
=========
May 30, 2025

- pyenv
- pipx
- uv

Starter references
------------------

<https://github.com/astral-sh/uv>

<https://docs.astral.sh/uv>

<https://docs.astral.sh/uv/getting-started/installation/>

[Managing Python Virtual Environments with UV: A Comprehensive Guide](
https://medium.com/@vkmauryavk/managing-python-virtual-environments-with-uv-a-comprehensive-guide-ac74d3ad8dff
) by Vijay Maurya Dec 26, 2024

UV is a modern, blazing-fast Python package and project manager written in Rust.
UV simplifies virtual environment management, dependency handling, and Python
version control, making it a one-stop solution for Python developers.


Related references
------------------

### LangGraph-Course

<https://academy.langchain.com/courses/intro-to-langgraph>

<https://github.com/Vaibhav807/LangGraph-Course>

YouTube
[LangGraph Complete Course for Beginners – Complex AI Agents with Python](
https://www.youtube.com/watch?v=jGg_1h0qzaM)

### pipx

pipx — Install and Run Python Applications in Isolated Environments.

[pipx](https://pypi.org/project/pipx/)

```shell
brew install pipx
pipx ensurepath

# optional to allow pipx actions with --global argument
sudo pipx ensurepath --global
```

Upgrade pipx with `brew update && brew upgrade pipx`.

`pipx` is a tool to help you install and run end-user applications written in
Python. It's roughly similar to macOS's `brew`, JavaScript's [npx](
https://medium.com/@maybekatz/introducing-npx-an-npm-package-runner-55f7d4bd282b
), and Linux's `apt`.

It's closely related to pip. In fact, it uses pip but is focused on installing
and managing Python packages that can be run from the command line directly as
applications.

### virtualenv

<https://virtualenv.pypa.io/en/latest/installation.html>

[virtualenv](
https://pypi.org/project/virtualenv/
) is a CLI tool that needs a Python interpreter to run. If you already have a
Python 3.7+ interpreter, the best is to use pipx to install virtualenv into an
isolated environment. This has the added benefit that later you’ll be able to
upgrade virtualenv without affecting other parts of the system.
```shell
pipx install virtualenv
virtualenv --help
```