This is a brief installation guide for Java SDK on Mac OS
=========================================================

See https://docs.oracle.com/javase/8/docs/technotes/guides/install/mac_jdk.html for more details.


Determining the Default Version of the JDK
------------------------------------------

    You can determine which version of the JDK is the default by typing
    % java -version or $ javac -version
    in a Terminal window. If the installed version is 8u6, you will see a string
    that includes the text 1.8.0_06. For example:

    % java -version
        java version "1.8.0_06-ea"
        Java(TM) SE Runtime Environment (build 1.8.0_06-ea-b13)
        Java HotSpot(TM) 64-Bit Server VM (build 23.2-b04, mixed mode)

    To run a different version of Java, either specify the full path, or use the java_home tool:

    % /usr/libexec/java_home -v 1.8.0_231 --exec java -version
        java version "1.8.0_231"
        Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
        Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)

    For more information, see the java_home(1) man page.


Installing the JDK
------------------

    At the Oracle’s Java 8 download page
    https://www.oracle.com/technetwork/java/javase/downloads

    JDK 8u231 Update, 1.8.0_231-b11, released October 15, 2019,
    /Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/bin/java

    JDK 8u241 1.8.0_241-b07 January 14, 2020
    jdk-8u241-macosx-x64.dmg

    JDK 8u251 1.8.0_251-b08 April 14, 2020
    jdk-8u251-macosx-x64.dmg
    
    JDK 8u261 1.8.0_261-b12 July 14, 2020
    jdk-8u261-macosx-x64.dmg
    
    JDK 8u271 1.8.0_271-b09 October 20, 2020
    jdk-8u271-macosx-x64.dmg


Uninstalling the JDK
--------------------

    To uninstall the JDK, you must have Administrator privileges and execute the remove command
    either as root or by using the sudo(8) tool.

    Navigate to
    /Library/Java/JavaVirtualMachines
    and remove the directory whose name matches the following format:
    /Library/Java/JavaVirtualMachines/jdkmajor.minor.macro[_update].jdk

    For example, to uninstall 8u6:

    % rm -rf jdk1.8.0_06.jdk

    Do not attempt to uninstall Java by removing the Java tools from /usr/bin.
    This directory is part of the system software and any changes will be reset
    by Apple the next time you perform an update of the OS.


Setting JAVA_HOME on macOS Mojave (10.14) to Lion (10.7)
--------------------------------------------------------

    % vim ~/.bash_profile

    Add the following two lines (or edit version):
        export JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_271`
        PATH=$JAVA_HOME/bin:$PATH
    Make sure you also have this line at the end:
        export PATH

    % source ~/.bash_profile

    Test the result of change:

    % echo $JAVA_HOME
        /Library/Java/JavaVirtualMachines/jdk1.8.0_271.jdk/Contents/Home

    % echo $PATH
        /Library/Java/JavaVirtualMachines/jdk1.8.0_271.jdk/Contents/Home/bin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin

    % which java
        /Library/Java/JavaVirtualMachines/jdk1.8.0_271.jdk/Contents/Home/bin/java

    % java -version
        java version "1.8.0_271"
        Java(TM) SE Runtime Environment (build 1.8.0_271-b09)
        Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)

