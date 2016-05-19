package com.complaint.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.complaint.utils.ByteUtil;
import com.complaint.utils.Constant;

public class ByteDecoder extends CumulativeProtocolDecoder{
	
	private static final Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        if(in.remaining() > 0){//判断是否有数据
            byte[] sizeBytes = new byte[Constant.headLen];
            byte[] headBodyBytes = new byte[Constant.len];
            in.mark();//标记当前位置，以便reset  
            in.get(sizeBytes);//读取消息头  
            this.getHeadBodyBytes(sizeBytes, headBodyBytes);
            //int转byte[]的一个工具类  
            int size = ByteUtil.getInt(headBodyBytes, 0);
            logger.debug("预计接收长度:" + size);
            //如果消息内容的长度不够则直接返回true
            if(size > in.remaining()){//如果消息内容不够，则重置，相当于不读取size  
                in.reset();  
                return false;//接收新数据，以拼凑成完整数据  
            } else{  
                byte[] bytes = new byte[size];   
                in.get(bytes, 0, size);
                String msg = new String(bytes,"utf-8");
                if(this.validRequest(sizeBytes))
                	session.setAttribute("validRequest", true);
                else
                	session.setAttribute("validRequest", false);
                session.setAttribute("commond", this.getCommond(sizeBytes));
                out.write(msg);  
                if(in.remaining() > 0){//如果读取内容后还粘了包，就进行下一次解析  
                    return true;  
                }
            }
        }
        return false;//处理成功，让父类进行接收下个包  
	}

	private void getHeadBodyBytes(byte[] headBytes,byte[] headBodyBytes){
		for(int i=0;i<headBodyBytes.length;i++){
			headBodyBytes[i] = headBytes[Constant.offset + i];
		}
	}
	
	private boolean validRequest(byte[] sizeBytes){
		byte[] needBytes = new byte[]{(byte)0xFF,(byte)0x00,(byte)0xFF};
		byte[] actuallyBytes = new byte[]{sizeBytes[0],sizeBytes[1],sizeBytes[2]};
		String needStr = new String(needBytes);
		String actuallyStr = new String(actuallyBytes);
		if(needStr.equals(actuallyStr))
			return true;
		return false;
	}
	
	private short getCommond(byte[] sizeBytes){
		byte[] commondBytes = new byte[]{sizeBytes[3],sizeBytes[4]};
		return ByteUtil.getShort(commondBytes, 0);
	}
}
