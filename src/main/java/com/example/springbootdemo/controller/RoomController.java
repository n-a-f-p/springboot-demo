package com.example.springbootdemo.controller;

import com.example.springbootdemo.pojo.Room;
import com.example.springbootdemo.util.ApiUtil;
import com.example.springbootdemo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Set;
import java.util.UUID;

@Controller
public class RoomController {

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/createRoom")
    public String createRoom(@RequestParam("vediosrc") String vediosrc,@RequestParam("roomname") String roomname, HttpSession session) throws Exception {
        String res = ApiUtil.getM3u8(vediosrc);
        System.out.println(roomname);
        if(res.equals("")){
            return "error";
        }
        if(roomname==null||roomname.equals("")){
            return "error";
        }
        //redis中存储房间号
        redisUtil.sSet("rooms",roomname);
        //每个房间号对应一个src和房间主人id
        Room master = new Room(res, (String) session.getAttribute("userid"),roomname);
        redisUtil.set(roomname,master);
        return "redirect:/roomid/"+roomname;
    }

    @RequestMapping("/roomid/{roomid}")
    public String roomid(@PathVariable("roomid") String roomid, Model model, HttpSession session){
        System.out.println("roomid:"+roomid);
        if(!(redisUtil.sHasKey("rooms",roomid))){
            return "notfound";
        }else{
            Room room = (Room) redisUtil.get(roomid);
            model.addAttribute("res",room.getJsonsrc());
            model.addAttribute("masterid",room.getMasterid());
            model.addAttribute("roomid",roomid);
        }
        return "videoroom";
    }

    @RequestMapping("/showRoom")
    public String showRoom(@RequestParam("userid") String userid, Model model, HttpSession session){
        session.setAttribute("userid",userid);
        //从redis中获取房间的数据
        Set<Object> rooms = redisUtil.sGet("rooms");

        model.addAttribute("rooms",rooms);
        return "showrooms";
    }
}
