import java.net.URLEncoder;
import groovy.json.JsonSlurper;

node{
	sh "rm -rf smartFolk.csv"
	inputFile = readFile("names.csv");
	lines = inputFile.split('\n');

	for (i=1;i<=lines.size()-1;i++){
		column = lines[i].split(',');
		first = column[0];
		last = column[1];
		urlFirst = URLEncoder.encode(column[0]);
		urlLast = URLEncoder.encode(column[1]);

		curl = getCMDResults("curl goes here");

	if(!curl.contains("error checking value goes here")){
		echo curl;
		echo("There was an error with the request.  Failed on line ${i+1} of the csv.");
		System.exit(0);
	}

	def jsonSlurper = new JsonSlurper();
	def parsedText = jsonSlurper.parseText(curl);
	jsonSlurper = null;

	String email = parsedText.person.email;
	parsedText = null;
	email = email.substring(1,email.length()-1)

	sh "echo ${first},${last},${email} >> smartFolk.csv"
	}
}

def getCMDResults(String cmd){
	result = sh (script:cmd,returnStdout: true).trim();
	return result;
}