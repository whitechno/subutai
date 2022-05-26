#!/usr/bin/env python
"""
Convert JSON data to human-readable form.

(Reads from stdin and writes to stdout)
Examples:
  echo '{"foo": "lorem", "bar": "ipsum"}' | python prettyJSON.py

  cat foo.json | python prettyJSON.py
  ot
  python prettyJSON.py < foo.json
"""
import sys

try:
    import simplejson as json
except:
    import json
print(json.dumps(json.loads(sys.stdin.read()), indent=4))
sys.exit(0)
