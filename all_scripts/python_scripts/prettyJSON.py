#!/usr/bin/env python3

"""
Convert JSON data to human-readable form.

(Reads from stdin and writes to stdout)

Examples:
  echo '{"foo": "lorem", "bar": "ipsum"}' | python3 prettyJSON.py

  # ensure_ascii=False to avoid printing "D\u00fcsseldorf"
  echo '{ "Düsseldorf": "lorem", "bar": "ipsum" }' | ./prettyJSON.py

  cat foo.json | ./prettyJSON.py
  or
  python3 prettyJSON.py < foo.json

`chmod +x prettyJSON.py`
"""
import sys

try:
    import simplejson as json
except:
    import json
print(json.dumps(json.loads(sys.stdin.read()),
                 sort_keys=True, indent=4, ensure_ascii=False))
sys.exit(0)
