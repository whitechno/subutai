#!/usr/bin/env python

"""
Convert JSON data to human-readable form.

(Reads from stdin and writes to stdout)

Examples:
  echo '{"foo": "lorem", "bar": "ipsum"}' | python prettyJSON.py

  # ensure_ascii=False to avoid printing "D\u00fcsseldorf"
  echo '{ "Düsseldorf": "lorem", "bar": "ipsum" }' | ./prettyJSON.py

  cat foo.json | python prettyJSON.py
  or
  python prettyJSON.py < foo.json
"""
import sys

try:
    import simplejson as json
except:
    import json
print(json.dumps(json.loads(sys.stdin.read()),
                 sort_keys=True, indent=4, ensure_ascii=False))
sys.exit(0)
