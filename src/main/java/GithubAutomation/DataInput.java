package GithubAutomation;


public class DataInput 
{
	static String username, password, repositoryname, comment, updatetext, path;
	
	public static void dataInput()
	{
		username = YamlClass.getvalue("Username");
	    password = YamlClass.getvalue("Password");
	    repositoryname = YamlClass.getvalue("Repository-Name");
	    comment = YamlClass.getvalue("Commit-Message");
	    updatetext = YamlClass.getvalue("Update-Text");
	}
}
