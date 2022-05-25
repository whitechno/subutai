Experiments with Bash scripting
===============================

- [Bash in Wikipedia](https://en.wikipedia.org/wiki/Bash_(Unix_shell))

- [Bash scripting cheatsheet](https://devhints.io/bash)

- [BashGuide](http://mywiki.wooledge.org/BashGuide)
    - Especially this: [Tests and Conditionals](
      http://mywiki.wooledge.org/BashGuide/TestsAndConditionals)
    - And this: [Patterns](
      http://mywiki.wooledge.org/BashGuide/Patterns)

- [Bash Shell Check Whether a Directory is Empty or Not](
  https://www.cyberciti.biz/faq/linux-unix-shell-check-if-directory-empty/)

- [SC2181: Check exit code directly with e.g. if mycmd;, not indirectly with $?.](
  https://github.com/koalaman/shellcheck/wiki/SC2181)  
  To additionally capture output with command substitution:  
  `if ! output=$(mycommand); then ...`

Strings
-------
`$'\t'` or `printf '\t'` or `echo -e "\t"` or `echo -e '\t'`

Use `printf`, not `echo`. There are multiple different versions of the `echo`
command. `printf`, on the other hand, has much less variation.

Pretty-print JSON
-----------------
With Python (to sort, add the `--sort-keys` flag to the end):
`echo '{"foo": "lorem", "bar": "ipsum"}' | python -m json.tool --sort-keys`

or use `jq` (You can find their tutorials [here](
http://stedolan.github.io/jq/tutorial/).):
`jq <<< '{ "foo": "lorem", "bar": "ipsum" }'`


