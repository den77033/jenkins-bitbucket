def getRepoBranches() {

        String baseUrl = "https://api.bitbucket.org"
                
        String version = "2.0"
        String organization = "den77033"
        String repository = "jira-plugin"

        // put it all together
        String branchesUrl = [baseUrl, version, "repositories", organization, repository, "refs", "branches"].join("/")
                
                
        withCredentials([
            usernamePassword(credentialsId: 'bitbucket',
            usernameVariable: 'username',
            passwordVariable: 'password')
        ]) {
            PASSWORD = password.collect { it }
	    USER = username.collect { it }
        }
				  
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
	assert branchesJson instanceof Map
        assert branchesJson.myList instanceof List

        // Close the stream
        inputStream.close()
	
	return branchesJson.myList.toString()
    }

