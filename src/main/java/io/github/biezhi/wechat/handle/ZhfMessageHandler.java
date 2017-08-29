package io.github.biezhi.wechat.handle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.biezhi.wechat.model.GroupMessage;
import io.github.biezhi.wechat.model.UserMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * purpose of this class
 *
 * @author Haifeng.Zhu
 *         created at 8/29/17
 */
public class ZhfMessageHandler extends AbstractMessageHandler {
    
    private List<String> userWhiteList = new ArrayList<>();
    
    private List<String> groupWhiteList = new ArrayList<>();

    public ZhfMessageHandler() {
        groupWhiteList.add("@@45789621b1f0dbaa0b8edb8578f285a1ef25c502442909b774a6ff6d97e9bdce");       // "NickName":"面朝袁海，春暖花开"
        groupWhiteList.add("@@858737524636013963b5c71a86a64fc7c108a2c19de94912550895932a88ff2a");       // 新生代成都尬舞偶像天团

        userWhiteList.add("@7a841f2f1276c24d042756c3acae87b3651640d1f76e1ab7e5b4862863c82846");  // "RemarkName": "杨西"
        userWhiteList.add("@e951b2eac68acb587ebc292a55a4432d");     // "RemarkName": "裘德超"
        userWhiteList.add("@853807f3667f6231009b4f525d335b7cb74301eebf43d32d05a3ce44e05481e0");     //  "NickName": "翎漓雪"
        userWhiteList.add("@add5e0f68fbef98e41d7acbb43e831ac");     //   "RemarkName": "抹茶-宋杰",
        userWhiteList.add("@44fc0388118fcc739bf1d7893111bd0cec4ba188b047fa91bbbb206aab3f2a86");     //    "RemarkName": "卢磊",
        userWhiteList.add("@3bd1f1edac80b01ea838d5c1381525af");     //    "RemarkName": "罗慧",
    }

    @Override
    public void wxSync(JsonObject msg) {
    }

    @Override
    public void groupListChange(String groupId, JsonArray memberList) {
    }

    @Override
    public void groupMemberChange(String groupId, JsonArray memberList) {
    }

    @Override
    public void userMessage(UserMessage userMessage){
        if (null == userMessage) {
            return;
        }
        
        if ( userMessage.getToUserName()==null || userMessage.getFromUserName()==null ){
            return;
        }
        
        // if message is send to self, should enter the command model
        if( userMessage.getToUserName().equals(userMessage.getFromUserName()) 
                && userMessage.getToUserName().equals(userMessage.getWechatApi().getUser().get("UserName")) ){
            doCommand(userMessage);
        }
        
        // in the white list will do the robot reply
        if ( userWhiteList.contains(userMessage.getFromUserName()) ){
            JsonObject raw_msg = userMessage.getRawMsg();
            String toUid = raw_msg.get("FromUserName").getAsString();
            String result = "哈哈哈!";
            userMessage.sendText(result, toUid);
        }
    }

    @Override
    public void groupMessage(GroupMessage groupMessage){
        
    }
    
    private void doCommand(UserMessage userMessage){
        String text = userMessage.getText();
        JsonObject raw_msg = userMessage.getRawMsg();
        String toUid = raw_msg.get("FromUserName").getAsString();
        String result = "welcome back, commander!";
        userMessage.sendText(result, toUid);
    }

}
