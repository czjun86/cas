package com.complaint.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.complaint.utils.ByteUtil;

public class ByteEncoder extends ProtocolEncoderAdapter{

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {  
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);  
          
        String respMsg = (String) message;
        byte[] bytes = respMsg.getBytes("utf-8");  
        byte[] sizeBytes = ByteUtil.toByteArray(bytes.length, 4);
        buffer.put(new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF});//请求头固定信息
        
        byte[] commondBytes = new byte[2];
        ByteUtil.putShort(commondBytes, Short.parseShort(session.getAttribute("commond").toString()), 0);
        buffer.put(commondBytes);//请求操作类型
        
        byte[] encryptBytes = new byte[2];
        ByteUtil.putShort(encryptBytes, (short)0, 0);
        buffer.put(encryptBytes);//加密信息
        
        buffer.put(sizeBytes);//将前4位设置成数据体的字节长度  
        
        buffer.put(bytes);//消息内容  
        
        buffer.flip();  
        out.write(buffer);  
	}
}
