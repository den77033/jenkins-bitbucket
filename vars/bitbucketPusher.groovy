def gitPush(String credentials, String userEmail, String repository) {
  
      withCredentials([
            usernamePassword(credentialsId: credentials,
            usernameVariable: 'username',
            passwordVariable: 'password')
        ]) {
            PASSWORD = password.collect { it }
	    USER = username.collect { it }
        }
				  
        PASSWORD = PASSWORD.toString().replace(',', '').replace('[', '').replace(']', '').replace(' ', '')
        USER = USER.toString().replace(',', '').replace('[', '').replace(']', '').replace(' ', '')

                
        //String username = USER
        //String password = PASSWORD
	
	sh "git config --global user.name $USER"
	sh "git config --global user.email $userEmail"
	
	sh 'git add .'
	sh "git commit -m 'new build $BUILD_NUMBER'  "
	
	sh "git push https://$USER:$PASSWORD@bitbucket.org/$USER/$repository HEAD:$BRANCH_NAME" 

}
