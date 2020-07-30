
      withCredentials([
            usernamePassword(credentialsId: credentials,
            usernameVariable: 'username',
            passwordVariable: 'password')
        ]) {
            PASSWORD = password.collect { it }
	    USER = username.collect { it }
        }


class bitbucket {

def getRepoBranches(String organization, String repository, String credentials ) {

        String baseUrl = "https://api.bitbucket.org"
                
        String version = "2.0"
       
        // put it all together
        String branchesUrl = [baseUrl, version, "repositories", organization, repository, "refs", "branches"].join("/")
                              
  
				  
        PASSWORD = PASSWORD.toString().replace(',', '').replace('[', '').replace(']', '').replace(' ', '')
        USER = USER.toString().replace(',', '').replace('[', '').replace(']', '').replace(' ', '')

                
        String username = USER
        String password = PASSWORD
                

        // Create authorization header using Base64 encoding
        String userpass = username + ":" + password;
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

        // Create URL
        URL url = branchesUrl.toURL()

        // Open connection
        URLConnection connection = url.openConnection()

        // Set authorization header
        connection.setRequestProperty ("Authorization", basicAuth)

        // Open input stream
        InputStream inputStream = connection.getInputStream()

        // Get JSON output
        def branchesJson = new groovy.json.JsonSlurper().parseText(inputStream.text)


        // Close the stream
        inputStream.close()
	
	return branchesJson.values.name
    }

def gitPull(String organization, String repository, String branch, String credentials ) {
	git credentialsId: 'bitbucket', branch: 'master', url: 'https://bitbucket.org/den77033/jira-plugin'
}
	
}
