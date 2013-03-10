import java.io.*;
import java.util.regex.*;
import java.net.*; 
import java.nio.file.*;
import java.nio.*;

class engine
{	
	public static void main(String[] args) throws IOException
	{
		File get=new File("E:/movies");
		File src;
		File[] store=get.listFiles();
		
		get.mkdir();
		//get.mkdirs();
		
		String code,movie;
			
		
		
		String pat0="(.*)(\\.(mp4|avi|mkv)$)";
		
		Pattern p=Pattern.compile(pat0);
		
		
		
		for(File f:store)
		{
			Matcher m=p.matcher(f.getName());
			
			if(m.find())
			{
				//System.out.println(m.group(1));
				String link="http://www.rottentomatoes.com/m/"+m.group(1).replaceAll(" ","_");
				URL rotten=new URL(link);
				InputStreamReader obj=new InputStreamReader(rotten.openStream());
				BufferedReader in=new BufferedReader(obj);
				String pat="\"genre\">(.*\\w+)<";
				String pat1="Average Rating: (\\d{1}\\.\\d/5)";
				String pat2="\"og:title\" content=\"(.*\\w+)\"";
				Pattern r=Pattern.compile(pat);
				Pattern q=Pattern.compile(pat1);
				Pattern s=Pattern.compile(pat2);
				
				
				while((code=in.readLine())!=null)
				{
					
					m=s.matcher(code);
					if(m.find())
					{
						System.out.println("Title: "+m.group(1));
					}
					m=q.matcher(code);
					if(m.find())
					{
						System.out.println("Rating: "+m.group(1));
					}
					m=r.matcher(code);
					if(m.find())
					{	
						src=new File("E:/movies/genre/"+m.group(1));
						src.mkdirs();
						Path target=FileSystems.getDefault().getPath("E:/movies",f.getName());
						Path new_path=FileSystems.getDefault().getPath(src.getPath(),f.getName());
						try {
							Files.createSymbolicLink(new_path, target);
						} catch (IOException x) {
							System.err.println(x);
						} catch (UnsupportedOperationException x) {
							// Some file systems do not support symbolic links.
							System.err.println(x);
						}
						System.out.print("Genre "+m.group(1)+",");
					}
					
					
				
				}
				
				System.out.println();
				System.out.println();
				
			}
		}
	
	}
	
}