Python UV
=========
May 30, 2025

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

### Install `uv` using `pipx`:
```text
$ pipx install uv
  installed package uv 0.7.9, installed using Python 3.13.3
  These apps are now globally available
    - uv
    - uvx
done! ✨ 🌟 ✨

$ pipx upgrade uv
upgraded package uv from 0.7.9 to 0.7.12 
(location: /Users/owhite/.local/pipx/venvs/uv)
```

### Set Python version using `pyenv`:
```text
$ cd all_scripts/python_env/python_uv/

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
```

### Set virtual env using `uv`
```text
$ uv venv .venv
Using CPython 3.12.10 interpreter at: /Users/owhite/.pyenv/versions/3.12.10/bin/python3.12
Creating virtual environment at: .venv
Activate with: source .venv/bin/activate
```

### Activate it:
```text
$ source .venv/bin/activate

(.venv) MacBook-Pro:python_uv owhite$ which python
/Users/owhite/dev/whitechno-github/subutai/all_scripts/python_env/python_uv/.venv/bin/python

$ python Examples/pretty_json_uv.py Examples/foo.json 
sys.prefix:  /Users/owhite/dev/whitechno-github/subutai/all_scripts/python_env/python_uv/.venv
sys.base_prefix:  /Users/owhite/.pyenv/versions/3.12.10
{
    "bar": "ipsum",
    "foo": "lorem"
}
```

Related references
------------------

### LangGraph-Course

<https://academy.langchain.com/courses/intro-to-langgraph>

<https://github.com/Vaibhav807/LangGraph-Course>

YouTube
[LangGraph Complete Course for Beginners – Complex AI Agents with Python](
https://www.youtube.com/watch?v=jGg_1h0qzaM)

