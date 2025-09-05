package com.cake0420.dormitory.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "테스트", description = "스웨거 테스트용 api")
public class TestController {

    @Operation(
            summary = "스웨거 테스트",
            description = """
                    스웨거 테스용입니다.
                    """
    )
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("test");
    }
}
