package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below-mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>>groupUserMap; // Groups--> User(list)
    private HashMap<Group, List<Message>> groupMessageMap; // Groups ---> Message(list)
    private HashMap<Message, User> senderMap; // Message---> belongs to which user
    private HashMap<Group, User> adminMap; // Group---> Name of Admin
    private HashSet<String> userMobile; // Unique Mobile Numbers
    private int customGroupCount; // Number of groups
    private int messageId; // Number id of message

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) throws Exception {
        if(userMobile.contains(mobile)) throw new Exception("User already exists");

        User user=new User(name,mobile);
        userMobile.add(mobile); // update set of phone numbers as well
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        String groupName="";
        if(users.size()>2) {
            this.customGroupCount++;
            groupName="Group"+customGroupCount;
        }
        if(users.size()==2) {
            groupName=users.get(1).getName();
        }
        Group group=new Group(groupName, users.size());
        groupUserMap.put(group, users);
        adminMap.put(group, users.get(0));
        groupMessageMap.put(group, new ArrayList<Message>());
        return group;
    }
    public int createMessage(String content) {
        this.messageId++;
        Message message=new Message(messageId, content);
        return messageId;
    }
    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        List<User> list=groupUserMap.get(group);
        if(!list.contains(sender)) throw new Exception("You are not allowed to send message");
        List<Message> msg=groupMessageMap.get(group);
        msg.add(message);
        this.messageId++;
        groupMessageMap.put(group, msg);
        senderMap.put(message, sender);
        return msg.size();
    }
    public String changeAdmin(User approver, User user, Group group) throws Exception {
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!adminMap.get(group).getName().equals(approver)) throw new Exception("Approver does not have rights");
        List<User> list=groupUserMap.get(group);
        if(!list.contains(user)) throw new Exception("User is not a participant");

        adminMap.put(group, user);
        return "SUCCESS";
    }

}
