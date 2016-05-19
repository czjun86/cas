package com.complaint.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	 private static final int PORT = 9123;
	
	    public static void main( String[] args ) throws IOException
	    {
	        IoAcceptor acceptor = new NioSocketAcceptor();
	        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory(Charset.forName( "UTF-8" ))));
	        acceptor.getFilterChain().addFirst("codeFactory", new ProtocolCodecFilter(new ByteCodeFactory()));
	        acceptor.getFilterChain().addLast("executorFilter", new ExecutorFilter(Executors.newCachedThreadPool()));
	        acceptor.setHandler(new MinaServerHandler());
	        acceptor.getSessionConfig().setMinReadBufferSize(100);
	        acceptor.getSessionConfig().setMaxReadBufferSize(8888);
	        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	        acceptor.bind(new InetSocketAddress(PORT));
	    }
}
