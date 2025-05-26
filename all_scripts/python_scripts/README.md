# Python Configuration for IntelliJ IDEA

May 25, 2025

This directory contains Python scripts for JSON formatting and other utilities.
The following instructions will help you configure IntelliJ IDEA to recognize
and use the specific Python interpreter at `/usr/local/bin/python3.13`.

There are multiple choices in [Add Python Interpreter](
https://www.jetbrains.com/help/idea/configuring-python-sdk.html#local-python-interpreters
), some of them are:
- Virtualenv Environment
- System Interpreter
- Pipenv Environment
- Poetry Environment
- Uv Environment

This module is set with System Interpreter based on `/usr/local/bin/python3.13`
SDK. This is the simplest setup, but it has a disadvantage that all
pip-installed packages are installed globally. Virtualenv is a better choice in
this regard.

## Configuration Steps

1. **Open the Project in IntelliJ IDEA**

2. **Configure the Python Interpreter**:

- Go to File > Project Structure > Project Settings > Modules
- Select the "python_scripts" module
- Go to the "Dependencies" tab
- Click on "Module SDK" dropdown
- Select "Python 3.13" if it exists
- If it doesn't exist, click on "New..." and follow these steps:
  - Select "Python SDK"
  - Choose "System Interpreter"
  - Navigate to and select '/usr/local/bin/python3.13'
  - Click "OK"

3. **Verify Configuration**:

- Open one of the Python scripts (e.g., prettyJSON.py or prettyJSON2.py)
- IntelliJ should now recognize the Python interpreter and provide proper code
  completion and analysis

## Troubleshooting

If IntelliJ still doesn't recognize the Python module:

1. **Fixing Project Module Manager configs in `.idea`**:

- Make sure the Python interpreter is correctly configured in:
  - `.idea/modules/python_scripts.iml` (should use "Python 3.13")
  - `.idea/misc.xml` (should have a "Black" component with sdkName as "Python
    3.13")
- `.idea/modules.xml` should list module python_scripts

See "Notes" below for more details.

2. **Invalidate Caches and Restart**:

- Go to File > Invalidate Caches / Restart...
- Select "Invalidate and Restart"

3. **Check Python Plugin**:

- Ensure the Python plugin is installed and enabled
- Go to File > Settings > Plugins
- Search for "Python" and make sure it's installed and enabled

## Notes

`.idea/modules.xml` should have this line in `<modules>`:
```xml

<module fileurl="file://$PROJECT_DIR$/.idea/modules/python_scripts.iml"
        filepath="$PROJECT_DIR$/.idea/modules/python_scripts.iml"/>
```

`.idea/modules/python_scripts.iml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<module type="PYTHON_MODULE" version="4">
    <component name="CheckStyle-IDEA-Module" serialisationVersion="2">
        <option name="activeLocationsIds"/>
    </component>
    <component name="NewModuleRootManager" inherit-compiler-output="true">
        <exclude-output/>
        <content url="file://$MODULE_DIR$/../../bash_scripts/python_scripts">
            <sourceFolder
                    url="file://$MODULE_DIR$/../../bash_scripts/python_scripts"
                    isTestSource="false"/>
        </content>
        <orderEntry type="jdk" jdkName="Python 3.13" jdkType="Python SDK"/>
        <orderEntry type="sourceFolder" forTests="false"/>
    </component>
</module>
```

`.idea/misc.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
    <component name="Black">
        <option name="sdkName" value="Python 3.13"/>
    </component>
    <component name="ProjectRootManager" version="2" languageLevel="JDK_17"
               default="true" project-jdk-name="openjdk-17"
               project-jdk-type="JavaSDK">
        <output url="file://$PROJECT_DIR$/out"/>
    </component>
    <component name="PyCharmProfessionalAdvertiser">
        <option name="shown" value="true"/>
    </component>
    <component name="PythonCompatibilityInspectionAdvertiser">
        <option name="version" value="3"/>
    </component>
</project>
```
