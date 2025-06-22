Homebrew openjdk
================

brew upgrade log
----------------

- 2025-06-22
  - openjdk 23.0.2 -> 24.0.1
  - maven 3.9.9 -> 3.9.10
  - sbt 1.11.0 -> 1.11.2

openjdk
-------

```text
$ brew search openjdk
==> Formulae
openjdk ✔      openjdk@11     openjdk@17 ✔   openjdk@21     openjdk@8 ✔    
openj9         openjph        openvdb

==> Casks
adoptopenjdk    microsoft-openjdk@11    microsoft-openjdk@21
microsoft-openjdk   microsoft-openjdk@17    openttd
```

**`openjdk` not symlinked**
```text
$ brew info openjdk
==> openjdk: stable 23.0.2 (bottled) [keg-only]
Development kit for the Java programming language
https://openjdk.java.net/
Installed
/usr/local/Cellar/openjdk/23.0.2 (602 files, 337MB)
  Poured from bottle using the formulae.brew.sh API on 2025-02-24 at 10:14:28
From: https://github.com/Homebrew/homebrew-core/blob/HEAD/Formula/o/openjdk.rb
License: GPL-2.0-only WITH Classpath-exception-2.0
==> Dependencies
Build: autoconf ✔, pkgconf ✔
Required: freetype ✔, giflib ✔, harfbuzz ✔, jpeg-turbo ✔, libpng ✔, little-cms2 ✔
==> Requirements
Build: Xcode (on macOS) ✔
Required: macOS >= 10.15 (or Linux) ✔
==> Caveats
For the system Java wrappers to find this JDK, symlink it with
  sudo ln -sfn \
  /usr/local/opt/openjdk/libexec/openjdk.jdk \
  /Library/Java/JavaVirtualMachines/openjdk.jdk
```

**`openjdk@8` symlinked**
```text
$ brew info openjdk@8
==> openjdk@8: stable 1.8.0-452 (bottled) [keg-only]
Development kit for the Java programming language
https://openjdk.java.net/
Installed
/usr/local/Cellar/openjdk@8/1.8.0-452 (783 files, 191.2MB)
  Poured from bottle using the formulae.brew.sh API on 2025-05-24 at 14:49:56
From: https://github.com/Homebrew/homebrew-core/blob/HEAD/Formula/o/openjdk@8.rb
License: GPL-2.0-only
==> Dependencies
Build: autoconf ✔, pkgconf ✘, gawk ✔
Required: freetype ✔, giflib ✔
==> Requirements
Required: x86_64 architecture ✔
==> Caveats
For the system Java wrappers to find this JDK, symlink it with
  sudo ln -sfn /usr/local/opt/openjdk@8/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-8.jdk
```
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

**`openjdk@17` symlinked**
```text
$ brew info openjdk@17
==> openjdk@17: stable 17.0.15 (bottled) [keg-only]
Development kit for the Java programming language
https://openjdk.java.net/
Installed
/usr/local/Cellar/openjdk@17/17.0.15 (636 files, 303.9MB)
  Poured from bottle using the formulae.brew.sh API on 2025-05-24 at 14:43:47
From: https://github.com/Homebrew/homebrew-core/blob/HEAD/Formula/o/openjdk@17.rb
License: GPL-2.0-only WITH Classpath-exception-2.0
==> Dependencies
Build: autoconf ✔, pkgconf ✘
Required: freetype ✔, giflib ✔, harfbuzz ✔, jpeg-turbo ✘, libpng ✘, little-cms2 ✔
==> Requirements
Build: Xcode (on macOS) ✔
==> Caveats
For the system Java wrappers to find this JDK, symlink it with
  sudo ln -sfn /usr/local/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
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

**Check symlinks:**
```text
$ ls -la /Library/Java/JavaVirtualMachines/
drwxr-xr-x  3 root  wheel   96 Aug  6  2023 jdk-1.8.jdk
lrwxr-xr-x  1 root  wheel   45 Oct  1  2023 openjdk-17.jdk -> /usr/local/opt/openjdk@17/libexec/openjdk.jdk
lrwxr-xr-x  1 root  wheel   44 Oct  1  2023 openjdk-8.jdk -> /usr/local/opt/openjdk@8/libexec/openjdk.jdk
```

**Add to `.bash_profile`:**
```text
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8.0_452) # Homebrew openjdk@8
# export JAVA_HOME=$(/usr/libexec/java_home -v 17) # Homebrew openjdk@17
PATH=$JAVA_HOME/bin:$PATH
alias j17='export JAVA_HOME=$(/usr/libexec/java_home -v 17); export PATH=$JAVA_HOME/bin:$PATH; export CPPFLAGS="-I$JAVA_HOME/include"'
alias j8='export JAVA_HOME=$(/usr/libexec/java_home -v 1.8.0_452); export PATH=$JAVA_HOME/bin:$PATH; export CPPFLAGS="-I$JAVA_HOME/include"'
```
```text
alias j17="export JAVA_HOME=$(/usr/libexec/java_home -v 17); \
export PATH=$JAVA_HOME/bin:$PATH; \
export CPPFLAGS=-I$JAVA_HOME/include; \
java -version"
```

**Check downloaded Java versions:**
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
