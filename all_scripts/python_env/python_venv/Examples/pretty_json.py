"""
Convert JSON data to human-readable form.

Usage:
    python3 pretty_json.py inputFile

Examples:
    python3 pretty_json.py foo.json
"""
import sys, platform, json


def main(args: list[str]) -> bool:
    print("sys.prefix: ", sys.prefix)
    print("sys.base_prefix: ", sys.base_prefix)
    print("sys.version: ", sys.version)
    print("sys.version_info: ", sys.version_info)
    print("platform.python_version(): ", platform.python_version())

    try:
        inputFile = open(args[1])
        input = json.load(inputFile)
        inputFile.close()
        print(json.dumps(input, sort_keys=True, indent=4))
    except IndexError:
        return False
    return True


if __name__ == "__main__":
    sys.exit(not main(sys.argv))
