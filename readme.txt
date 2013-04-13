The "Jekins to Gamepad" project for NCA Code Camp 2013. Actually we want to 
display any 4 line information on the pad. Jekins would only be one source. 

The whole thing should be language independent. So any device that can produce 
a 4 line file can act as a message producer.

We implemented a little messaging application which can send information to 
the display as receiving messages from the buttons. Doing this we defined a 
simple protocol and implemented a file based version.

   ${windowsUser}/.logitec/inbound is the folder where we can place messages 
   which then get displayed by the pad. Each message producer uses its unique 
   feed name to produce files, for instance
   
      jenkins.10.0.messages
      tagi.10.0.messages
      
   The very left part references the feed name (such as jenkins, tagi), the 
   first number the time in seconds the message should be displayed at least 
   and the second number the correlationId of the message. The postfix
   message is only used to identify the file as message file.
   
   
    ${windowsUser}/.logitec/outbound it the folder where we place files when 
    one of the four pad buttons is pressed. The names of the files have 
    following pattern
    
      <full name of the last consumed inbound file>.bn.pressed
      
     For example: jenkins.10.0.messages.b0.pressed is the file produced when 
     the very left button is pressed after the message from jenkins was 
     consumed.
     
The class nca.any2logitec.impl.LogitectHub is the main class. It contains also
the "hard coded" configuration for the jekins and tagi adapters.

In the folder cpp you find the c++ programm which talks to the game pad through
the provided API from logitec. See also 
http://www.logitech.com/de-ch/support/7246?crid=411&osid=14&bit=64 for more
details on that.

   
