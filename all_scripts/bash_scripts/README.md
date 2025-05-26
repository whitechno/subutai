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
From [here](
https://stackoverflow.com/questions/352098/how-can-i-pretty-print-json-in-a-shell-script
)

### With Python

(to sort, add the `--sort-keys` flag to the end):
`echo '{"foo": "lorem", "bar": "ipsum"}' | python -m json.tool --sort-keys`

For even more convenience with a bit more typing to get it ready:

```shell
# if the JSON is a string
prettyjson_s() {
  echo "$1" | python -m json.tool
}

# if the JSON is in a file
prettyjson_f() {
  python -m json.tool "$1"
}

# if the JSON is from an internet source such as an API
prettyjson_w() {
  curl "$1" | python -m json.tool
}
```

You can put this in .bashrc and it will be available every time in shell. Invoke it
like `prettyjson_s '{"foo": "lorem", "bar": "ipsum"}'`.

Another way:

```shell
echo '{"test":1,"test2":2}' | \
python -c 'import sys,json;data=json.loads(sys.stdin.read());print(data["test"])'
```

If you want to do it all in one go with curl on the command line using an
authentication token:

```shell
curl -X GET -H "Authorization: Token wef4fwef54te4t5teerdfgghrtgdg53" \
http://testsite/api/ | python -m json.tool
```

### With jq

(You can find their tutorials [here](
http://stedolan.github.io/jq/tutorial/).):
`jq <<< '{ "foo": "lorem", "bar": "ipsum" }'`

Usage examples:

```shell
$ jq --color-output . file1.json file1.json | less -R

$ command_with_json_output | jq .

$ jq # stdin/"interactive" mode, just enter some JSON

$ jq <<< '{ "foo": "lorem", "bar": "ipsum" }'
{
  "foo": "lorem",
  "bar": "ipsum"
}
```

Other Python
------------

Nice example of using github-actions with Python:
[nickzxcv / gipv6minmax](https://github.com/nickzxcv/gipv6minmax)

