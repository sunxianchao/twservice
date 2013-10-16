/**
 * 
 */
package com.gamephone.pay.dao;

import java.util.List;

 
import com.gamephone.common.to.PayChannelTO;
import com.gamephone.pay.exception.YunPayException;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-9-14
 */
public interface PayChannelDAO {

    List<PayChannelTO> getPayChannels() throws YunPayException;

    List<PayChannelTO> getPayServices() throws YunPayException;

}
