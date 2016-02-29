package de.hdm.drools;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.glassfish.jersey.server.ChunkedOutput;

public class NetzverwaltungsOutputThread extends Thread {
	private ChunkedOutput<String> output;
	private NetzverwaltungsOutput netzverwaltungsOutput;
	
	public NetzverwaltungsOutputThread(ChunkedOutput<String> output,NetzverwaltungsOutput netzverwaltungsOutput){
		this.output=output;
		this.netzverwaltungsOutput=netzverwaltungsOutput;
	}
	
	public void run(){
		String chunk;
		while(!isInterrupted()){
			try {
                while ((chunk = netzverwaltungsOutput.getNextString()) != null && !output.isClosed()) {
                    output.write(chunk);
                    TimeUnit.SECONDS.sleep(1);
                }
            }
            catch (IOException | InterruptedException e) {
            	e.printStackTrace();
            }
            finally {
                try {
					output.close();
				}
                catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
	}
}
