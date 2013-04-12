ConsoleApplication1
===================

Environment Settings
--------------------
 LOGITEC_DISPLAY_FOLDER_ROOT, point to the directory the application reads and writes files. The directory contains 
 following sub folders:

    inbound, directory where inbound messages can be placed which will then be displayed on the logitc gamepad.
	the file must have follwoing naming pattern <id>.<nn>.message.
	   
	   id: unique id of the producer of the file
	   nn: time in seconds the message should be displayed at least on the pad
	         

    outbound, can contain up to 4 files: b0.pressed, b1.pressed, b2.pressed and b3.pressed. The actual time stamp
	of the files indicate when the buttons on the pad have been pressed. If a file does not exist, the button was
	never pressed.


    Example:
	  LOGITEC_DISPLAY_FOLDER_ROOT=C:\Users\Beat\.logitec

	   C:\Users\Beat\.logitec\inbound\jenkins.50.message
	   C:\Users\Beat\.logitec\outbound\b1.pressed
	   C:\Users\Beat\.logitec\outbound\b3.pressed