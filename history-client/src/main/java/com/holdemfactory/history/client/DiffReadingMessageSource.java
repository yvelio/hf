//package com.holdemfactory.history.client;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.FileSystem;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Arrays;
//
//import org.eclipse.jgit.api.AddCommand;
//import org.eclipse.jgit.api.CommitCommand;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.Status;
//import org.eclipse.jgit.api.errors.GitAPIException;
//import org.eclipse.jgit.diff.DiffEntry;
//import org.eclipse.jgit.errors.NoWorkTreeException;
//import org.springframework.integration.endpoint.AbstractMessageSource;
//import org.springframework.messaging.Message;
//import org.springframework.stereotype.Component;
//
///**
// * Executes the {@code Diff} command 
// * 
// * @author anlev
// *
// */
//@Component
//public class DiffReadingMessageSource extends AbstractMessageSource<DiffEntry> {
//	private File directory;
//	private Git git;
//	private FileSystem fs = FileSystems.getDefault();
//
//
//	public DiffReadingMessageSource() {
//		
//	}
//	
//	public DiffReadingMessageSource(File directory) {
//		this.directory = directory;
//		
//		try {
//			//If directory == null, it adds src/main/java/io/yvel/gpp/patl as directory
//			git = Git.open(directory);
//		} catch (IOException e) {
//			e.printStackTrace();
//			try {
//				git = Git.init().setDirectory(directory).call();
//				
//				System.out.println("Git new status: "+git.status());
//				
//			} catch (IllegalStateException | GitAPIException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//
//
//	/**
//	 * Typically the returned value will be the {@code payload} of type T, 
//	 * but the returned value may also be a {@link Message} instance whose payload is of type T;
//	 */
//	@Override
//	protected Object doReceive() {
//		Status status = null;
//		try {
//			status = git.status().call();
//		} catch (NoWorkTreeException | GitAPIException e) {
//			e.printStackTrace();
//		}
//
//
//		if(!status.isClean()) {
//			if(!status.getUntracked().isEmpty()) {
//				System.out.println("New files to process: "+Arrays.toString(status.getUntracked().toArray()));
//				System.out.println("	Number of remaining files to process: "+status.getUntracked().size());
//				for(String newFileName : status.getUntracked()) {
//					//TODO: report new files to process?
//					AddCommand add = git.add();
//
//					try {
//						add.addFilepattern(newFileName).call();
//
//						CommitCommand commit = git.commit();
//						commit.setMessage(newFileName+" to commit").call();
//						
//						String content = null;
//						try {
//							content = new String(Files.readAllBytes(Paths.get(directory.getAbsolutePath()+fs.getSeparator() +newFileName)));
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						if (content != null && content.length() > 0) {
//							System.out.println("	New file was with content");
////							System.out.println(content);
////							System.out.println("######################################");
//							return getMessageBuilderFactory().withPayload(content);
//						}
//						System.out.println("	continue to track");
//					} catch (GitAPIException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			if(!status.getModified().isEmpty()) {
//				for(String modifiedFile : status.getModified()) {
//					System.out.println("	Added&Commit&Extracted new record from file: "+modifiedFile);
//
//					String diff = getDiffWithCommit(modifiedFile);
//					return getMessageBuilderFactory().withPayload(diff);
//				}
//			}	
//		} else {
//			System.out.println("No changes");
//		}
//		return null;
//	}
//
//	private String getDiffWithCommit(String modifiedFile) {
//		String diffString = null;
//		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		final String utf8 = StandardCharsets.UTF_8.name();
//
//		try (PrintStream ps = new PrintStream(baos, true, utf8)) {
//			//Differences between the work directory and the index: compares two trees instead of revisions.
//			git.diff().setOutputStream(ps).call();
//
//			diffString = baos.toString(utf8);
//
//			git.add().addFilepattern(modifiedFile).call();
//			git.commit().setMessage("new commit for new record" ).call();
//			
//			return diffString;
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (GitAPIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public String getComponentType() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public File getDirectory() {
//		return directory;
//	}
//
//	public void setDirectory(File directory) {
//		this.directory = directory;
//	}
//}
