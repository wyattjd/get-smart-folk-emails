import java.net.URLEncoder;
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

		curl = getCMDResults("curl -i curlgoeshere.com");

	if(!curl.contains("HTTP/1.1 200")){
		echo curl;
		echo("There was an error with the request.  Failed on line ${i+1} of the csv.");
		System.exit(0);
	}

	if(curl.contains("mailto:")){
		mailToTag = curl.split("mailto:");
		endTag = mailToTag[1].split('">');
		email = endTag[0]
		}else{
			email = "No Email Found";
			echo "No email found for ${first} ${last}"
		}
		sh "echo ${first},${last},${email} >> smartFolk.csv"
	}
}

def getCMDResults(String cmd){
	result = sh (script:cmd,returnStdout: true).trim();
	return result;
}