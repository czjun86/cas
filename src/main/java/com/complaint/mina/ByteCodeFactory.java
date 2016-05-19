package com.complaint.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.springframework.beans.factory.annotation.Autowired;

public class ByteCodeFactory implements ProtocolCodecFactory{
	
	@Autowired
	private ProtocolEncoderAdapter byteEncoder;
	@Autowired
	private ProtocolDecoderAdapter byteDecoder;

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return byteEncoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return byteDecoder;
	}

}
