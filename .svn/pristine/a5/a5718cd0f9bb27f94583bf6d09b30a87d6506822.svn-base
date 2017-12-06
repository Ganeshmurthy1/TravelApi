package com.resources.utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FolderZiper {
	public static void main(String[] a) throws Exception {
		zipFolder("C:/a/temp", "C:/a/temp.zip","");
	}

	static public void zipFolder(String srcFolder, String destZipFile,String fileName) throws Exception {
		if(deleteFiles(srcFolder)){
			createFile(srcFolder,fileName);
			ZipOutputStream zip = null;
			FileOutputStream fileWriter = null;
			fileWriter = new FileOutputStream(destZipFile);
			zip = new ZipOutputStream(fileWriter);
			addFolderToZip("", srcFolder, zip);
			zip.flush();
			zip.close(); 
		}

	}

	static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
			throws Exception {
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}

		} 

	}

	static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
			throws Exception {
		File folder = new File(srcFolder);
		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
	static private boolean deleteFiles(String path){
		boolean isDirEmpty=false;
		File file = new File(path);
		File[] listOfFiles = file.listFiles(); 
		if(listOfFiles!=null && listOfFiles.length>0){
			for (int i = 0; i < listOfFiles.length; i++) 
				if (listOfFiles[i].isFile()) 
				{
					String  fileName = listOfFiles[i].getName();
					new File(fileName).delete();
				}
		}

		if(file.listFiles()==null) 
			isDirEmpty=true;
		else if(listOfFiles!=null && listOfFiles.length>0) 
			isDirEmpty=true;

		return isDirEmpty;

	}

	static private void createFile(String sourceFile,String fileName){
		File f = new File(sourceFile+"/"+fileName);
		if(!f.getParentFile().exists()){
			f.getParentFile().mkdirs();
		}
		//Remove if clause if you want to overwrite file
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			//dir will change directory and specifies file name for writer
			File dir = new File(f.getParentFile(), f.getName());
			PrintWriter writer = new PrintWriter(dir);
			writer.print("writing anything...");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}





}





