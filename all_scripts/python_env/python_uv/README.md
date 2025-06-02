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

### virtualenv

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