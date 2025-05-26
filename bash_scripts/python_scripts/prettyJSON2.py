"""
Convert JSON data to human-readable form.

Usage:
    python3 prettyJSON2.py inputFile [outputFile]

Examples:
    python3 prettyJSON2.py test.json

    echo '{"b": 1, "a": 2}' | python3 prettyJSON2.py -
"""
import sys, json


def main(args: list[str]) -> bool:
    try:
        if args[1] == '-':
            inputFile = sys.stdin
        else:
            inputFile = open(args[1])
        input = json.load(inputFile)
        inputFile.close()
    except IndexError:
        usage()
        return False
    if len(args) < 3:
        print(json.dumps(input, sort_keys=False, indent=4))
    else:
        outputFile = open(args[2], "w")
        json.dump(input, outputFile, sort_keys=False, indent=4)
        outputFile.close()
    return True


def usage() -> None:
    print(__doc__)


if __name__ == "__main__":
    sys.exit(not main(sys.argv))
