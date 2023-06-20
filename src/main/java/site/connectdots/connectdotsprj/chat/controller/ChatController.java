//package site.connectdots.connectdotsprj.chat.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import site.connectdots.connectdotsprj.chat.entity.ChatRoom;
//import site.connectdots.connectdotsprj.chat.service.ChatService;
//
//import java.util.List;
//
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/chat")
//public class ChatController {
//    private final ChatService chatService;
//
//    /**
//     * 채팅 룸을 여는 것 밖에 안 돼서
//     * 단순히 localStorage만 설정하면 될 것 같다
//     *
//     * @param model
//     * @return
//     */
//    @GetMapping("/room")
//    public String rooms(Model model) {
//        System.out.println("/chat/room/ -> rooms()");
//        return "/chat/room";
//    }
//
//    /**
//     * 생성된 방을 모두 조회하는 역할
//     *
//     * @return
//     */
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room() {
//        System.out.println("/chat/rooms -> room()");
//        return chatService.findAllRoom();
//    }
//
//    /**
//     * 방을 생성하는 역할
//     *
//     * @param name
//     * @return
//     */
//    @PostMapping("/room")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestBody String name) {
//        System.out.println("/chat/room -> createRoom()");
//        System.out.println("name");
//        return chatService.createRoom(name);
//    }
//
//    /**
//     * 채팅방에 입장할 때.
//     * 단순히 할당만 하면 되는건지?
//     *
//     * @param model
//     * @param roomId
//     * @return
//     */
//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        System.out.println("/chat/room/enter/{roomId} -> roomDetail()" + " --------------- " + roomId);
//        model.addAttribute("roomId", roomId);
//        return "/chat/roomdetail";
//    }
//
//    /**
//     * @param roomId
//     * @return
//     */
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        System.out.println("/chat/room/{roomId} -> roomdInfo()" + " ----------- " + roomId);
//
//        return chatService.findById(roomId);
//    }
//}
