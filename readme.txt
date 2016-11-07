The "Jenkins to Gamepad" project for NCA Code Camp 2013. 

We want to display information (4 lines of each 80 characters) on the pad
from any kind of source. Jenkins would only be one source. 

The solution should be language independent. So any "thing" able to 
produce a 4 line file can act as a message producer.

To do so, we implemented a little messaging application which can send 
information to the display of the keyboard, as well as receiving messages 
from the buttons of the keyboard. 

We defined a simple protocol and implemented a file based version.

   %LOGITEC_DISPLAY_FOLDER_ROOT%/inbound is the folder where we can 
   place messages which then get displayed by on the pad. Each message 
   producer uses its unique feed name to produce files, for instance
   
      jenkins.10.0.messages
      tagi.10.0.messages
      
   The very left part references the feed name (such as jenkins, tagi), the 
   first number the time in seconds the message should be displayed at least 
   and the second number is the correlationId of the message. The postfix
   message is only used to identify the file as a message file.
      
    %LOGITEC_DISPLAY_FOLDER_ROOT%/outbound is the folder where the pad places 
    files when one of the four pad buttons have been pressed. The names of the 
    files have following pattern
    
      <full name of the last consumed inbound file>.bn.pressed
      
      For example: jenkins.10.0.messages.b0.pressed is the file produced 
      when the very left button is pressed, after the message from jenkins
      was consumed.
     
    %LOGITEC_DISPLAY_FOLDER_ROOT%/cfg contains for each adapter 
    the property file used for configuration. 
     
      jenkins.prop 
        jenkins.api.url=http://localhost:8080/jenkins/api/xml?depth=1
        
      tagi.prop
        tagi.rss_ticker.url=http://www.tagesanzeiger.ch/rss_ticker.html
        
    We use separated files to avoid any name clashes and also to follow
    the plug in concept.     
     
     
The class nca.any2logitec.impl.LogitectHub is the main class representing the 
java based hub. It contains also the configuration for the Jenkins and tagi 
adapters. Currently we do not have a installation script for for the java 
part. 

In the folder cpp you find the c++ programm which talks to the game pad through
the provided API from logitec. See also 
http://www.logitech.com/de-ch/support/7246?crid=411&osid=14&bit=64 for more
details on that.

The logitecHub.7z contains the C++ executable. After you have installed the
software for the Logitec G510 unzip the file at any location and run the exe.

See also the Präsentation1.pptx for more details.


   
