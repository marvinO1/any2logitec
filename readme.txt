The "Jekins to Gamepad" project for NCA Code Camp 2013. Actually we want to 
display any 4 line information on the pad. Jekins would only be one source. 

The whole thing should be language independent. So any device that can produce 
a 4 line file can act as a message producer.

Approach
--------
 - We have a text file consumer which will read text files from the directory 
   defined with the system environment LOGITEC_DISPLAY_FOLDER_ROOT. 
   The consumer is written in C++ to be able to talk to the Logitec game pad.
   
   - code is not yet in GIT
   - the first 4 lines of such a text file will be send to the logitec game pad
   - after sending the lines, the file will be removed by the consumer
   - name pattern of the files: *.nn.message
      - nn is the time in seconds the message should be displayed at least
      - current implementation:
         - the consumer ignores this argument
         - files will be consumed in any order. It is not a FIFO yet!
   - in case there is no new file, the last displayed text will remain 
   - an empty file will reset the text on the game pad
   
     
 - We have a text file producer written in Java (the code of this project) 
   which connects to a jenkins server and pulls the state of all jobs.
   
   - only failing jobs will be added to the text file
   - text file will be written with jenkins.50.message
   - we wait for 60 seconds before we do the next check
   
Outlook
-------   
 - Consumer reads files in FIFO manner and will also dispaly a message as 
   desired in the file name. Of course there is a maximal display time
   (configure).
 - 
 - Consumer as well as producer could be registered as Windows Services
 - Provide a Java Plugin Framework with spring less configuration to have 
   any kind of message producer running in the same daemon.  
   
 - Write more cool message producers in different languages such as Perl, 
   PHP, Groovy, Batch and so on ...  