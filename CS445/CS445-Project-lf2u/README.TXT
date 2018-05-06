#Configuration instructions

$ sudo add-apt-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
#/* install java */
  
$ sudo apt-get install maven
#/* install maven via command line */

#Build and deploy instructions

I use maven to be take care of project.

$ Maven package
#/* build project, the executable is named 'lf2u.war' in Target folder. */

$ Maven site
#/* build project, producing project executable and reports for covrage report. The report is located in target folder 
The Site Plugin is used to generate a site for the project. 
The generated site also includes the project's reports that were configured in the POM*/

$ Maven deploy
#/*   done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects.
 */


#Copyright and licensing instructions
Author : Jinyang Li
License: GNU GENERAL PUBLIC LICENSE v3.0


#Known bugs
Get report functional requirments for Farmers and Managers are incomplete.

#Credits and acknowledgements
JAVA Open source community for nice spring mvc framework and other tools.
Maven project for easyier project management.
