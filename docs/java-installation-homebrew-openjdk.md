Homebrew openjdk
================

```text
$ brew install openjdk@8
For the system Java wrappers to find this JDK, symlink it with
$ sudo ln -sfn \
/usr/local/opt/openjdk@8/libexec/openjdk.jdk \
/Library/Java/JavaVirtualMachines/openjdk-8.jdk

openjdk@8 is keg-only, which means it was not symlinked 
into /usr/local,
because this is an alternate version of another formula.

If you need to have openjdk@8 first in your PATH, run:
$ echo 'export PATH="/usr/local/opt/openjdk@8/bin:$PATH"' >> /Users/owhite/.bash_profile

For compilers to find openjdk@8 you may need to set:
$ export CPPFLAGS="-I/usr/local/opt/openjdk@8/include"
```

```text
$ brew install openjdk@17

$ sudo ln -sfn \
/usr/local/opt/openjdk@17/libexec/openjdk.jdk \
/Library/Java/JavaVirtualMachines/openjdk-17.jdk

$ export JAVA_HOME=$(/usr/libexec/java_home -v 17); \
export PATH=$JAVA_HOME/bin:$PATH; \
export CPPFLAGS=-I$JAVA_HOME/include
```

Add to `.bash_profile`
```text
alias j17="export JAVA_HOME=$(/usr/libexec/java_home -v 17); \
export PATH=$JAVA_HOME/bin:$PATH; \
export CPPFLAGS=-I$JAVA_HOME/include; \
java -version"
```

Check downloaded Java versions:
```
$ /usr/libexec/java_home -V
Matching Java Virtual Machines (4):
    17.0.9 (x86_64) "Homebrew" - "OpenJDK 17.0.9" /usr/local/Cellar/openjdk@17/17.0.9/libexec/openjdk.jdk/Contents/Home
    1.8.381.09 (x86_64) "Oracle Corporation" - "Java" /Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home
    1.8.0_392 (x86_64) "Homebrew" - "OpenJDK 8" /usr/local/Cellar/openjdk@8/1.8.0-392/libexec/openjdk.jdk/Contents/Home
    1.8.0_381 (x86_64) "Oracle Corporation" - "Java SE 8" /Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home
/usr/local/Cellar/openjdk@17/17.0.9/libexec/openjdk.jdk/Contents/Home


$ echo $JAVA_HOME
/usr/local/Cellar/openjdk@17/17.0.8.1/libexec/openjdk.jdk/Contents/Home

$ echo $PATH
/usr/local/Cellar/openjdk@17/17.0.8.1/libexec/openjdk.jdk/Contents/Home/bin
:/usr/local/bin
:/usr/local/Cellar/openjdk@8/1.8.0-382_1/libexec/openjdk.jdk/Contents/Home/bin
:/usr/local/bin
:/usr/bin
:/bin
:/usr/sbin
:/sbin
:/Library/TeX/texbin
:/Library/Apple/usr/bin

$ which java
/usr/local/Cellar/openjdk@17/17.0.8.1/libexec/openjdk.jdk/Contents/Home/bin/java

$ java -version
openjdk version "17.0.8.1" 2023-08-24
OpenJDK Runtime Environment Homebrew (build 17.0.8.1+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.8.1+0, mixed mode, sharing)
```
