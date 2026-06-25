package com.learn.backend.controller;

import com.learn.backend.common.Result;
import com.learn.backend.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 消息 Controller
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {

    private static final Logger log = Logger.getLogger(MessageController.class.getName());

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/getMessageListForStudent")
    public Result<List<Map<String, Object>>> getMessageListForStudent(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String cid = params.get("cid");
        return Result.success(messageService.getMassageListForStudent(phone, cid));
    }

    @PostMapping("/getMessageListForTeacher")
    public Result<List<Map<String, Object>>> getMessageListForTeacher(@RequestBody Map<String, String> params) {
        String cid = params.get("cid");
        return Result.success(messageService.getMessageListForTeacher(cid));
    }

    @PostMapping("/insert")
    public Result<Void> insert(@RequestBody Map<String, Object> params) {
        int result = messageService.insert(params);
        return result >= 1 ? Result.success() : Result.failed();
    }
}
